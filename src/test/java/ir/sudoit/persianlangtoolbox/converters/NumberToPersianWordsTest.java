package ir.sudoit.persianlangtoolbox.converters;

import ir.sudoit.persianlangtoolbox.core.model.ConverterOptions;
import ir.sudoit.persianlangtoolbox.core.model.CurrencyOptions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberToPersianWordsTest {


    @Test
    void testConvertToWordsDefault() {
        assertThat(NumberToPersianWords.convertToWords("15000D")).isNotNull();

        String number1 = "12345.0";
        String expectedWords1 = "دوازده هزار سیصد چهل و پنج";
        assertEquals(expectedWords1, NumberToPersianWords.convertToWords(number1));

        String number2 = "1234.567";
        String expectedWords2 = "یک هزار دویست سی و چهار ممیز پانصد شست و هفت هزارم";
        assertEquals(expectedWords2, NumberToPersianWords.convertToWords(number2));

        String number3 = "-9876.54321";
        String expectedWords3 = "منفی نه هزار هشتصد هفتاد و شش ممیز پنجاه و چهار هزار سیصد بیست و یک میلیاردم";
        assertEquals(expectedWords3, NumberToPersianWords.convertToWords(number3));
    }


    @Test
    void testConvertToWordsZero() {
        String expectedWord = "صفر";

        String number1 = "0";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number1));

        String number2 = "0";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number2));

        String number3 = "0";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number3));

        String number4 = "0.0";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number4));

        String number5 = "000";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number5));

        String number6 = "0.0000";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number6));

        String number7 = "0000.0";
        assertEquals(expectedWord, NumberToPersianWords.convertToWords(number7));
    }


    @Test
    void testConvertToWordsWithCurrencyConfig() {

        CurrencyOptions options = new CurrencyOptions("تومان", "", "ریال", "");
        CurrencyOptions options1 = new CurrencyOptions("دلار", "", "سنت", "");
        CurrencyOptions options2 = new CurrencyOptions("یورو", "", "سنت", "");
        CurrencyOptions options3 = new CurrencyOptions("", "$", "", "");

        String number1 = "100000.000000";
        String expectedWords1 = "صد هزار ریال";
        assertEquals(expectedWords1, NumberToPersianWords.convertToWords(number1,
                new ConverterOptions(true, false, "ممیز", options)));

        String number2 = "3451234.567";
        String expectedWords2 = "سه میلیون چهارصد پنجاه و یک هزار دویست سی و چهار تومان و پانصد شست و هفت ریال";
        assertEquals(expectedWords2, NumberToPersianWords.convertToWords(number2,
                new ConverterOptions(true, false, "و", options)));

        String number3 = "116";
        String expectedWords3 = "صد شانزده ریال";
        assertEquals(expectedWords3, NumberToPersianWords.convertToWords(number3,
                new ConverterOptions(true, false, "و", options)));

        String number4 = "123456.54321";
        String expectedWords4 = "صد بیست و سه هزار چهارصد پنجاه و شش دلار و پنجاه و چهار هزار سیصد بیست و یک سنت";
        assertEquals(expectedWords4, NumberToPersianWords.convertToWords(number4,
                new ConverterOptions(true, false, "و", options1)));

        String number5 = "10000000000000";
        String expectedWords5 = "ده تریلیون ریال";
        assertEquals(expectedWords5, NumberToPersianWords.convertToWords(number5,
                new ConverterOptions(true, false, "و", options)));


        String number6 = "10000000030000";
        String expectedWords6 = "ده تریلیون سی و هزار";
        assertEquals(expectedWords6, NumberToPersianWords.convertToWords(number6,
                new ConverterOptions(true, false, "و", options3)));


        String number7 = "10.1";
        String expectedWords7 = "ده تومان و یک ریال";
        assertEquals(expectedWords7, NumberToPersianWords.convertToWords(number7,
                new ConverterOptions(true, false, "و", options)));


        String number8 = "-42308578273405.44321";
        String expectedWords8 = "منفی چهل و دو تریلیون سیصد هشت میلیارد پانصد هفتاد و هشت میلیون دویست هفتاد و سه هزار چهارصد پنج یورو و چهل و چهار هزار سیصد بیست و یک سنت";
        assertEquals(expectedWords8, NumberToPersianWords.convertToWords(number8,
                new ConverterOptions(true, false, "و", options2)));

        String number9 = "1.5";
        String expectedWords9 = "یک سنت";
        assertEquals(expectedWords9, NumberToPersianWords.convertToWords(number9,
                new ConverterOptions(true, true, "و", options2)));

        String number10 = "1.9";
        String expectedWords10 = "یک سنت";
        assertEquals(expectedWords10, NumberToPersianWords.convertToWords(number10,
                new ConverterOptions(true, true, "و", options2)));

        String number11 = "10.10000000";
        String expectedWords11 = "ده تومان و یک ریال";
        assertEquals(expectedWords11, NumberToPersianWords.convertToWords(number11,
                new ConverterOptions(true, false, "و", options)));
    }


    @Test
    void testConvertToWords_ThenThrowException() {
        assertThrows(NumberFormatException.class,
                () -> NumberToPersianWords.convertToWords("This is not number"));
    }

}