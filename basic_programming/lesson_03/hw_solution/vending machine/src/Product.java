import java.math.BigDecimal;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Product {
    private final String name;
    private final BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
