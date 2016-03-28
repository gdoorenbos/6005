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
        
        myPiano.beginNote(new Pitch(2));
        clearMidiHistory();
        myPiano.endNote(new Pitch(2));
        assertMidiHistory("off(62,PIANO)");
        
        clearMidiHistory();
        myPiano.endNote(new Pitch(0));
        assertMidiHistory("");
    }
    
    @Test
    public void changeInstrumentTest() throws MidiUnavailableException
    {
        clearMidiHistory();
        myPiano.changeInstrument();
        myPiano.beginNote(new Pitch(0));
        assertMidiHistory("on(60,BRIGHT_PIANO)");
        myPiano.endNote(new Pitch(0));
        
        clearMidiHistory();
        myPiano.beginNote(new Pitch(0));
        Midi.wait(100);
        myPiano.changeInstrument();
        assertMidiHistory("on(60,BRIGHT_PIANO) wait(100) off(60,BRIGHT_PIANO) wait(0) on(60,ELECTRIC_GRAND)");
        myPiano.endNote(new Pitch(0));
    }
    
    @Test
    public void shiftUpTest() throws MidiUnavailableException
    {
        clearMidiHistory();
        myPiano.shiftUp();
        myPiano.beginNote(new Pitch(0));
        assertMidiHistory("on(72,PIANO)");
        myPiano.endNote(new Pitch(0));
        myPiano.shiftDown();
        
        clearMidiHistory();
        myPiano.beginNote(new Pitch(0));
        Midi.wait(100);
        myPiano.shiftUp();
        assertMidiHistory("on(60,PIANO) wait(100) off(60,PIANO) wait(0) on(72,PIANO)");
        clearMidiHistory();
        myPiano.shiftUp();
        assertMidiHistory("off(72,PIANO) wait(0) on(84,PIANO)");
        clearMidiHistory();
        myPiano.shiftUp();
        assertMidiHistory("");
        myPiano.shiftDown();
        myPiano.shiftDown();
        myPiano.endNote(new Pitch(0));
    }
    
    @Test
    public void shiftDownTest() throws MidiUnavailableException
    {
        clearMidiHistory();
        myPiano.shiftDown();
        myPiano.beginNote(new Pitch(0));
        assertMidiHistory("on(48,PIANO)");
        myPiano.endNote(new Pitch(0));
        myPiano.shiftUp();
        
        clearMidiHistory();
        myPiano.beginNote(new Pitch(0));
        Midi.wait(100);
        myPiano.shiftDown();
        assertMidiHistory("on(60,PIANO) wait(100) off(60,PIANO) wait(0) on(48,PIANO)");
        clearMidiHistory();
        myPiano.shiftDown();
        assertMidiHistory("off(48,PIANO) wait(0) on(36,PIANO)");
        clearMidiHistory();
        myPiano.shiftDown();
        assertMidiHistory("");
        myPiano.shiftUp();
        myPiano.shiftUp();
        myPiano.endNote(new Pitch(0));
    }

}
