package de.ait;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
class LocalDataTimeConverterTest {

    @Test
    void test_valueValid_resultValidDate() {
        String testValue = "2023.12.31 23:59:12";
        String columnName = "TEST";
        List<String> errors = new ArrayList<>();


        LocalDataTimeConverter localDataTimeConverter = new LocalDataTimeConverter();
        LocalDateTime result = localDataTimeConverter.convert(
                testValue,
                columnName,
                errors,
                false);

        assertEquals(0, errors.size(), errors.stream().collect(Collectors.joining(",\n", "\n", "\n")));
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 12);
        assertEquals(expectedLocalDateTime, result);
    }


    @Test
    void test_valueFormanInvalid_resultAddErrorMsg() {
        String testValue = "2023-12-31T23:59:12";
        String columnName = "TEST";
        List<String> errors = new ArrayList<>();


        LocalDataTimeConverter localDataTimeConverter = new LocalDataTimeConverter();
        LocalDateTime result = localDataTimeConverter.convert(
                testValue,
                columnName,
                errors,
                false);

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '2023-12-31T23:59:12' не соответствует формату (yyyy.MM.dd HH24:mm:ss)",
                errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is null and required is true, result add error message to errors list")
    @Test
    void test_valueNullAndRequiredIsTrue_addErrorMsg() {
        String testValue = null;
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        LocalDataTimeConverter localDataTimeConverter = new LocalDataTimeConverter();
        LocalDateTime result = localDataTimeConverter.convert(
                testValue,
                columName,
                errors,
                true);

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение является обязательным и не должно быть пустым", errors.get(0));
        assertNull(result);
    }

    @DisplayName("Value is null and required is false, result add error message to errors list")
    @Test
    void test_valueNullAndRequiredIsFalse_addErrorMsg() {
        String testValue = null;
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        LocalDataTimeConverter localDataTimeConverter = new LocalDataTimeConverter();
        LocalDateTime result = localDataTimeConverter.convert(
                testValue,
                columName,
                errors,
                false);

        assertEquals(0, errors.size(), errors.stream().collect(Collectors.joining(",\n", "\n", "\n")));
        assertNull(result);
    }

    @DisplayName("Value is empty and required is false, result add error message to errors list")
    @Test
    void test_valueEmptyAndRequiredIsFalse_addErrorMsg() {
        String testValue = "";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        LocalDataTimeConverter localDataTimeConverter = new LocalDataTimeConverter();
        LocalDateTime result = localDataTimeConverter.convert(
                testValue,
                columName,
                errors,
                false);

        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '' не соответствует формату (yyyy.MM.dd HH24:mm:ss)",
                errors.get(0));
        assertNull(result);
    }
}
