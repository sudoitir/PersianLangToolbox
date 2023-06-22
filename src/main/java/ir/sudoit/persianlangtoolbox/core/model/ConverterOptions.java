package ir.sudoit.persianlangtoolbox.core.model;

public record ConverterOptions(boolean currency, boolean ignoreDecimal, boolean ignoreZeroCurrency,
                               boolean doNotAddOnly, CurrencyOptions currencyOptions) {

}
