import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Session {

    private List<Machine> machines;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Session(List<Machine> machines) {
        this.machines = machines;
        for (Machine machine : machines) {
            init(machine);
        }
        startTime = LocalDateTime.now();
    }

    public void endSession() {
        this.endTime = LocalDateTime.now();
        printReport();
        System.out.println("Machines stopped on " + endTime);
    }

    private void printReport() {
        for (Machine machine : machines) {
            checkBalance(machine);
        }
    }

    private void init(Machine machine) {
        System.out.println("-".repeat(50));
        System.out.println("Init Machine " + machine.getName());

        try {
            for (int i = 0; i <= 100; i++) {
                System.out.print("\rLoading: " + i + "%");
                Thread.sleep(new Random().nextLong(10));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        checkSlots(machine);
        checkBalance(machine);

        System.out.println("Machine " + machine.getName() + " ready for work");
        System.out.println("_".repeat(50));
    }

    private void checkBalance(Machine machine) {
        System.out.println("#".repeat(30));

        System.out.println("Check balance:");
        System.out.println("В аппарате при старте " + machine.getName() + " зафиксировано " + machine.getBalance().getInitCash() + " денег");
        System.out.println("На данный момент " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyy hh:mm:ss")) + " продано на сумму: " + machine.getBalance().getSoldCash().add(machine.getBalance().getSoldCard()));
        System.out.println("Оплачено " + PayMethod.CASH.getDescription() + ": " + machine.getBalance().getSoldCash());
        System.out.println("Оплачено " + PayMethod.CARD.getDescription() + ": " + machine.getBalance().getSoldCard());

        System.out.println("#".repeat(30));
    }

    public void checkSlots(Machine machine) {
        List<Slot> slots = machine.getSlots();
        System.out.println("#".repeat(30));
        System.out.println("Check Slots:");
        System.out.println("Total Slots Detected: " + slots.size());
        for (Slot slot : slots) {
            if (slot.getProducts().isEmpty()) {
                System.out.println("Slot: " + slot.getSlotNumber() + " Empty");
            } else {
                System.out.println("Slot: " + slot.getSlotNumber() + " has " + slot.getProducts().size() + " Items");
            }
        }
        System.out.println("Check Slots END");
        System.out.println("#".repeat(30));
    }
}
