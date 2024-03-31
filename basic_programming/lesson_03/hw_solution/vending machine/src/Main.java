import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrej Reutow
 * created on ${DATE}
 */
public class Main {
    private static final InputReader inputReader = new InputReader();

    public static void main(String[] args) {
        List<Slot> slots = fillMachineWithSlots(9);
        slots.get(0).addProduct(new Product("Вода", BigDecimal.valueOf(3.5)), 10);
        slots.get(2).addProduct(new Product("Cola", BigDecimal.valueOf(5.65)), 5);
        Balance balance1 = new Balance();
        Machine machine1 = new Machine("First Machine", slots, balance1);

        Session session = new Session(List.of(machine1));

        System.out.println(WlcomeScreen.WELCOME);
        while (true) {
            System.out.printf((WlcomeScreen.SELECT_PRODUCT) + "%n", 1, slots.size());
            int slotNumber = selectSlot();
            Slot slot = machine1.getSlots().get(slotNumber - 1);
            if (slot.getProducts().isEmpty()) {
                System.out.printf(WlcomeScreen.SLOT_IS_EMPTY);
            } else {
                Product product = slot.getProducts().get(0);
//                System.out.println("Выбранный слот " + slotNumber);
                System.out.println("Вы хотите купить " + product.getName() + ", Цена: " + product.getPrice());
                PayMethod payMethod = buyProductSelectMethod();
                buyProductPay(product.getPrice(), payMethod, machine1.getBalance());
                slot.getProducts().remove(slot.getProducts().size() - 1);
            }
            if (inputReader.scanner.next().equals("q")) {
                break;
            }
        }

        session.checkSlots(machine1);
        session.endSession();
        System.out.println("END");
    }

    private static void buyProductPay(BigDecimal price, PayMethod payMethod, Balance balance) {
        System.out.println("К оплате " + price);
        if (PayMethod.CARD.equals(payMethod)) {
            System.out.println("Приложите свою карту к терминалу и следуйте инструкциям терминала");
        } else if (PayMethod.CASH.equals(payMethod)) {
            System.out.println("С вас " + price);
            System.out.println("Введите сумму оплаты");

            boolean isSold = false;

            while (!isSold) {
                BigDecimal bigDecimal = inputReader.readPrice();
                System.out.println("Вы заплатили " + bigDecimal);
                if (bigDecimal.compareTo(price) == 0) {
                    System.out.println("Спасибо за покупку, получите свой товар");
                    isSold = true;
                } else if (bigDecimal.compareTo(price) < 0) {
                    System.out.println("Вы заплатили не достаточно. Не хватает: " + price.subtract(bigDecimal));
                    System.out.println("повторите последнее действие");
                } else if (bigDecimal.compareTo(price) > 0) {
                    System.out.println("Ваша сдача: " + bigDecimal.subtract(price));
                    isSold = true;
                }
            }
        }
        balance.sold(price, payMethod);
    }

    private static PayMethod buyProductSelectMethod() {
        System.out.println("Способ оплаты");
        List<Integer> allowedMethodsId = new ArrayList<>();
        for (PayMethod value : PayMethod.values()) {
            allowedMethodsId.add(value.getId());
            System.out.println(value.getId() + " для " + value.getDescription());
        }
        PayMethod payMethod = null;
        while (payMethod == null) {
            Integer integer = inputReader.readNumber(allowedMethodsId);
            for (PayMethod value : PayMethod.values()) {
                if (value.getId() == integer) {
                    payMethod = value;
                    System.out.println("Выбранный способ оплаты " + payMethod.getDescription());
                    break;
                }
            }
        }
        return payMethod;
    }

    public static int selectSlot() {
        return inputReader.readNumber();
    }

    private static List<Slot> fillMachineWithSlots(int numberOfSlots) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < numberOfSlots; i++) {
            slots.add(new Slot(i + 1));
        }
        return slots;
    }
}
