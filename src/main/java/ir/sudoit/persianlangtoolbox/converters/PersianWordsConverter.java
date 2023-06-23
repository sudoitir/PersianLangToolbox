package ir.sudoit.persianlangtoolbox.converters;


import static ir.sudoit.persianlangtoolbox.core.constant.NumberConstant.*;
import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.isValidNumber;

public class PersianWordsConverter {


    public static void main(String[] args) {
        String s = convertToWords(-22131236.5d);
        System.out.println(s);
    }


    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param input The String decimal number to convert.
     * @return The words representation of the decimal number or null if number not valid.
     */
    public static String convertToWords(String input) {
        if (isValidNumber(input)) {
            var number = Double.parseDouble(input);
            return convertToWords(number);
        } else
            return null;
    }

    /**
     * Converts a decimal number into its corresponding words representation in Persian (Farsi) language.
     *
     * @param number The decimal number to convert.
     * @return The words representation of the decimal number.
     */
    public static String convertToWords(Double number) {
        var integerPart = number.longValue();
        Double fractionalNumber = number - integerPart;
        var fractional = fractionalNumber.longValue();

        boolean isFractional = fractionalNumber != 0;

        String convertedInteger = convertInternal(integerPart);

        if (!isFractional)
            return convertedInteger;
        else {
            var words = new StringBuilder();
            String s = convertInternal(fractional);
            words.append(convertedInteger).append(" و ").append(s);
            return words.toString();
        }
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
