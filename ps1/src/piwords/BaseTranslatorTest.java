package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseTranslatorTest {
    @Test
    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        int[] input = {0, 1};
        int[] expectedOutput = {2, 5};
        assertArrayEquals(expectedOutput, BaseTranslator.convertBase(input, 2, 10, 2));
        assertArrayEquals(input, BaseTranslator.convertBase(expectedOutput, 10, 2, 2));
    }
    
    @Test
    public void baseTranslatorTest_InputArrayNotMutated()
    {
        int[] input = {0, 1};
        int[] cloneOfInput = input.clone();
        BaseTranslator.convertBase(input, 2, 10, 2);
        assertArrayEquals(cloneOfInput, input);
        
        int[] input2 = {0, 1, 1};
        int[] cloneOfInput2 = input2.clone();
        BaseTranslator.convertBase(input2, 2, 10, 3);
        assertArrayEquals(cloneOfInput2, input2);
    }
    
    @Test 
    public void baseTranslatorTest_InputBase2()
    {
    	int[] oneHalfBase2 = {1, 0};
    	int[] oneHalfBase10 = {5, 0};
    	assertArrayEquals(oneHalfBase10, BaseTranslator.convertBase(oneHalfBase2, 2, 10, 2));
    	
    	int[] input = {1, 0, 1, 1};
    	int[] expectedOutput = {6, 8, 7, 5};
    	assertArrayEquals(expectedOutput, BaseTranslator.convertBase(input, 2, 10, 4));
    }
    
    @Test
    public void baseTranslatorTest_ThreeEights()
    {
    	int[] threeEightsBase2 = {0, 1, 1, 0};
    	int[] threeEightsBase5 = {1, 4, 1, 4, 1, 4};
    	int[] threeEightsBase10 = {3, 7, 5};

    	assertArrayEquals(threeEightsBase5, BaseTranslator.convertBase(threeEightsBase2, 2, 5, 6));
    	assertArrayEquals(threeEightsBase10, BaseTranslator.convertBase(threeEightsBase2, 2, 10, 3));

    	// Can't accurately convert _from_ base five since it's a repeating decimal. 
//    	assertArrayEquals(threeEightsBase2, BaseTranslator.convertBase(threeEightsBase5, 5, 2, 4));
//    	assertArrayEquals(threeEightsBase10, BaseTranslator.convertBase(threeEightsBase5, 5, 10, 3));
    	
    	assertArrayEquals(threeEightsBase2, BaseTranslator.convertBase(threeEightsBase10, 10, 2, 4));
    	assertArrayEquals(threeEightsBase5, BaseTranslator.convertBase(threeEightsBase10, 10, 5, 6));
    }
    
    @Test
    public void baseTranslatorTest_RandomSampling()
    {
    	int[] input1 = {1, 1, 2};
    	int[] output1 = {5, 1, 8};
    	assertArrayEquals(output1, BaseTranslator.convertBase(input1, 3, 10, 3));
    	
    	int[] input2 = {3};
    	int[] output2 = {0, 2, 2, 0};
    	assertArrayEquals(output2, BaseTranslator.convertBase(input2, 10, 3, 4));
    }
    
    @Test
    public void baseTranslatorTest_BoundaryConditions()
    {
    	//pass in empty array
    	int[] expectedOutput = {0, 0};
    	assertArrayEquals(expectedOutput, BaseTranslator.convertBase(new int[0], 2, 10, 2));
    	
    	// precision < 1
    	int[] input = {1, 1};
    	assertNull(BaseTranslator.convertBase(input, 2, 10, 0));
    	assertNull(BaseTranslator.convertBase(input, 2, 10, -1));
    	
    	// pass in empty array and 0 precision
    	assertNull(BaseTranslator.convertBase(new int[0], 2, 10, 0));
    	
    	// pass in bases < 2
    	assertNull(BaseTranslator.convertBase(input, 1, 10, 2));
    	assertNull(BaseTranslator.convertBase(input, 2, 1, 2));
    	assertNull(BaseTranslator.convertBase(input, 1, -10, 2));
    	assertNull(BaseTranslator.convertBase(input, 10, -10, 2));
    	assertNull(BaseTranslator.convertBase(input, -10, 10, 2));
    	
    	// zero bases
    	assertNull(BaseTranslator.convertBase(input, 0, 10, 2));
    	assertNull(BaseTranslator.convertBase(input, 2, 0, 2));
    	
    	// negative input digits
    	int[] firstDigitNegative = {-1, 0, 1};
    	assertNull(BaseTranslator.convertBase(firstDigitNegative, 2, 10, 3));
    	
    	int[] containsNegative = {0, 1, -1};
    	assertNull(BaseTranslator.convertBase(containsNegative, 2, 10, 3));
    	
    	// input digits >= baseA
    	int[] baseTenNumbers = {3, 5, 6, 9};
    	assertNull(BaseTranslator.convertBase(baseTenNumbers, 2, 10, 4));
    	assertNull(BaseTranslator.convertBase(baseTenNumbers, 9, 10, 4));
    }

}
