package piano;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.MidiUnavailableException;

import midi.Midi;
import music.Pitch;

import org.junit.Test;

public class PianoMachineTest {
	
	PianoMachine myPiano = new PianoMachine();
	
	private void clearMidiHistory() throws MidiUnavailableException
	{
	    Midi.getInstance().clearHistory();
	}
	
	private void assertMidiHistory(String expected) throws MidiUnavailableException
	{
	    Midi midi = Midi.getInstance();
        System.out.println(midi.history());
        assertEquals(expected, midi.history());
	}
	
    @Test
    public void singleNoteTest() throws MidiUnavailableException {
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO)";
        
    	Midi midi = Midi.getInstance();

    	midi.clearHistory();
    	
        myPiano.beginNote(new Pitch(1));
		Midi.wait(100);
		myPiano.endNote(new Pitch(1));

        System.out.println(midi.history());
        assertEquals(expected0,midi.history());
    }
    
    @Test
    public void beginNoteTest() throws MidiUnavailableException
    {
        clearMidiHistory();
        myPiano.beginNote(new Pitch(0));
        assertMidiHistory("on(60,PIANO)");
        myPiano.endNote(new Pitch(0));

        clearMidiHistory();
        myPiano.beginNote(new Pitch(1));
        assertMidiHistory("on(61,PIANO)");
        myPiano.endNote(new Pitch(1));
        
        clearMidiHistory();
        myPiano.beginNote(new Pitch(2));
        assertMidiHistory("on(62,PIANO)");
        myPiano.endNote(new Pitch(2));
        
        clearMidiHistory();
        myPiano.beginNote(new Pitch(0));
        myPiano.beginNote(new Pitch(0));
        assertMidiHistory("on(60,PIANO)");
        myPiano.endNote(new Pitch(0));
    }
    
    @Test
    public void endNoteTest() throws MidiUnavailableException 
    {
        myPiano.beginNote(new Pitch(0));
        clearMidiHistory();
        myPiano.endNote(new Pitch(0));
        assertMidiHistory("off(60,PIANO)");

        myPiano.beginNote(new Pitch(1));
        clearMidiHistory();
        myPiano.endNote(new Pitch(1));
        assertMidiHistory("off(61,PIANO)");
        
        clearMidiHistory();
        myPiano.endNote(new Pitch(0));
        assertMidiHistory("");
    }

}
