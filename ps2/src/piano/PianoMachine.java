package piano;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.MidiUnavailableException;

import midi.Instrument;
import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
	private Set<Pitch> notesCurrentlyPlaying;
	private Instrument currentInstrument;
    
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
     * Begins playing the midi note specified by rawPitch and adds the note to 
     * the notesCurrentlyPlaying Set. 
     * @param rawPitch
     */
    private void beginMidiNoteAndAddToCurrentlyPlaying(Pitch rawPitch) {
        midi.beginNote(rawPitch.toMidiFrequency(), currentInstrument);
        notesCurrentlyPlaying.add(rawPitch);
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
     * Stops playing the note specified by rawPitch and removes the note from the
     * notesCurrentlyPlaying Set. 
     * @param rawPitch
     */
    private void endMidiNoteAndRemoveFromCurrentlyPlaying(Pitch rawPitch) {
        midi.endNote(rawPitch.toMidiFrequency(), currentInstrument);
        notesCurrentlyPlaying.remove(rawPitch);
    }
    
    /**
     * Changes the currently playing instrument in a round-robin fashion.
     * If any notes are currently being played on the current instrument, those
     * notes will be stopped and restarted on the new instrument. 
     */
    public void changeInstrument() {
        Instrument newInstrument = currentInstrument.next();
        for( Pitch note : notesCurrentlyPlaying )
            restartNoteOnNewInstrument(note, newInstrument);
       	currentInstrument = newInstrument;
    }

    /**
     * Ends the note being played on currentInstrument and restarts it on newInstrument.
     * MUST ensure that note is currently being played on the current Instrument before
     * calling this method.
     * @param note
     * @param newInstrument
     */
    private void restartNoteOnNewInstrument(Pitch note, Instrument newInstrument) {
        midi.endNote(note.toMidiFrequency(), currentInstrument);
        midi.beginNote(note.toMidiFrequency(), newInstrument);
    }
    
    //TODO write method spec
    public void shiftUp() {
    	//TODO: implement for question 3
    }
    
    //TODO write method spec
    public void shiftDown() {
    	//TODO: implement for question 3
    }
    
    //TODO write method spec
    public boolean toggleRecording() {
    	return false;
    	//TODO: implement for question 4
    }
    
    //TODO write method spec
    protected void playback() {    	
        //TODO: implement for question 4
    }

}
