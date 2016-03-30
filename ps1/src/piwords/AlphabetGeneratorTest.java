package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AlphabetGeneratorTest 
{

	private void assertGenerateFrequencyAlphabetOutput( int base, 
														String[] trainingData, 
														char[] expectedOutput ) 
	{
		assertArrayEquals( expectedOutput, 
				      	   AlphabetGenerator.generateFrequencyAlphabet(base, trainingData));
	}

	private void assertGetLetterCountMapFromStringArrayOutput(String[] trainingData, 
			                                                  Map<Character, Integer> expectedOutput) 
	{
		assertEquals(expectedOutput, 
				     AlphabetGenerator.getLetterCountMapFromStringArray(trainingData));
	}

	private void assertTransformLetterCountsMapToArrayOutput(Map<Character, Integer> letterCounts, 
			                                                 int size,
			                                                 char[] expected) 
	{
		assertArrayEquals(expected, 
				          AlphabetGenerator.transformLetterCountsMapToArray(letterCounts, size));
	}
	
    @Test
    public void generateFrequencyAlphabetTest() {
        // Expect in the training data that Pr(a) = 2/5, Pr(b) = 2/5,
        // Pr(c) = 1/5.
        String[] trainingData = {"aa", "bbc"};
        // In the output for base 10, they should be in the same proportion.
        char[] expectedOutput = {'a', 'a', 'a', 'a',
                                 'b', 'b', 'b', 'b',
                                 'c', 'c'};
        assertArrayEquals(expectedOutput,
                AlphabetGenerator.generateFrequencyAlphabet(
                        10, trainingData));
    }
	
	@Test
	public void generateFrequencyAlphabetTest_badInput()
	{
		assertGenerateFrequencyAlphabetOutput(10, null, null);
		assertGenerateFrequencyAlphabetOutput(-1, new String[] {"a"}, null);
		assertGenerateFrequencyAlphabetOutput(1, new String[] {"1"}, null);
	}
	
	@Test
	public void generateFrequencyAlphabetTest_simple()
	{
		assertGenerateFrequencyAlphabetOutput(1, new String[] {"a"}, new char[] {'a'});
		assertGenerateFrequencyAlphabetOutput(1, new String[] {"b"}, new char[] {'b'});
		assertGenerateFrequencyAlphabetOutput(2, new String[] {"a"}, new char[] {'a', 'a'});
		assertGenerateFrequencyAlphabetOutput(2, new String[] {"ab"}, new char[] {'a', 'b'});
	}
	
	@Test
	public void getLetterCountMapTest()
	{
		Map<Character, Integer> expectedOutput = new HashMap<Character, Integer>();
		assertGetLetterCountMapFromStringArrayOutput(new String[] {""}, expectedOutput);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"1"}, expectedOutput);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"!"}, expectedOutput);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"12345"}, expectedOutput);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"!@#$%"}, expectedOutput);
		
		expectedOutput.put('a', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"a"}, expectedOutput);
		
		expectedOutput.put('a', 2);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"aa"}, expectedOutput);
		
		expectedOutput.put('a', 1);
		expectedOutput.put('b', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"ab"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 2);
		expectedOutput.put('o', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('e', 1);
		expectedOutput.put('h', 1);
		expectedOutput.put('o', 1);
		expectedOutput.put('l', 2);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 3);
		expectedOutput.put('o', 2);
		expectedOutput.put('w', 1);
		expectedOutput.put('r', 1);
		expectedOutput.put('d', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello world"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 3);
		expectedOutput.put('o', 2);
		expectedOutput.put('w', 1);
		expectedOutput.put('r', 1);
		expectedOutput.put('d', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello, world!"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 3);
		expectedOutput.put('o', 2);
		expectedOutput.put('w', 1);
		expectedOutput.put('r', 1);
		expectedOutput.put('d', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello", "world"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 3);
		expectedOutput.put('o', 2);
		expectedOutput.put('w', 1);
		expectedOutput.put('r', 1);
		expectedOutput.put('d', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"hello, ", "world!"}, expectedOutput);
		
		expectedOutput.clear();
		expectedOutput.put('h', 1);
		expectedOutput.put('e', 1);
		expectedOutput.put('l', 3);
		expectedOutput.put('o', 2);
		expectedOutput.put('w', 1);
		expectedOutput.put('r', 1);
		expectedOutput.put('d', 1);
		assertGetLetterCountMapFromStringArrayOutput(new String[] {"Hello, ", "World!"}, expectedOutput);
	}
	
	@Test
	public void getTotalLetterCountTest()
	{
		Map<Character, Integer> inputMap = new HashMap<Character, Integer>();
		assertEquals(0, AlphabetGenerator.getTotalLetterCount(inputMap));
		
		inputMap.put('a', 1);
		assertEquals(1, AlphabetGenerator.getTotalLetterCount(inputMap));
		
		inputMap.put('a', 2);
		assertEquals(2, AlphabetGenerator.getTotalLetterCount(inputMap));
		
		inputMap.clear();
		inputMap.put('a', 1);
		inputMap.put('b', 1);
		assertEquals(2, AlphabetGenerator.getTotalLetterCount(inputMap));
		
		inputMap.clear();
		inputMap.put('a', 4);
		inputMap.put('b', 8);
		inputMap.put('c', 11);
		inputMap.put('d', 25);
		inputMap.put('j', 5);
		assertEquals(53, AlphabetGenerator.getTotalLetterCount(inputMap));
	}
	
	@Test
	public void transformLetterCountsMapToArray()
	{
		Map<Character, Integer> inputMap = new HashMap<Character, Integer>();
		assertTransformLetterCountsMapToArrayOutput(inputMap, 0, new char[0]);
		assertTransformLetterCountsMapToArrayOutput(inputMap, 1, new char[0]);
		assertTransformLetterCountsMapToArrayOutput(inputMap, 2, new char[0]);
		
		inputMap.put('a', 1);
		assertTransformLetterCountsMapToArrayOutput(inputMap, 1, new char[] {'a'});
		assertTransformLetterCountsMapToArrayOutput(inputMap, 2, new char[] {'a', 'a'});
		assertTransformLetterCountsMapToArrayOutput(inputMap, 10, new char[] {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'});
		
		inputMap.clear();
		inputMap.put('a', 1);
		inputMap.put('b', 1);
		assertTransformLetterCountsMapToArrayOutput(inputMap, 2, new char[] {'a', 'b'});
		assertTransformLetterCountsMapToArrayOutput(inputMap, 3, new char[] {'a', 'a', 'b'});
		assertTransformLetterCountsMapToArrayOutput(inputMap, 4, new char[] {'a', 'a', 'b', 'b'});
		
		inputMap.clear();
		inputMap.put('h', 1);
		inputMap.put('a', 2);
		inputMap.put('z', 1);
		inputMap.put('r', 1);
		inputMap.put('d', 1);
		assertTransformLetterCountsMapToArrayOutput(inputMap, 6, "aadhrz".toCharArray());
		assertTransformLetterCountsMapToArrayOutput(inputMap, 12, "aaaaddhhrrzz".toCharArray());
	}
}
