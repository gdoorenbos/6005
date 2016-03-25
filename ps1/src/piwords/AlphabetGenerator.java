package piwords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javafx.collections.transformation.SortedList;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 193
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (28 As, indexes 0-27),
     *                "b", "b", ... (47 Bs, indexes 28-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */
    public static char[] generateFrequencyAlphabet(int base, String[] trainingData) 
    {
    	if( base < 0 || trainingData == null )
    		return null;

    	Map<Character, Integer> letterCounts = getLetterCountMapFromStringArray(trainingData);
    	if( letterCounts.isEmpty() )
    		return null;
    	
		return transformLetterCountsMapToArray(letterCounts, base);
    }

	public static char[] transformLetterCountsMapToArray(Map<Character, Integer> letterCounts, int size) 
	{
		float cumulativeEndpoint = 0;
    	String outputString = "";
		int totalLetterCount = getTotalLetterCount(letterCounts);
	    for( char letter : new TreeSet<Character>(letterCounts.keySet()))
    	{
			float lastcumulativeEndpoint = cumulativeEndpoint;
			cumulativeEndpoint += (float) letterCounts.get(letter) / totalLetterCount * size;
			outputString += createSimpleString(letter, Math.round(cumulativeEndpoint) - Math.round(lastcumulativeEndpoint));
    	}
		return outputString.toCharArray();
	}

	/**
	 * returns string of given length with given letters.
	 * example: createSimpleString('a', 3) returns "aaa"
	 */
	private static String createSimpleString(char letter, int stringLength) 
	{
		char[] outputArray = new char[stringLength];
		Arrays.fill(outputArray, letter);
		return new String(outputArray);
	}

	public static Map<Character, Integer> getLetterCountMapFromStringArray(String[] trainingData) 
	{
		Map<Character, Integer> letterCounts = new HashMap<Character, Integer>();
		for(int wordPos=0; wordPos < trainingData.length; ++wordPos)
			updateLetterCountMapWithWord(letterCounts, trainingData[wordPos]);
		return letterCounts;
	}

	private static void updateLetterCountMapWithWord(Map<Character, Integer> letterCounts, String word) 
	{
		word = word.toLowerCase();
		for(int letterPos=0; letterPos < word.length(); ++letterPos)
			if( isLetterLowercaseAtoZ(word.charAt(letterPos)) )
				incrementLetterCount(letterCounts, word.charAt(letterPos));
	}
	
	private static boolean isLetterLowercaseAtoZ(char letter)
	{
		return( letter >= 'a' && letter <= 'z' );
	}

	private static void incrementLetterCount(Map<Character, Integer> letterCounts, char letter) 
	{
		if( letterCounts.containsKey(letter) )
			letterCounts.put(letter, letterCounts.get(letter)+1);
		else
			letterCounts.put(letter, 1);
	}
	
	public static int getTotalLetterCount(Map<Character, Integer> letterCountMap)
	{
		int count = 0;
		for( char letter : letterCountMap.keySet() )
			count += letterCountMap.get(letter);
		return count;
	}
    
}
