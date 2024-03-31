import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class InputReader {

    private Parser parser = new Parser();
    Scanner scanner = new Scanner(System.in);


    public Integer readNumber() {
        Integer number = null;
        while (number == null) {
            String text = scanner.next();
            if (text.equalsIgnoreCase("q")) {
                break;
            }
            number = parser.toInt(text);
        }
        return number;
    }

    public BigDecimal readPrice() {
        BigDecimal price = null;
        while (price == null) {
            String text = scanner.next();
            if (text.equalsIgnoreCase("q")) {
                break;
            }
            price = parser.toBigDezimel(text);
        }
        return price;
    }

    public Integer readNumber(List<Integer> allowedMethodsId) {
        Integer integer = readNumber();
        while (!allowedMethodsId.contains(integer)) {
            String text = scanner.next();
            if (text.equalsIgnoreCase("q")) {
                break;
            } else {
                System.out.println("Введенй способ олаты не существует. Попробуйте еще раз");
                integer = readNumber();
            }
        }
        return integer;
    }
}
