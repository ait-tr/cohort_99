package de.ait;

import de.ait.models.OperationTyp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrej Reutow
 * created on 17.06.2023
 */
public class OperationTypConverter extends Converter<OperationTyp> {

    private static final String ERROR_MSG_NOT_FOUND = "Колонка '%s', значение '%s' тип операции не найден. Доступные типы по ключам [%s]";


    @Override
    public OperationTyp convert(String value, //"1 - 3"
                                String columnName,
                                List<String> errors,
                                boolean isRequired) {

        Converter<Integer> intConverter = new IntConverter();
        Integer operationTypKey = intConverter.convert(
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

        if (operationTypKey == null) {
            return null;
        }

        OperationTyp operationTyp = null;
//        operationTyp = Arrays.stream(OperationTyp.values())
//                .filter(ot -> ot.getKey() == operationTypKey)
//                .findFirst()
//                .orElse(null);


        for (OperationTyp typ : OperationTyp.values()) {
            if (typ.getKey() == operationTypKey) {
                operationTyp = typ;
            }
        }

        if (operationTyp == null) {
            String availableIds = Arrays.stream(OperationTyp.values())
                    .map(x -> x.getKey())
                    .map(x -> String.valueOf(x))
//                    .map(OperationTyp::getKey)
//                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")); // 1, 2, 3
            String msg = String.format(ERROR_MSG_NOT_FOUND, columnName, value, availableIds);
            System.err.print(msg);
            errors.add(msg);
        }

        return operationTyp;
    }
}
