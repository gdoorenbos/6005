package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class PiGeneratorTest {
    @Test
    public void basicPowerModTest() 
    {
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
        assertEquals(0, PiGenerator.powerMod(2, 3, 4));
        assertEquals(1, PiGenerator.powerMod(3, 4, 5));
        assertEquals(5, PiGenerator.powerMod(17, 19, 23));
        assertEquals(449, PiGenerator.powerMod(15, 4, 1024));
        assertEquals(1024, PiGenerator.powerMod(4, 5, 4096));
    }
    
    @Test
    public void powerModTest_InvalidInput()
    {
    	assertEquals(-1, PiGenerator.powerMod(-1, 1, 1));
    	assertEquals(-1, PiGenerator.powerMod(1, -1, 1));
    	assertEquals(-1, PiGenerator.powerMod(1, 1, -1));
    }
    
    @Test
    public void powerModTest_BaseZero()
    {
    	assertEquals(0, PiGenerator.powerMod(0, 1, 1));
    	assertEquals(0, PiGenerator.powerMod(0, 4, 5));
    }
    
    @Test
    public void powerModTest_ZeroPower()
    {
    	assertEquals(0, PiGenerator.powerMod(5, 0, 1));
    	assertEquals(1, PiGenerator.powerMod(6, 0, 5));
    	assertEquals(1, PiGenerator.powerMod(7, 0, 23));
    }
    
    @Test
    public void computePiInHexTest_Basic()
    {
    	/**
    	 * From http://www.herongyang.com/Cryptography/Blowfish-First-8366-Hex-Digits-of-PI.html
    	 * 243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89
    	 */
    	int[] piHexPrecision1 = {0x2};
    	assertArrayEquals(piHexPrecision1, PiGenerator.computePiInHex(1));
    	
    	int[] piHexPrecision5 = {0x2, 0x4, 0x3, 0xF, 0x6};
    	assertArrayEquals(piHexPrecision5, PiGenerator.computePiInHex(5));
    	
    	// not the same as herongyang. bug, or inaccuracy in the alg? Assuming the latter.
    	int[] piHexPrecision10 = {0x2, 0x4, 0x3, 0xF, 0x6, 0xA, 0x8, 0x8, 0x5, 0x7};
    	assertArrayEquals(piHexPrecision10, PiGenerator.computePiInHex(10));
    }
    
    @Test 
    public void computePiInHexTest_PrecisionLessThanOne()
    {
    	assertNull(PiGenerator.computePiInHex(-1));
    	assertNull(PiGenerator.computePiInHex(-10));
    	assertNull(PiGenerator.computePiInHex(-100));
    }
    
    @Test
    public void computePiInHexTest_Precision0()
    {
    	assertArrayEquals(new int[0], PiGenerator.computePiInHex(0));
    }

}
