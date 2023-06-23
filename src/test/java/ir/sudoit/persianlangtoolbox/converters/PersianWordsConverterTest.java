package ir.sudoit.persianlangtoolbox.converters;

import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;
import ir.sudoit.persianlangtoolbox.core.model.CurrencyOptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersianWordsConverterTest {


    @Test
    void testConvertToWordsDefault() {
        String number1 = "12345.0";
        String expectedWords1 = "دوازده هزار سیصد چهل و پنج";
        assertEquals(expectedWords1, PersianWordsConverter.convertToWords(number1));

        String number2 = "1234.567";
        String expectedWords2 = "یک هزار دویست سی و چهار ممیز پانصد شست و هفت هزارم";
        assertEquals(expectedWords2, PersianWordsConverter.convertToWords(number2));

        String number3 = "-9876.54321";
        String expectedWords3 = "منفی نه هزار هشتصد هفتاد و شش ممیز پنجاه و چهار هزار سیصد بیست و یک میلیاردم";
        assertEquals(expectedWords3, PersianWordsConverter.convertToWords(number3));
    }


    @Test
    void testConvertToWordsZero() {
        String expectedWord = "صفر";

        String number1 = "0";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number1));

        String number2 = "0d";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number2));

        String number3 = "0D";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number3));

        String number4 = "0.0";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number4));

        String number5 = "000";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number5));

        String number6 = "0.0000";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number6));

        String number7 = "0000.0";
        assertEquals(expectedWord, PersianWordsConverter.convertToWords(number7));
    }


    @Test
    void testConvertToWordsWithConfig() {
        String number1 = "100000.0";
        String expectedWords1 = "صد هزار تومان";
        assertEquals(expectedWords1, PersianWordsConverter.convertToWords(number1,
                new ConverterOptions(true, false, "ممیز", new CurrencyOptions("تومان", ""))));

        String number2 = "3451234.567";
        String expectedWords2 = "سه میلیون چهارصد پنجاه و یک هزار دویست سی و چهار ممیز پانصد شست و هفت هزارم تومان";
        assertEquals(expectedWords2, PersianWordsConverter.convertToWords(number2,
                new ConverterOptions(true, false, "ممیز", new CurrencyOptions("تومان", ""))));

        String number3 = "116";
        String expectedWords3 = "صد شانزده تومان";
        assertEquals(expectedWords3, PersianWordsConverter.convertToWords(number3,
                new ConverterOptions(true, false, "و", new CurrencyOptions("تومان", ""))));

        String number4 = "123456.54321D";
        String expectedWords4 = "صد بیست و سه هزار چهارصد پنجاه و شش و پنجاه و چهار هزار سیصد بیست و یک میلیاردم ریال";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4,
                new ConverterOptions(true, false, "و", new CurrencyOptions("ریال", ""))));

        String number5 = "10000000000000D";
        String expectedWords5 = "ده تریلیون ریال";
        assertEquals(expectedWords5, PersianWordsConverter.convertToWords(number5,
                new ConverterOptions(true, false, "و", new CurrencyOptions("ریال", ""))));


        String number6 = "10000000030000";
        String expectedWords6 = "ده تریلیون سی و هزار دلار $";
        assertEquals(expectedWords6, PersianWordsConverter.convertToWords(number6,
                new ConverterOptions(true, false, "و", new CurrencyOptions("دلار", "$"))));


        String number7 = "10.000000001";
        String expectedWords7 = "ده و یک کنتیلیون دلار";
        assertEquals(expectedWords7, PersianWordsConverter.convertToWords(number7,
                new ConverterOptions(true, false, "و", new CurrencyOptions("دلار", ""))));


        String number8 = "-42308578273405.44321";
        String expectedWords8 = "منفی چهل و دو تریلیون سیصد هشت میلیارد پانصد هفتاد و هشت میلیون دویست هفتاد و سه هزار چهارصد پنج یورو";
        assertEquals(expectedWords8, PersianWordsConverter.convertToWords(number8,
                new ConverterOptions(true, true, "و", new CurrencyOptions("یورو", ""))));

        String number9 = "1.5";
        String expectedWords9 = "یک یورو";
        assertEquals(expectedWords9, PersianWordsConverter.convertToWords(number9,
                new ConverterOptions(true, true, "و", new CurrencyOptions("یورو", ""))));

        String number10 = "1.9";
        String expectedWords10 = "یک یورو";
        assertEquals(expectedWords10, PersianWordsConverter.convertToWords(number10,
                new ConverterOptions(true, true, "و", new CurrencyOptions("یورو", ""))));
    }


}