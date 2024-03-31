import java.math.BigDecimal;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Parser {

    public Integer toInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели " + text + ". Введите число");
            return null;
        }
    }

    public BigDecimal toBigDezimel(String text) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(text));
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели " + text + ". Введите значение по шаблону: #.##, 9.99, 13.5, 4.09, 1234");
            return null;
        }
    }
}
