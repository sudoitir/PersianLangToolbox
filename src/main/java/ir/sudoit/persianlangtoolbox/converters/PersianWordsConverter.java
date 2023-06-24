package ir.sudoit.persianlangtoolbox.converters;


import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;

import java.util.regex.Pattern;

import static ir.sudoit.persianlangtoolbox.core.constant.NumberConstant.*;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.isValidNumber;

public final class PersianWordsConverter {

    private static final ConverterOptions CONVERTER_OPTIONS =
            new ConverterOptions(false, false, "ممیز", null);

    private static final Pattern MULTIPLE_SPACES_PATTERN = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS);
    private static final String[] UNITS = {
            "", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه", "ده",
            "یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده"
    };

    private static final String[] TENS = {
            "", "", "بیست", "سی", "چهل", "پنجاه", "شست", "هفتاد", "هشتاد", "نود"
    };

    private static final String[] HUNDREDS = {
            "", "صد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد", "هزار"
    };

    private static final String[] SCALES = {
            "", "هزار", "میلیون", "میلیارد", "تریلیون", "کوآدریلیون", "کوینتیلیون", "سکستیلیون", "سپتیلیون", "اکتیلیون",
            "نونیلیون", "دسیلیون", "آندسیلیون", "دودسیلیون", "تریدسیلیون", "کوادریدسیلیون", "کویندسیلیون", "سیکستیدسیلیون",
            "سپتاندسیلیون", "اکتودسیلیون", "نونیدسیلیون", "ویجینتیلیون"
    };


    private static final String[] DECIMALS = {
            "", "دهم", "صدم", "هزارم", "میلیونیم", "میلیاردم", "تریلیونم", "کادریلیون", "کنتیلیون"
    };

    private PersianWordsConverter() {
    }

    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param input The String decimal number to convert.
     * @return The words representation of the decimal number
     * @throws NumberFormatException if input not a valid number
     */
    public static String convertToWords(String input) {
        return convertToWords(input, CONVERTER_OPTIONS);
    }

    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param input            The decimal number to convert.
     * @param converterOptions The options for the converter.
     * @return The words representation of the decimal number.
     * @throws NumberFormatException if input not a valid number
     */
    public static String convertToWords(String input, ConverterOptions converterOptions) {
        if (!isValidNumber(input))
            throw new NumberFormatException(String.format("%s not a valid number", input));

        String[] split = input.split("\\.");
        Double number = Double.parseDouble(input);
        var integerPart = number.longValue();

        Long fractional = 0L;
        fractional = getFractional(split, fractional);

        String convertedInteger = convertInternal(integerPart);

        String calculated;
        if (fractional == 0 || converterOptions.ignoreDecimal())
            calculated = convertedInteger;
        else {
            calculated = calculateFractional(input, split, fractional, convertedInteger, converterOptions);
        }

        if (converterOptions.currency())
            return addCurrencyWord(converterOptions, calculated);
        else
            return calculated;
    }

    private static String addCurrencyWord(ConverterOptions converterOptions, String calculated) {
        return calculated
                .concat(" ")
                .concat(converterOptions.currencyOptions().name())
                .concat(" ")
                .concat(converterOptions.currencyOptions().symbol()).trim();
    }

    private static String calculateFractional(String input, String[] split, Long fractional, String convertedInteger,
                                              ConverterOptions converterOptions) {
        var resultBuilder = new StringBuilder(convertedInteger);
        resultBuilder.append(" ").append(converterOptions.point()).append(" ");

        String[] fractionalWords = convertInternal(fractional).split(" ");
        for (String word : fractionalWords) {
            resultBuilder.append(word).append(" ");
        }

        int length;
        if (input.endsWith("d") || input.endsWith("D")) {
            length = input.substring(0, split[1].length() - 1).length();
        } else
            length = split[1].length();

        int decimalIndex = Math.min(length, DECIMALS.length - 1);
        resultBuilder.append(DECIMALS[decimalIndex]);

        return resultBuilder.toString();
    }

    private static Long getFractional(String[] split, Long fractional) {
        if (split.length > 1) {
            String fractionalPart = split[1];
            if (fractionalPart.endsWith("d") || fractionalPart.endsWith("D")) {
                fractionalPart = fractionalPart.substring(0, fractionalPart.length() - 1);
            }
            fractional = Long.parseLong(fractionalPart);
        }
        return fractional;
    }

    private static String convertInternal(Long number) {
        if (number == 0) {
            return "صفر";
        }
        if (number < 0) {
            return "منفی " + convertInternal(-number);
        }

        var words = new StringBuilder();

        var scaleIndex = 0;
        while (number > 0) {
            // The last three digits of the number
            long thousand = number % THOUSAND;
            // If the remainder is not zero, we need to convert the last three digits to Persian words
            if (thousand != 0) {
                // Create a new StringBuilder object to hold the Persian words for the current three digits.
                var currentWords = new StringBuilder();

                // If the first digit of the three-digit number is not zero, we need to add the Persian word for that digit followed by "صد" (which means "hundred" in Persian).
                if (thousand / HUNDRED != 0) {
                    // Append the Persian word for the first digit to the StringBuilder object.
                    currentWords.append(HUNDREDS[(int) (thousand / HUNDRED)]).append(" ");
                }

                // The last two digits.
                long tensUnits = thousand % HUNDRED;
                // If the last two digits are less than twenty, we can use the Persian word for that number directly.
                if (tensUnits < TWENTY) {
                    // Append the Persian word for the last two digits to the StringBuilder object.
                    currentWords.append(UNITS[(int) tensUnits]);

                // If the last two digits are twenty or greater, we need to use the Persian word for the tens digit followed by the Persian word for the units digit.
                } else {
                    // Append the Persian words for the tens and units digits to the StringBuilder object.
                    currentWords.append(TENS[(int) (tensUnits / TEN)]).append(" و ").append(UNITS[(int) (tensUnits % TEN)]);
                }
                // Append the Persian word for the current scale (i.e. thousands, millions, etc.) to the StringBuilder object.
                currentWords.append(" ").append(SCALES[scaleIndex]);
                // Insert the Persian words for the current three digits at the beginning of the StringBuilder object.
                words.insert(0, currentWords.toString().trim() + " ");
            }

            // Divide the number by a thousand to move on to the next three digits.
            number /= THOUSAND;
            // Increment the scale index to move on to the next scale.
            scaleIndex++;
        }
        // Convert the StringBuilder object to a String.
        var wordsString = words.toString();
        // Trim any leading or trailing spaces and replace any multiple spaces with a single space, then return the resulting String as the final output of the method.
        return MULTIPLE_SPACES_PATTERN.matcher(wordsString).replaceAll(" ").trim();
    }

}
