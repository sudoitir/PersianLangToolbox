package ir.sudoit.persianlangtoolbox.converters;

import ir.sudoit.persianlangtoolbox.core.local.LocalePersian;
import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;
import ir.sudoit.persianlangtoolbox.core.model.CurrencyOptions;
import ir.sudoit.persianlangtoolbox.core.model.LocaleConfig;
import ir.sudoit.persianlangtoolbox.core.model.NumberWordMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ir.sudoit.persianlangtoolbox.core.utils.NumberUtil.*;

public class PersianWordsConverter {

    private static final ConverterOptions DefaultConverterOptions =
            new ConverterOptions(false, false, false, false, null);
    private final ConverterOptions options;

    public PersianWordsConverter(ConverterOptions options) {
        if (options == null)
            throw new IllegalArgumentException();
        this.options = options;
    }

    public PersianWordsConverter() {
        this.options = DefaultConverterOptions;
    }

    public static void main(String[] args) throws Exception {
        PersianWordsConverter persianWordsConverter = new PersianWordsConverter();
        String convert = persianWordsConverter.convert("60030610");
        System.out.println(convert);

    }

    public String convert(String number) throws Exception {
        return convert(number, this.options);
    }

    public String convert(String input, ConverterOptions options) throws Exception {
        if (options == null)
            throw new IllegalArgumentException();

        if (!isValidNumber(input)) {
            throw new Exception("Invalid Number \"" + input + "\"");
        }

        var number = Double.parseDouble(input);

        if (options.ignoreDecimal()) {
            number = Math.round(number);
        }

        List<String> words;
        if (options.currency()) {
            words = convertCurrency(String.valueOf(number), options);
        } else {
            words = convertNumber(String.valueOf(number));
        }
        return String.join(" ", words);

    }

    protected List<String> convertNumber(String number) throws Exception {
        LocalePersian locale = new LocalePersian();

        boolean isFloat = isFloat(number);
        var parseDouble = Double.parseDouble(number);


        boolean isNegativeNumber = parseDouble < 0;
        if (isNegativeNumber) {
            parseDouble = Math.abs(parseDouble);
        }

        String[] split = String.valueOf(parseDouble).split("\\.");
        boolean ignoreZero = isNumberZero(parseDouble) && locale.config().ignoreZeroInDecimals();
        List<String> words = convertInternal(Integer.parseInt(split[0]));
        if (isFloat && ignoreZero) {
            words = new ArrayList<>();
        }

        List<String> wordsWithDecimal = new ArrayList<>();
        if (isFloat) {
            if (!ignoreZero) {
                wordsWithDecimal.add(locale.config().texts().and());
            }
            if (split[1].startsWith("0") && locale.config().decimalLengthWordMapping() == null) {
                List<String> zeroWords = new ArrayList<>();
                for (char num : split[1].toCharArray()) {
                    zeroWords.addAll(convertInternal(Character.getNumericValue(num)));
                }
                wordsWithDecimal.addAll(zeroWords);
            } else {
                wordsWithDecimal.addAll(convertInternal(Integer.parseInt(split[1])));
                String decimalLengthWord = locale.config().decimalLengthWordMapping().get(split[1].length());
                if (decimalLengthWord != null) {
                    wordsWithDecimal.add(decimalLengthWord);
                }
            }
        }

        boolean isEmpty = words.isEmpty();
        if (!isEmpty && isNegativeNumber) {
            words.add(0, locale.config().texts().minus());
        }
        words.addAll(wordsWithDecimal);
        return words;
    }

    protected List<String> convertCurrency(String number, ConverterOptions options) throws Exception {
        LocalePersian locale = new LocalePersian();

        var doubleNum = Double.parseDouble(number);

        boolean isNegativeNumber = doubleNum < 0;
        if (isNegativeNumber) {
            doubleNum = Math.abs(doubleNum);
        }

        String[] split = number.split("\\.");
        ArrayList<String> words = new ArrayList<>(convertInternal(Integer.parseInt(split[0])));

        boolean ignoreZero = isNumberZero(doubleNum) && (options.ignoreZeroCurrency() ||
                (locale.config().ignoreZeroInDecimals() && doubleNum == 0));

        if (ignoreZero) {
            words.clear();
        }

        ArrayList<String> wordsWithDecimal = new ArrayList<>();
        boolean isFloat = isFloat(number);
        if (isFloat) {
            if (!ignoreZero) {
                wordsWithDecimal.add(locale.config().texts().and());
            }
            wordsWithDecimal.addAll(convertInternal((int) (Double.parseDouble(split[1]) *
                    (!locale.config().decimalLengthWordMapping().isEmpty() ? Math.pow(10, 2 - split[1].length()) : 1))));
            String decimalLengthWord = locale.config().decimalLengthWordMapping().get(split[1].length());
            if (decimalLengthWord != null && decimalLengthWord.length() > 0) {
                wordsWithDecimal.add(decimalLengthWord);
            }
        }
        boolean isEmpty = words.isEmpty() && wordsWithDecimal.isEmpty();
        if (!isEmpty && isNegativeNumber) {
            words.add(0, locale.config().texts().minus());
        }
        if (!wordsWithDecimal.isEmpty()) {
            words.addAll(wordsWithDecimal);
        }
        return words;
    }

    protected List<String> convertInternal(long number) throws Exception {
        LocalePersian locale = new LocalePersian();
        LocaleConfig config = locale.config();


        long finalNumber1 = number;
        Optional<NumberWordMapping> match = config.numberWordsMapping().stream()
                .filter(elem -> finalNumber1 >= elem.number())
                .findFirst();

        List<String> words = new ArrayList<>();
        if (number <= 100 || (number < 1000 && config.namedLessThan1000())) {
            words.add(match.get().value());
            number -= match.get().number();
            if (number > 0) {
                if (config.splitWord() != null && !config.splitWord().isEmpty()) {
                    words.add(config.splitWord());
                }
                words.addAll(convertInternal(number));
            }
            return words;
        }

        long quotient = number / match.get().number();
        long remainder = number % match.get().number();
        String matchValue = match.get().value();
        words.addAll(convertInternal(quotient));
        words.add(matchValue);
        if (remainder > 0) {

            words.addAll(convertInternal(remainder));
        }
        return words;
    }


}
