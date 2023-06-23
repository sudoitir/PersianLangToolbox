package ir.sudoit.persianlangtoolbox.converters;


import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;

import static ir.sudoit.persianlangtoolbox.core.constant.NumberConstant.*;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.isValidNumber;

public final class PersianWordsConverter {

    private static final ConverterOptions DefaultConverterOptions =
            new ConverterOptions(false, false, false);

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

        if (fractional == 0 || converterOptions.ignoreDecimal())
            return convertedInteger;
        else {
            return calculateFractional(input, split, fractional, convertedInteger);
        }
    }

    private static String calculateFractional(String input, String[] split, Long fractional, String convertedInteger) {
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
            long thousand = number % THOUSAND;
            if (thousand != 0) {
                var currentWords = new StringBuilder();

                if (thousand / HUNDRED != 0) {
                    currentWords.append(HUNDREDS[(int) (thousand / HUNDRED)]).append(" ");
                }

                long tensUnits = thousand % HUNDRED;
                if (tensUnits < TWENTY) {
                    currentWords.append(UNITS[(int) tensUnits]);
                } else {
                    currentWords.append(TENS[(int) (tensUnits / TEN)]).append(" و ").append(UNITS[(int) (tensUnits % TEN)]);
                }

                currentWords.append(" ").append(SCALES[scaleIndex]);
                words.insert(0, currentWords.toString().trim() + " ");
            }

            number /= THOUSAND;
            scaleIndex++;
        }
        return words.toString().trim();
    }

}
