package de.ait;

import de.ait.models.OperationTyp;
import de.ait.models.TransaktionCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
public class TransaktionCodeConverter extends Converter<TransaktionCode> {

    private static final String ERROR_MSG_NOT_FOUND = "Колонка '%s', значение '%s' код операции не найден. Доступные типы по ключам [%s]";


    @Override
    public TransaktionCode convert(String value, //"1 - 3"
                                   String columnName,
                                   List<String> errors,
                                   boolean isRequired) {

        Converter<Integer> intConverter = new IntConverter();
        Integer transaktionCodeKey = intConverter.convert(
                value,
                columnName,
                errors,
                isRequired
        );

        //isRequired = true, value = null   -> Integer result = null, errors.size() == 1
        //isRequired = false, value = null  -> Integer result = null, errors.size() == 0
        //isRequired = false, value = "123" -> Integer result = 123, errors.size() == 0
        //isRequired = false, value = "ABC" -> Integer result = null, errors.size() == 1
        //isRequired = true, value = "ABC"  -> Integer result = null, errors.size() == 1

        if (transaktionCodeKey == null) {
            return null;
        }

        TransaktionCode transaktionCode = null;

        for (TransaktionCode typ : TransaktionCode.values()) {
            if (typ.getKey() == transaktionCodeKey) {
                transaktionCode = typ;
            }
        }

        if (transaktionCode == null) {
            String availableIds = Arrays.stream(TransaktionCode.values())
                    .map(x -> x.getKey())
                    .map(x -> String.valueOf(x))
                    .collect(Collectors.joining(", "));
            String msg = String.format(ERROR_MSG_NOT_FOUND, columnName, value, availableIds);
            System.err.print(msg);
            errors.add(msg);
        }

        return transaktionCode;
    }
}
