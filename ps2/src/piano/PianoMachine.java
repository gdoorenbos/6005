package piano;

import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
	Set<Integer> currentlyPlayingMidiNotes;
    
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
    	currentlyPlayingMidiNotes = new HashSet<Integer>();
    	currentlyPlayingMidiNotes.clear();
    }
    
    /**
     * Begins playing a note specified by rawPitch if the note is not already playing.
     * @param rawPitch
     */
    public void beginNote(Pitch rawPitch) {
        if( !currentlyPlayingMidiNotes.contains(rawPitch.toMidiFrequency()))
        {
            midi.beginNote(rawPitch.toMidiFrequency());
            currentlyPlayingMidiNotes.add(rawPitch.toMidiFrequency());
        }
    }
    
    /**
     * Stops playing a note specified by rawPitch if the note is currently playing.
     * @param rawPitch
     */
    public void endNote(Pitch rawPitch) {
        if( currentlyPlayingMidiNotes.contains(rawPitch.toMidiFrequency()))
        {
            midi.endNote(rawPitch.toMidiFrequency());
            currentlyPlayingMidiNotes.remove(rawPitch.toMidiFrequency());
        }
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
