package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WordFinderTest 
{
	private void assertGetSubstrings( String haystack, 
									  String[] needles, 
									  Map<String,Integer> expectedResult)
	{
		assertEquals(expectedResult, WordFinder.getSubstrings(haystack, needles));
	}
	
    @Test
    public void basicGetSubstringsTest() {
        String haystack = "abcde";
        String[] needles = {"ab", "abc", "de", "fg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("ab", 0);
        expectedOutput.put("abc", 0);
        expectedOutput.put("de", 3);

        String[] copyOfNeedles = needles.clone();
        assertGetSubstrings(haystack, needles, expectedOutput);
        assertArrayEquals(copyOfNeedles, needles);
    }
    
    @Test
    public void getSubStrings_myTests()
    {
    	Map<String, Integer> emptyResult = new HashMap<String, Integer>();
    	
    	assertGetSubstrings("abc", new String[0], emptyResult);
    	assertGetSubstrings("abc", null, emptyResult);
    	assertGetSubstrings("abc", new String[] {""}, emptyResult);
    	assertGetSubstrings(null, new String[] {"a"}, emptyResult);
    	assertGetSubstrings("", new String[] {"a"}, emptyResult);
    	
    	Map<String, Integer> expectedResult1 = new HashMap<String, Integer>();
    	expectedResult1.put("a", 0);
    	assertGetSubstrings("a", new String[] {"a"}, expectedResult1);
    	
    	Map<String, Integer> expectedResult2 = new HashMap<String, Integer>();
    	expectedResult2.put("b", 1);
    	assertGetSubstrings("ab", new String[] {"b"}, expectedResult2);
    	
    	Map<String, Integer> expectedResult3 = new HashMap<String, Integer>();
    	expectedResult3.put("a", 0);
    	expectedResult3.put("b", 1);
    	assertGetSubstrings("ab", new String[] {"a", "b"}, expectedResult3);
    }

}
