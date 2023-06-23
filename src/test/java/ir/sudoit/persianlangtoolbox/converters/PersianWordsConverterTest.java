package ir.sudoit.persianlangtoolbox.converters;

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
        String number1 = "0";
        String expectedWords1 = "صفر";
        assertEquals(expectedWords1, PersianWordsConverter.convertToWords(number1));

        String number2 = "0d";
        String expectedWords2 = "صفر";
        assertEquals(expectedWords2, PersianWordsConverter.convertToWords(number2));

        String number3 = "0D";
        String expectedWords3 = "صفر";
        assertEquals(expectedWords3, PersianWordsConverter.convertToWords(number3));

        String number4 = "0.0";
        String expectedWords4 = "صفر";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4));

        String number5 = "000";
        String expectedWords5 = "صفر";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4));

        String number6 = "0.0000";
        String expectedWords6 = "صفر";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4));

        String number7 = "0000.0";
        String expectedWords7 = "صفر";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4));
    }


}