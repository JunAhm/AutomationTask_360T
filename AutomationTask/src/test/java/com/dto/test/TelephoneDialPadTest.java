package com.dto.test;

import com.dto.TelephoneDialPad;
import com.utils.Util;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TelephoneDialPadTest {

    /**
     * DataProvider to supply test data from the JSON file.
     *
     * @return Test data in Object[][] format.
     * @throws IOException if JSON file cannot be read.
     */
    /**
     * DataProvider to supply test data from the JSON file.
     *
     * @return Test data in Object[][] format.
     * @throws IOException if the JSON file cannot be read.
     */
    @DataProvider(name = "testDataFromJson")
    public Object[][] getDataFromJson() throws IOException {
        // Use the utility method to fetch data for the DataProvider
        return Util.getDataProviderData();
    }

    @Test(dataProvider = "testDataFromJson", priority = 1, description = "Validate combinations from JSON")
    public void testRetrieveCombinations(String input, List<String> expectedCombinations) {
        // Retrieve combinations from the TelephoneDialPad class
        List<String> actualCombinations = TelephoneDialPad.retrieveCombinations(input);
        assertEquals(actualCombinations, expectedCombinations, "Mismatch for input: " + input + " actual =" + actualCombinations + "expected from json = " + expectedCombinations);
    }

    @Test(priority = 2, description = "Test invalid input scenarios")
    public void testInvalidInput() {
        List<String> input = TelephoneDialPad.retrieveCombinations("abc");
        assertEquals(input.size(), Util.calculateCombinationSize("abc"), "Mismatch for input: " + input + "Expected the size of the result for invalid input 'abc' to " +
                "match the calculated size, but it did not." + "actual = " + input.size() + "  expected = " + Util.calculateCombinationSize("abc"));
        assertEquals(input.get(0), "Invalid Input", "Expected invalid input response");
    }

    @Test(priority = 3, description = "Test null input scenario")
    public void testNullInput() {
        List<String> nullResult = TelephoneDialPad.retrieveCombinations(null);
        assertNotEquals(nullResult, "1", "Expected the result for null input to not be equal to '1', but it was " + nullResult);
        assertEquals(nullResult.get(0), "Invalid Input", "Expected null input response");
    }
}
