package ir.sudoit.persianlangtoolbox.converters;


import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;

import java.util.regex.Pattern;

import static ir.sudoit.persianlangtoolbox.core.constant.NumberConstant.*;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.isValidNumber;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.trimTrailingZeros;

public final class NumberToPersianWords {

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

    private NumberToPersianWords() {
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

        input = trimTrailingZeros(input);

        String[] split = input.split("\\.");
        Double number = Double.parseDouble(input);
        var integerPart = number.longValue();


        String fractional = getFractional(split);

        String convertedInteger = convertInternal(integerPart);

        String calculated;
        if (fractional.isEmpty() || fractional.isBlank())
            fractional = "0";

        if (("0".equals(fractional)) || converterOptions.ignoreDecimal()) {
            calculated = addCurrencyWord(converterOptions, convertedInteger, false);
        } else {
            String currencyWord = addCurrencyWord(converterOptions, convertedInteger, true);
            String calculatedFractional = calculateFractional(split, fractional, currencyWord, converterOptions);
            calculated = addCurrencyWord(converterOptions, calculatedFractional, false);
        }
        return MULTIPLE_SPACES_PATTERN.matcher(calculated).replaceAll(" ").trim();
    }

    private static String addCurrencyWord(ConverterOptions converterOptions, String calculated, boolean isFractional) {
        var currencyOptions = converterOptions.currencyOptions();
        if (isFractional) {
            if (currencyOptions != null) {
                String name = currencyOptions.name();
                String symbol = currencyOptions.symbol();

                if (name != null && symbol != null) {
                    return calculated
                            .concat(" ")
                            .concat(name)
                            .concat(" ")
                            .concat(symbol).trim();
                }
            }
        } else {
            if (currencyOptions != null) {
                String name = currencyOptions.fractionalName();
                String symbol = currencyOptions.fractionalSymbol();

                if (name != null && symbol != null) {
                    return calculated
                            .concat(" ")
                            .concat(name)
                            .concat(" ")
                            .concat(symbol).trim();
                }
            }
        }

        return calculated;
    }


    private static String calculateFractional(String[] split, String fractional, String convertedInteger,
                                              ConverterOptions converterOptions) {
        var resultBuilder = new StringBuilder(convertedInteger);
        resultBuilder.append(" ").append(converterOptions.point()).append(" ");

        String[] fractionalWords = convertInternal(Long.parseLong(fractional)).split(" ");
        for (String word : fractionalWords) {
            resultBuilder.append(word).append(" ");
        }

        int length = split[1].length();

        int decimalIndex = Math.min(length, DECIMALS.length - 1);
        if (!converterOptions.currency())
            resultBuilder.append(DECIMALS[decimalIndex]);

        return resultBuilder.toString();
    }

    private static String getFractional(String[] split) {
        var fractional = "";
        if (split.length > 1) {
            fractional = String.valueOf(split[1]);
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
        return words.toString().trim();
    }

}
