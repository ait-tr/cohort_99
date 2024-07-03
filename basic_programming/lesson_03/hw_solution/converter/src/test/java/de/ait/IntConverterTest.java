package de.ait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
class IntConverterTest {

    @Test
    void test_valueMaxLong_addErrorMsg() {
        String testValue = "9223372036854775807";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        IntConverter intConverter = new IntConverter();
        Integer result = intConverter.convert(
                testValue,
                columName,
                errors,
                false
        );


        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '9223372036854775807' должно быть между -2147483648 и 2147483647",
                errors.get(0));
        assertNull(result);
    }

    @Test
    void test_valueMinLong_addErrorMsg() {
        String testValue = "-9223372036854775808";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        IntConverter intConverter = new IntConverter();
        Integer result = intConverter.convert(
                testValue,
                columName,
                errors,
                false
        );


        assertEquals(1, errors.size());
        assertEquals("Колонка 'TEST', значение '-9223372036854775808' должно быть между -2147483648 и 2147483647",
                errors.get(0));
        assertNull(result);
    }


    @Test
    void test_valueMaxInt_resultValidInteger() {
        String testValue = "2147483647";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        IntConverter intConverter = new IntConverter();
        Integer result = intConverter.convert(
                testValue,
                columName,
                errors,
                false
        );


        assertEquals(0, errors.size(), errors.stream().collect(Collectors.joining(",\n", "\n", "\n")));
        assertEquals(2147483647, result);
    }

    @Test
    void test_valueMinInt_resultValidInteger() {
        String testValue = "-2147483648";
        String columName = "TEST";
        List<String> errors = new ArrayList<>();

        IntConverter intConverter = new IntConverter();
        Integer result = intConverter.convert(
                testValue,
                columName,
                errors,
                false
        );


        assertEquals(0, errors.size(), errors.stream().collect(Collectors.joining(",\n", "\n", "\n")));
        assertEquals(-2147483648, result);
    }
}
