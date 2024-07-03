package de.ait;

import java.util.List;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
public class DoubleConverter extends Converter<Double> {
    private static final String ERROR_MSG = "Колонка '%s', значение '%s' не является дробным числом";

    @Override
    public Double convert(String value,
                          String columnName,
                          List<String> errors,
                          boolean isRequired) {
        if (checkIsRequired(value, columnName, errors, isRequired)) {
            return null;
        }

        if (value == null) {
            return null;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
            errors.add(String.format(ERROR_MSG, columnName, value));
            return null;
        }
    }
}
