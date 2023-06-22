package ir.sudoit.persianlangtoolbox.core.model;

import java.util.List;
import java.util.Map;

public record LocaleConfig(
        CurrencyOptions currency,
        Texts texts,
        List<NumberWordMapping> numberWordsMapping,
        Boolean namedLessThan1000,
        String splitWord,
        Boolean ignoreZeroInDecimals,
        Map<Integer, String> decimalLengthWordMapping) {
}
