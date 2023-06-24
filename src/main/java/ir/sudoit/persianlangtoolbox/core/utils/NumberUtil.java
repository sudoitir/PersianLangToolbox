package ir.sudoit.persianlangtoolbox.core.utils;

import java.util.regex.Pattern;

public final class NumberUtil {

    private static final Pattern TRAILING_ZEROS_PATTERN = Pattern.compile("0*$");
    private static final Pattern DECIMAL_POINT_PATTERN = Pattern.compile("\\.$");

    private NumberUtil() {
    }

    public static String trimTrailingZeros(String input) {
        if (input.contains(".")) {
            var trailingZerosMatcher = TRAILING_ZEROS_PATTERN.matcher(input);
            input = trailingZerosMatcher.replaceFirst("");

            var decimalPointMatcher = DECIMAL_POINT_PATTERN.matcher(input);
            input = decimalPointMatcher.replaceFirst("");
        }
        return input;
    }

    public static boolean isDigits(String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        for (var i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static boolean isFloat(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNumber(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFractional(double number) {
        return number % 1 != 0;
    }


    public static boolean isNumberZero(double number) {
        return number >= 0 && number < 1;
    }

}
