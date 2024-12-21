package com.utils;

import com.dto.TelephoneDialPad;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Util {

    private static final String JSON_PATH = "src/test/resources/test_data.json";

    private static final String[][] DIAL_PAD_MAP = {
            {"0"}, {"1"}, {"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"},
            {"J", "K", "L"}, {"M", "N", "O"}, {"P", "Q", "R", "S"},
            {"T", "U", "V"}, {"W", "X", "Y", "Z"}
    };

    /**
     * @param digitInput reads the input
     * @return exact combinations for an input (size of the list )
     */
    public static int calculateCombinationSize(String digitInput) {
        return Optional.ofNullable(digitInput)
                .filter(input -> !input.isEmpty() && input.matches("\\d+"))
                .map(input -> {
                    int size = 1;
                    for (char c : input.toCharArray()) {
                        size *= DIAL_PAD_MAP[Character.getNumericValue(c)].length;
                    }
                    return size;
                })
                .orElse(1);
    }

    /**
     * Reads test data from the JSON file.
     *
     * @param filePath Path to the JSON file.
     * @return A list of maps containing the test data.
     * @throws IOException if the file cannot be read.
     */
    public static List<Map<String, Object>> getTestData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonData = objectMapper.readValue(new File(filePath), Map.class);
        return (List<Map<String, Object>>) jsonData.get("combinations");
    }

    /**
     * Converts JSON test data into a format suitable for a TestNG DataProvider.
     *
     * @return Test data in Object[][] format.
     * @throws IOException if the file cannot be read.
     */
    public static Object[][] getDataProviderData() throws IOException {
        // Read the JSON file and prepare data for tests
        List<Map<String, Object>> testData = getTestData(JSON_PATH);
        Object[][] data = new Object[testData.size()][2];

        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i).get("input"); // Numeric input
            data[i][1] = testData.get(i).get("expectedCombinations"); // Expected combinations
        }
        return data;
    }

    /**
     * @return expected combination for input or return invalid input
     * @throws IOException if the file cannot be read.
     */
    public static List<String> getExpectedCombinations(String input) throws IOException {
        List<Map<String, Object>> combinationsList = getTestData(JSON_PATH);

        for (Map<String, Object> combinationEntry : combinationsList) {
            if (combinationEntry.get("input").equals(input)) {
                return (List<String>) combinationEntry.get("expectedCombinations");
            }
        }
        return List.of("Invalid Input"); // Return a default invalid response if input is not found
    }

    /**
     * Fetches actual and expected values from JSON or computed logic.
     *
     * @param input The numeric input.
     * @return A map with keys "actual" and "expected".
     * @throws IOException If the JSON file cannot be read.
     */
    public static Map<String, Object> getActualAndExpectedValues(String input) throws IOException {
        // Get expected combinations from JSON
        List<String> expectedCombinations = Util.getExpectedCombinations(input);

        // Get actual combinations using the TelephoneDialPad class
        List<String> actualCombinations = TelephoneDialPad.retrieveCombinations(input);

        // Return a map with both actual and expected values
        return Map.of(
                "actual", actualCombinations,
                "expected", expectedCombinations
        );
    }
}