package ir.sudoit.persianlangtoolbox.converters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersianWordsConverterTest {


    @Test
    void testConvertToWords() {
        // Test cases with various decimal numbers

        // Test case: Integer number
        String number1 = "12345.0";
        String expectedWords1 = "دوازده هزار سیصد چهل و پنج";
        assertEquals(expectedWords1, PersianWordsConverter.convertToWords(number1));

        // Test case: Decimal number
        String number2 = "1234.567";
        String expectedWords2 = "هزار و دویست سی و چهار و پانصد و شصت و هفت هزارم";
        assertEquals(expectedWords2, PersianWordsConverter.convertToWords(number2));

        // Test case: Negative number
        String number3 = "-9876.54321";
        String expectedWords3 = "منفی نه هزار هشتصد هفتاد و شش ممیز پنجاه و چهار صد و بیست و یک صدم";
        assertEquals(expectedWords3, PersianWordsConverter.convertToWords(number3));

        // Test case: Zero
        String number4 = "0.0";
        String expectedWords4 = "صفر";
        assertEquals(expectedWords4, PersianWordsConverter.convertToWords(number4));
    }

}