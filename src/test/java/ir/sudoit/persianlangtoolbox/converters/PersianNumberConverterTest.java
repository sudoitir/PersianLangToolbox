package ir.sudoit.persianlangtoolbox.converters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersianNumberConverterTest {


    @Test
    void testConvertToEnglish() {
        String persianNumber = "۰۱۲۳۴۵۶۷۸۹";
        String expectedEnglishNumber = "0123456789";
        String actualEnglishNumber = PersianNumberConverter.convertToEnglish(persianNumber);
        assertEquals(expectedEnglishNumber, actualEnglishNumber);

        String persianNumber2 = "۰۱2۳۴5۶۷۸9";
        String actualEnglishNumber2 = PersianNumberConverter.convertToEnglish(persianNumber2);
        assertEquals(expectedEnglishNumber, actualEnglishNumber2);
    }

    @Test
    void testConvertToPersian() {
        String englishNumber = "0123456789";
        String expectedPersianNumber = "۰۱۲۳۴۵۶۷۸۹";
        String actualPersianNumber = PersianNumberConverter.convertToPersian(englishNumber);
        assertEquals(expectedPersianNumber, actualPersianNumber);
    }

    @Test
    void testConvertToEnglish_IgnoreNonNumericCharacters() {
        String persianNumber = "۱۲۳abc۴۵";
        String expectedEnglishNumber = "123abc45";
        String actualEnglishNumber = PersianNumberConverter.convertToEnglish(persianNumber);
        assertEquals(expectedEnglishNumber, actualEnglishNumber);
    }

    @Test
    void testConvertToEnglish_NoException() {
        assertDoesNotThrow(() -> {
            String persianNumber = "۱۲۳۴۵";
            PersianNumberConverter.convertToEnglish(persianNumber);
        });

        assertDoesNotThrow(() -> {
            PersianNumberConverter.convertToEnglish(null);
        });

        assertDoesNotThrow(() -> {
            PersianNumberConverter.convertToEnglish("");
        });

        assertDoesNotThrow(() -> {
            String persianNumber = "۱۲۳abc۴۵";
            PersianNumberConverter.convertToEnglish(persianNumber);
        });
    }

    @Test
    void testConvertToPersian_NoException() {
        assertDoesNotThrow(() -> {
            String englishNumber = "12345";
            PersianNumberConverter.convertToPersian(englishNumber);
        });

        assertDoesNotThrow(() -> {
            String englishNumber = "";
            PersianNumberConverter.convertToPersian(englishNumber);
        });

        assertDoesNotThrow(() -> {
            PersianNumberConverter.convertToPersian(null);
        });

        assertDoesNotThrow(() -> {
            String englishNumber = "123۴۵۶";
            PersianNumberConverter.convertToPersian(englishNumber);
        });
    }

}