package com.sap.cc.unittesting;

public class RomanNumerals {

    // Roman numerals only go up to 3999 (MMMCMXCIX)
    private static final String ROMAN_NUMERAL_REGEX = "^(?=[MDCLXVI])(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    public int toArabic(String romanNumeral) {
        final int ERROR_VALUE = -1;
        if (romanNumeral == null) {
            return ERROR_VALUE;
        }

        romanNumeral = romanNumeral.trim().toUpperCase();
        if (isInvalidRoman(romanNumeral)) {
            return ERROR_VALUE;
        }
        return convertRomanToArabic(romanNumeral);
    }

    private int convertRomanToArabic(String romanNumeral) {
        return calculateUpToLastSymbol(romanNumeral) + valueOfLastSymbol(romanNumeral);
    }

    private int calculateUpToLastSymbol(String romanNumeral) {
        int sum = 0;
        int indexOfPenultimateSymbol = romanNumeral.length() - 2;
        for (int i = 0; i <= indexOfPenultimateSymbol; i++) {
            int current = getDigitalValueAtIndex(romanNumeral, i);
            int next = getDigitalValueAtIndex(romanNumeral, i + 1);

            if (current < next) {
                sum -= current;
            } else {
                sum += current;
            }
        }
        return sum;
    }

    private int getDigitalValueAtIndex(String romanNumeral, int index) {
        return Roman.valueOf(String.valueOf(romanNumeral.charAt(index))).value;
    }

    private int valueOfLastSymbol(String romanNumeral) {
        return getDigitalValueAtIndex(romanNumeral, romanNumeral.length() - 1);
    }

    private boolean isInvalidRoman(String romanNumeral) {
        return !romanNumeral.matches(ROMAN_NUMERAL_REGEX);
    }

    private enum Roman {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        private final int value;

        Roman(int value) {
            this.value = value;
        }
    }
}
