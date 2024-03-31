/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public enum PayMethod {

    CARD(1, "Карта"),
    CASH(2, "Наличные");

    private final int id;
    private final String description;

    PayMethod(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + ", " + description;
    }
}
