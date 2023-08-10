package ir.sudoit.persianlangtoolbox.core.utils;

import java.math.BigDecimal;

public final class NumberUtil {

    private NumberUtil() {
        throw new UnsupportedOperationException();
    }

    public static String trimTrailingZeros(String input) {
        if (input.contains(".")) {
            var bigDecimal = new BigDecimal(input);
            bigDecimal = bigDecimal.stripTrailingZeros();
            input = bigDecimal.toPlainString();
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
