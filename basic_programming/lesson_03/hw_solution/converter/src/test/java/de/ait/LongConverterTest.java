package de.ait;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
class LongConverterTest {

    @DisplayName("Value is not a number, result add error msg to errors list")
    @Test
    void test_valueIsNotNumber_addErrorMsg() {
        String testValue = "notNumber";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                false
        );

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '" + testValue + "' не является числом",
                errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is number with space, result add error msg to errors list")
    @Test
    void test_valueIsNumberWithSpace_addErrorMsg() {
        String testValue = "186 ";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                false
        );

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '" + testValue + "' не является числом",
                errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is number, result valid Long")
    @Test
    void test_valueNumber_validLong() {
        String testValue = "186";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                false
        );

        assertEquals(0, errors.size());
        assertEquals(186L, result);
    }

    @DisplayName("Value is null and required is true, result add error message to errors list")
    @Test
    void test_valueNullAndRequiredIsTrue_addErrorMsg() {
        String testValue = null;
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                true
        );

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение является обязательным и не должно быть пустым", errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is Number and required is true, result valid Long")
    @Test
    void test_valueNumberAndRequiredIsTrue_validLong() {
        String testValue = "999";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                true
        );

        assertEquals(0, errors.size());
        assertEquals(999L, result);
    }

    @DisplayName("Value is empty and required is false, result add error message to errors list")
    @Test
    void test_valueEmptyAndRequiredIsFalse_addErrorMsg() {
        String testValue = "";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                false);

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '" + testValue + "' не является числом",
                errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is whitespace and required is false, result add error message to errors list")
    @Test
    void test_valueWhitespaceAndRequiredIsFalse_addErrorMsg() {
        String testValue = " ";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        Converter<Long> longConverter = new LongConverter();
        Long result = longConverter.convert(
                testValue,
                columName,
                errors,
                false);

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '" + testValue + "' не является числом",
                errors.get(0));
        assertNull(result);
    }
}
