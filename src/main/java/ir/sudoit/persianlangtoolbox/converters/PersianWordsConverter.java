package ir.sudoit.persianlangtoolbox.converters;

import ir.sudoit.persianlangtoolbox.core.local.LocalePersian;
import ir.sudoit.persianlangtoolbox.core.model.*;

import java.lang.reflect.Constructor;
import java.util.*;

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

    public String convert(String number) throws Exception {
        return convert(number, this.options);
    }

    public String convert(String input, ConverterOptions options) throws Exception {
        return null;
    }



}
