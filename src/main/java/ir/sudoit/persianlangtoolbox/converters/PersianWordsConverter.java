package ir.sudoit.persianlangtoolbox.converters;


import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;

import static ir.sudoit.persianlangtoolbox.core.constant.NumberConstant.*;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.isValidNumber;

public class PersianWordsConverter {

    private static final ConverterOptions DefaultConverterOptions =
            new ConverterOptions(false, false, false);

    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param input The String decimal number to convert.
     * @return The words representation of the decimal number or null if number not valid.
     */
    public static String convertToWords(String input) {
        if (isValidNumber(input)) {
            return convertToWords(input, DefaultConverterOptions);
        } else
            return null;
    }


    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param input The decimal number to convert.
     * @return The words representation of the decimal number.
     */
    public static String convertToWords(String input, ConverterOptions converterOptions) {
        if (!isValidNumber(input))
            return null;

        String[] split = input.split("\\.");
        Double number = Double.parseDouble(input);
        var integerPart = number.longValue();

        Long fractional = 0L;
        fractional = getFractional(split, fractional);

        if (Boolean.TRUE.equals(converterOptions.ignoreDecimal())) {
            integerPart = Math.round(number);
        }

        String convertedInteger = convertInternal(integerPart);

        if (fractional == 0)
            return convertedInteger;
        else {
            var resultBuilder = new StringBuilder(convertedInteger);
            resultBuilder.append(" ممیز ");

            String[] fractionalWords = convertInternal(fractional).split(" ");
            for (String word : fractionalWords) {
                resultBuilder.append(word).append(" ");
            }

            int length;
            if (input.endsWith("d") || input.endsWith("D")) {
                length = input.substring(0, split[1].length() - 1).length();
            } else
                length = split[1].length();

            int decimalIndex = Math.min(length, decimals.length - 1);
            resultBuilder.append(decimals[decimalIndex]);

            return resultBuilder.toString();
        }
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
            long thousand = number % 1000;
            if (thousand != 0) {
                var currentWords = new StringBuilder();

                if (thousand / 100 != 0) {
                    currentWords.append(hundreds[(int) (thousand / 100)]).append(" ");
                }

                long tensUnits = thousand % 100;
                if (tensUnits < 20) {
                    currentWords.append(units[(int) tensUnits]);
                } else {
                    currentWords.append(tens[(int) (tensUnits / 10)]).append(" و ").append(units[(int) (tensUnits % 10)]);
                }

                currentWords.append(" ").append(scales[scaleIndex]);
                words.insert(0, currentWords.toString().trim() + " ");
            }

            number /= 1000;
            scaleIndex++;
        }
        return words.toString().trim();
    }


}
