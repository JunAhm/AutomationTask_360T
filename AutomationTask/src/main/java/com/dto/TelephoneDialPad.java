package com.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TelephoneDialPad {
    /**
     * Map representing TelephoneDialPad inputs
     */
    public static String dialPadMap[][] = {
            {"0"}, {"1"}, {"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"},
            {"J", "K", "L"}, {"M", "N", "O"}, {"P", "Q", "R", "S"},
            {"T", "U", "V"}, {"W", "X", "Y", "Z"}
    };

    /**
     * method to calculate all possible combinations of letters
     * for a given numeric input based on the telephone dial pad mapping.
     *
     * @param combinationList List to store generated combinations.
     * @param prefixVar       Current prefix for building combinations.
     * @param remainingVar    input digits to process.
     */
    protected static void calculateAlphabetCombinations(List<String> combinationList, String prefixVar, String remainingVar) {
        // Get the first digit from the remaining string
        int digitVar = Integer.parseInt(remainingVar.substring(0, 1));
        // If only one digit remains, append each corresponding letter to the combinations
        if (remainingVar.length() == 1) {
            for (int i = 0; i < dialPadMap[digitVar].length; i++) {
                combinationList.add(prefixVar + dialPadMap[digitVar][i]);
            }
        } else {
            // Process the current digit and recurse for the rest
            for (int i = 0; i < dialPadMap[digitVar].length; i++) {
                calculateAlphabetCombinations(combinationList,
                        prefixVar + dialPadMap[digitVar][i], remainingVar.substring(1));
            }
        }

    }

    /**
     * Public method to retrieve all possible letter combinations for a given numeric input.
     * Validates input and uses `calculateAlphabetCombinations` to generate the results.
     *
     * @param digitInput Numeric input string. this is called from test class
     * @return A LinkedList of all possible letter combinations or an "Invalid Input" message for invalid inputs.
     */

    public static LinkedList<String> retrieveCombinations(String digitInput) {
        return Optional.ofNullable(digitInput)// Handle null input
                .filter(input -> !input.isEmpty() && input.matches("\\d+"))// checks input is non-empty and numeric
                .map(input -> {
                    LinkedList<String> combinationList = new LinkedList<>();
                    //calculates and return the combination
                    calculateAlphabetCombinations(combinationList, "", digitInput);
                    return combinationList;
                })
                .orElseGet(() -> {   // Return "Invalid Input" if input is null, empty, or non-numeric
                    LinkedList<String> invalidInputResponse = new LinkedList<>();
                    invalidInputResponse.add("Invalid Input");
                    return invalidInputResponse;
                });
    }

}

