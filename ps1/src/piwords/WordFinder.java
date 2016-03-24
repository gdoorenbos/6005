package piwords;

import java.util.HashMap;
import java.util.Map;

public class WordFinder {
    /**
     * Given a String (the haystack) and an array of Strings (the needles),
     * return a Map<String, Integer>, where keys in the map correspond to
     * elements of needles that were found as substrings of haystack, and the
     * value for each key is the lowest index of haystack at which that needle
     * was found. A needle that was not found in the haystack should not be
     * returned in the output map.
     * 
     * @param haystack The string to search into.
     * @param needles The array of strings to search for. This array is not
     *                mutated.
     * @return The list of needles that were found in the haystack.
     */
    public static Map<String, Integer> getSubstrings(String haystack,
                                                     String[] needles) 
    {
        if( haystack == null || needles == null )
    		return new HashMap<String, Integer>();
        else
        	return populateSubstringMap(haystack, needles);
    }
    
    private static Map<String, Integer> populateSubstringMap( String haystack,
    													      String[] needles )
    {
    	HashMap<String, Integer> output = new HashMap<String, Integer>();
        for( int i=0; i < needles.length; ++i )
        	insertSubstringIntoMap(output, needles[i], haystack.indexOf(needles[i]));
        return output;
    }
    
    private static void insertSubstringIntoMap( Map<String,Integer> substringMap,
    											String substring,
    											Integer index )
    {
    	if( substring != "" && index != -1 )
    		substringMap.put(substring, index);
    }
}
