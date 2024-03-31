import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @author Andrej Reutow
 * created on 29.01.2023
 */
public class Balance {

    private final BigDecimal initCash;
    private BigDecimal soldCash = BigDecimal.ZERO;
    private BigDecimal soldCard = BigDecimal.ZERO;

    public Balance() {
        double initBalance = new Random().nextDouble(1000);
        this.initCash = BigDecimal.valueOf(initBalance).setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getInitCash() {
        return initCash;
    }

    public BigDecimal getSoldCash() {
        return soldCash;
    }

    public BigDecimal getSoldCard() {
        return soldCard;
    }

    public void sold(BigDecimal soldPrice, PayMethod payMethod) {
        if (PayMethod.CARD.equals(payMethod)) {
            this.soldCard = this.soldCard.add(soldPrice);
        } else if (PayMethod.CASH.equals(payMethod)) {
            this.soldCash = this.soldCash.add(soldPrice);
        }
    }
}
