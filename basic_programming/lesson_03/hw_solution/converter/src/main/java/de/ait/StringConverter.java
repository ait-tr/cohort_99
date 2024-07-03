package de.ait;

import java.util.List;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
public class StringConverter extends Converter<String> {

    @Override
    public String convert(String value,
                          String columnName,
                          List<String> errors,
                          boolean isRequired) {

        if (checkIsRequired(value, columnName, errors, isRequired)) {
            return null;
        }

        return value;
    }
}
