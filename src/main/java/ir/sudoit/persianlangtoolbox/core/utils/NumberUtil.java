package ir.sudoit.persianlangtoolbox.core.utils;

public final class NumberUtil {

    private NumberUtil() {
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
