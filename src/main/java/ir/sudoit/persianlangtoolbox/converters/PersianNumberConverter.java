package ir.sudoit.persianlangtoolbox.converters;

import java.util.HashMap;
import java.util.Map;

public final class PersianNumberConverter {

    private static final Map<Character, Character> ENGLISH_TO_PERSIAN_MAPPING = createMapping();
    private static final Map<Character, Character> PERSIAN_TO_ENGLISH_MAPPING = createReverseMapping();

    private PersianNumberConverter() {
        throw new UnsupportedOperationException();
    }

    private static Map<Character, Character> createMapping() {
        Map<Character, Character> mapping = new HashMap<>();
        mapping.put('0', '۰');
        mapping.put('1', '۱');
        mapping.put('2', '۲');
        mapping.put('3', '۳');
        mapping.put('4', '۴');
        mapping.put('5', '۵');
        mapping.put('6', '۶');
        mapping.put('7', '۷');
        mapping.put('8', '۸');
        mapping.put('9', '۹');
        return mapping;
    }

    private static Map<Character, Character> createReverseMapping() {
        Map<Character, Character> mapping = new HashMap<>();
        for (Map.Entry<Character, Character> entry : ENGLISH_TO_PERSIAN_MAPPING.entrySet()) {
            mapping.put(entry.getValue(), entry.getKey());
        }
        return mapping;
    }

    /**
     * Converts an English number to a Persian number.
     *
     * @param englishNumber The English number to be converted.
     * @return The Persian representation of the input English number.
     */
    public static String convertToPersian(String englishNumber) {
        if (englishNumber == null) {
            return "";
        }
        var persianNumber = new StringBuilder();

        for (char englishDigit : englishNumber.toCharArray()) {
            Character persianDigit = ENGLISH_TO_PERSIAN_MAPPING.get(englishDigit);
            if (persianDigit != null) {
                persianNumber.append(persianDigit);
            } else {
                persianNumber.append(englishDigit);
            }
        }

        return persianNumber.toString();
    }

    /**
     * Converts a Persian number represented as a string to its equivalent English number representation.
     *
     * @param persianNumber The Persian number to convert.
     * @return The English number representation of the given Persian number.
     */
    public static String convertToEnglish(String persianNumber) {
        if (persianNumber == null) {
            return "";
        }
        var englishNumber = new StringBuilder();

        for (char persianDigit : persianNumber.toCharArray()) {
            Character englishDigit = PERSIAN_TO_ENGLISH_MAPPING.get(persianDigit);
            if (englishDigit != null) {
                englishNumber.append(englishDigit);
            } else {
                englishNumber.append(persianDigit);
            }
        }

        return englishNumber.toString();
    }
}
