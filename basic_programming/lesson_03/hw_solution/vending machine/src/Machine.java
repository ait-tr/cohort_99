import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Machine {

    private final String name;
    private final List<Slot> slots;

    private final Balance balance;

    public Machine(String name, List<Slot> slots, Balance balance) {
        this.name = name;
        this.slots = slots;
        this.balance = balance;
    }

    public List<Slot> getEmptySlots() {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getProducts().isEmpty()) {
                result.add(slot);
            }
        }
        return result;
    }

    public int getNumberOfSlots() {
        return slots.size();
    }

    public String getName() {
        return name;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Balance getBalance() {
        return balance;
    }
}
