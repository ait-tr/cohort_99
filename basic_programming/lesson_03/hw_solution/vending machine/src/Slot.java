import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Slot {

    private final int slotNumber;

    private List<Product> products = new ArrayList<>();

    public Slot(int slotNumber, List<Product> product) {
        this.slotNumber = slotNumber;
        this.products = product;
    }

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int addProduct(Product product, int count) {
        if (product == null) {
            this.products = new ArrayList<>();
        }
        int lastProductIndex = this.products.isEmpty() ? 0 : this.products.size() - 1;
        for (int i = lastProductIndex; i < lastProductIndex + count; i++) {
            this.products.add(product);
        }
        return this.products.size();
    }

    public int addProduct(Product product) {
        if (product == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(product);
        return this.products.size();
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public List<Product> getProducts() {
        return products;
    }
}
