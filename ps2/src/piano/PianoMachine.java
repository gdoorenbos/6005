package piano;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
	private boolean midi60IsPlaying;
    private boolean midi61IsPlaying;
    
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
    	midi60IsPlaying = false;
        midi61IsPlaying = false;
    }
    
    /**
     * Begins playing a note specified by rawPitch if the note is not already playing.
     * @param rawPitch
     */
    public void beginNote(Pitch rawPitch) {
        if( rawPitch.toMidiFrequency() == 60 && !midi60IsPlaying )
        {
            midi.beginNote(rawPitch.toMidiFrequency());
            midi60IsPlaying = true;
        }
        else if( rawPitch.toMidiFrequency() == 61 && !midi61IsPlaying )
        {
            midi.beginNote(rawPitch.toMidiFrequency());
            midi61IsPlaying = true;
        }
    }
    
    /**
     * Stops playing a note specified by rawPitch if the note is currently playing.
     * @param rawPitch
     */
    public void endNote(Pitch rawPitch) {
        if( rawPitch.toMidiFrequency() == 60 && midi60IsPlaying )
        {
            midi.endNote(rawPitch.toMidiFrequency());
            midi60IsPlaying = false;
        }
        else if( rawPitch.toMidiFrequency() == 61 && midi61IsPlaying )
        {
            midi.endNote(rawPitch.toMidiFrequency());
            midi61IsPlaying = false;
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
