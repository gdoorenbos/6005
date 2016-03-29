package piano;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.MidiUnavailableException;

import midi.Instrument;
import midi.Midi;
import music.Pitch;
import music.NoteEvent;

public class PianoMachine {

    private Midi midi;
	private Set<Pitch> notesCurrentlyPlaying;
	private Instrument currentInstrument;

    private int octaveShift;
    private static final int MAX_OCTAVE_SHIFT = 24;
    private static final int MIN_OCTAVE_SHIFT = -24;
    private static final int ONE_OCTAVE = 12;
    
    private boolean isRecording;
    private long recordingStartTime;
    private List<NoteEvent> recording;
    
	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
            return;
        }
    	
    	notesCurrentlyPlaying = new HashSet<Pitch>();
    	notesCurrentlyPlaying.clear();
    	
    	currentInstrument = Midi.DEFAULT_INSTRUMENT;
    	
    	octaveShift = 0;
    	
    	isRecording = false;
    	recordingStartTime = 0;
    	recording = new LinkedList<NoteEvent>();
    	recording.clear();
    }

    /**
     * Begins playing the midi note specified by rawPitch and adds the note to 
     * the notesCurrentlyPlaying Set. 
     * @param rawPitch
     */
    private void beginMidiNoteAndAddToCurrentlyPlaying(Pitch rawPitch) {
        midi.beginNote(rawPitch.toMidiFrequency()+octaveShift, currentInstrument);
        notesCurrentlyPlaying.add(rawPitch);
        
        if( isRecording )
        {
            NoteEvent event = new NoteEvent(rawPitch, System.currentTimeMillis()-recordingStartTime, currentInstrument, NoteEvent.Kind.start);
            recording.add(event);
        }
    }
    
    /**
     * Begins playing the note specified by rawPitch if the note is not already playing.
     * @param rawPitch
     */
    public void beginNote(Pitch rawPitch) {
        if( !notesCurrentlyPlaying.contains(rawPitch) )
            beginMidiNoteAndAddToCurrentlyPlaying(rawPitch);
    }

    /**
     * Stops playing the note specified by rawPitch and removes the note from the
     * notesCurrentlyPlaying Set. 
     * @param rawPitch
     */
    private void endMidiNoteAndRemoveFromCurrentlyPlaying(Pitch rawPitch) {
        midi.endNote(rawPitch.toMidiFrequency()+octaveShift, currentInstrument);
        notesCurrentlyPlaying.remove(rawPitch);
        
        if( isRecording )
        {
            NoteEvent event = new NoteEvent(rawPitch, System.currentTimeMillis()-recordingStartTime, currentInstrument, NoteEvent.Kind.stop);
            recording.add(event);
        }
    }
    
    /**
     * Stops playing the note specified by rawPitch if the note is currently playing.
     * @param rawPitch
     */
    public void endNote(Pitch rawPitch) {
        if( notesCurrentlyPlaying.contains(rawPitch) )
            endMidiNoteAndRemoveFromCurrentlyPlaying(rawPitch);
    }

    /**
     * Ends all currently playing notes.
     */
    private void endAllCurrentlyPlayingNotes() {
        Set<Pitch> tempSet = new HashSet<Pitch>(notesCurrentlyPlaying);
        for( Pitch note : tempSet )
            endNote(note);
    }

    /**
     * calls beginNote(note) for every note in notes. 
     * @param notes
     */
    private void beginNotesInSet(Set<Pitch> notes) {
        for( Pitch note : notes )
            beginNote(note);
    }
    
    /**
     * Changes the currently playing instrument in a round-robin fashion.
     * If any notes are currently being played on the current instrument, those
     * notes will be stopped and restarted on the new instrument. 
     */
    public void changeInstrument() {
        Set<Pitch> restartNotes = new HashSet<Pitch>(notesCurrentlyPlaying);
        endAllCurrentlyPlayingNotes();
        currentInstrument = currentInstrument.next();
        beginNotesInSet(restartNotes);
    }

    /**
     * Increments the octaveShift by one octave and shifts all currently playing notes to the 
     * new octaveShift. 
     */
    private void applyNewOctaveShift(int newOctaveShift) {
        Set<Pitch> restartNotes = new HashSet<Pitch>(notesCurrentlyPlaying);
        endAllCurrentlyPlayingNotes();
        octaveShift = newOctaveShift;
        beginNotesInSet(restartNotes);
    }
    
    /**
     * Increments the octave of the PianoMachine by 1. Can repeat calls to shiftUp() to increase
     * the octave shift to a maximum of two octaves. Calling shiftUp() while the octave is already
     * two octaves higher than the original has no effect.
     */
    public void shiftUp() {
        int newOctaveShift = octaveShift + ONE_OCTAVE;
        if( newOctaveShift <= MAX_OCTAVE_SHIFT )
            applyNewOctaveShift(newOctaveShift);
    }
    
    /**
     * Decrements the octave of the PianoMachine by 1. Can repeat calls to shiftDown() to decrease
     * the octave shift to a maximum of two octaves. Calling shiftDown() while the octave is already
     * two octaves lower than the original has no effect. 
     */
    public void shiftDown() {
        int newOctaveShift = octaveShift - ONE_OCTAVE;
        if( newOctaveShift >= MIN_OCTAVE_SHIFT )
            applyNewOctaveShift(newOctaveShift);
    }
    
    /**
     * Turn recording on if the PianoMachine is not already recording, and turns recording off
     * if the PianoMachine is currently recording.
     * @return true if recording was turned on, false otherwise. 
     */
    public boolean toggleRecording() {
        isRecording = !isRecording;
        if( isRecording )
        {
            recording.clear();
            recordingStartTime = System.currentTimeMillis();
        }
        return isRecording;
    }
    
    /**
     * plays the stored recording. If nothing is stored, nothing is played. 
     */
    protected void playback() {
        long lastNoteTime = 0;
        boolean firstNote = true;
        for( NoteEvent event : recording )
        {
            if( firstNote )
            {
                firstNote = false;
                lastNoteTime = event.getTime();
            }
            else
            {
                int waitTime = (int) (event.getTime() - lastNoteTime) / 10;
                Midi.wait(waitTime);
                lastNoteTime = event.getTime();
            }
            
            if( event.getKind() == NoteEvent.Kind.start )
            {
                beginNote(event.getPitch());
            }
            else
            {
                endNote(event.getPitch());
            }
        }
    }

}
