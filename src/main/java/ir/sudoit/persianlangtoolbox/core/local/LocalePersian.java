package ir.sudoit.persianlangtoolbox.core.local;

import ir.sudoit.persianlangtoolbox.core.comfig.UTF8Control;
import ir.sudoit.persianlangtoolbox.core.model.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalePersian implements Local {


    @Override
    public LocaleConfig config() {
        ResourceBundle bundle = ResourceBundle.getBundle("num",
                new Locale("fa", "IR"), new UTF8Control());
        List<NumberWordMapping> numberWordsMapping = List.of(
                new NumberWordMapping(1000000000000000L, bundle.getString("1000000000000000")),
                new NumberWordMapping(1000000000000L, bundle.getString("1000000000000")),
                new NumberWordMapping(1000000000L, bundle.getString("1000000000")),
                new NumberWordMapping(1000000L, bundle.getString("1000000")),
                new NumberWordMapping(1000L, bundle.getString("1000")),
                new NumberWordMapping(900L, bundle.getString("900")),
                new NumberWordMapping(800L, bundle.getString("800")),
                new NumberWordMapping(700L, bundle.getString("700")),
                new NumberWordMapping(600L, bundle.getString("600")),
                new NumberWordMapping(500L, bundle.getString("500")),
                new NumberWordMapping(400L, bundle.getString("400")),
                new NumberWordMapping(300L, bundle.getString("300")),
                new NumberWordMapping(200L, bundle.getString("200")),
                new NumberWordMapping(100L, bundle.getString("100")),
                new NumberWordMapping(90L, bundle.getString("90")),
                new NumberWordMapping(80L, bundle.getString("80")),
                new NumberWordMapping(70L, bundle.getString("70")),
                new NumberWordMapping(60L, bundle.getString("60")),
                new NumberWordMapping(50L, bundle.getString("50")),
                new NumberWordMapping(40L, bundle.getString("40")),
                new NumberWordMapping(30L, bundle.getString("30")),
                new NumberWordMapping(20L, bundle.getString("20")),
                new NumberWordMapping(19L, bundle.getString("19")),
                new NumberWordMapping(18L, bundle.getString("18")),
                new NumberWordMapping(17L, bundle.getString("17")),
                new NumberWordMapping(16L, bundle.getString("16")),
                new NumberWordMapping(15L, bundle.getString("15")),
                new NumberWordMapping(14L, bundle.getString("14")),
                new NumberWordMapping(13L, bundle.getString("13")),
                new NumberWordMapping(12L, bundle.getString("12")),
                new NumberWordMapping(11L, bundle.getString("11")),
                new NumberWordMapping(10L, bundle.getString("10")),
                new NumberWordMapping(9L, bundle.getString("9")),
                new NumberWordMapping(8L, bundle.getString("8")),
                new NumberWordMapping(7L, bundle.getString("7")),
                new NumberWordMapping(6L, bundle.getString("6")),
                new NumberWordMapping(5L, bundle.getString("5")),
                new NumberWordMapping(4L, bundle.getString("4")),
                new NumberWordMapping(3L, bundle.getString("3")),
                new NumberWordMapping(2L, bundle.getString("2")),
                new NumberWordMapping(1L, bundle.getString("1")),
                new NumberWordMapping(0L, bundle.getString("0"))
        );
        Map<Integer, String> decimalLengthWordMapping = Map.of(
                1, "دهم",
                2, "صدم",
                3, "هزارم",
                4, "ده‌هزارم",
                5, "صد‌هزارم",
                6, "میلیونیوم",
                7, "ده‌میلیونیوم",
                8, "صد‌میلیونیوم",
                9, "میلیاردیوم"
        );
        return new LocaleConfig(
                new CurrencyOptions("تومان", "﷼",
                        new FractionalUnit("تومان", "﷼")),
                new Texts("و", "منفی"),
                numberWordsMapping,
                true,
                "و",
                true,
                decimalLengthWordMapping
        );
    }
}
