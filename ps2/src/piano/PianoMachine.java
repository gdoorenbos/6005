package piano;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
	Set<Pitch> notesCurrentlyPlaying;
    
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
        midi.beginNote(rawPitch.toMidiFrequency());
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
        midi.endNote(rawPitch.toMidiFrequency());
        notesCurrentlyPlaying.remove(rawPitch);
    }
    
    //TODO write method spec
    public void changeInstrument() {
       	//TODO: implement for question 2
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
