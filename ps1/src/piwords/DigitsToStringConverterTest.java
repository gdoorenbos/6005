package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class DigitsToStringConverterTest {
    @Test
    public void basicNumberSerializerTest() {
        // Input is a 4 digit number, 0.123 represented in base 4
        int[] input = {0, 1, 2, 3};

        // Want to map 0 -> "d", 1 -> "c", 2 -> "b", 3 -> "a"
        char[] alphabet = {'d', 'c', 'b', 'a'};

        String expectedOutput = "dcba";
        assertEquals( expectedOutput, 
        		      DigitsToStringConverter.convertDigitsToString(input, 4, alphabet) );
    }
    
    @Test
    public void convertDigitsToStringTest_nonConsecutiveDigits()
    {
    	int[] digits = {3, 2, 1, 0};
    	char[] alphabet = {'a', 'b', 'c', 'd'};
    	String expectedOutput = "dcba";
    	assertEquals(expectedOutput, 
    			  	 DigitsToStringConverter.convertDigitsToString(digits, 4, alphabet));
    }
    
    @Test
    public void convertDigitsToStringTest_nonConsecutiveDigitsAndAlphabet()
    {
    	int[] digits = {5, 2, 8, 1, 7};
    	char[] alphabet = {'d', 'u', 'g', 't', 'a', 'r', 'm', 'q', 'b', 'j'};
    	String expectedOutput = "rgbuq";
    	assertEquals(expectedOutput, 
    				 DigitsToStringConverter.convertDigitsToString(digits, 10, alphabet));
    }
    
    @Test 
    public void convertDigitsToStringTest_badInput()
    {
    	/**
    	 * From the specification:
    	 * 
    	 * If digits[i] >= base or digits[i] < 0 for any i, consider the input
	     * invalid, and return null.
	     * 
	     * If alphabet.length != base, consider the input invalid, and return null.
    	 */
    	
    	// input digit equal to base
    	int[] digits1 = {0, 1, 2};
    	char[] charsBase2 = {'a', 'b'};
    	assertNull(DigitsToStringConverter.convertDigitsToString(digits1, 2, charsBase2));
    	
    	// input digit greater than base
    	int[] digits2 = {0, 1, 3};
    	assertNull(DigitsToStringConverter.convertDigitsToString(digits2, 2, charsBase2));
    	
    	// input digit < 0
    	int[] digits3 = {1, 5, 7, -1};
    	char[] charsBase10 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
    	assertNull(DigitsToStringConverter.convertDigitsToString(digits3, 10, charsBase10));
    	
    	// alphabet.length != base
    	assertNull(DigitsToStringConverter.convertDigitsToString(digits2, 11, charsBase10));
    	
    	// null alphabet
    	assertEquals("", DigitsToStringConverter.convertDigitsToString(digits2, 4, null));
    	
    	// base < 2
    	assertNull(DigitsToStringConverter.convertDigitsToString(new int[] {0}, 1, new char[] {'a'}));
    }

}
