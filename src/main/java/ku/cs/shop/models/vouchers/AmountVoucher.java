package ku.cs.shop.models.vouchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AmountVoucher extends Voucher{

    private int amountOfItem;

    public AmountVoucher(String codename, double percent, String description, int amountOfItem) {
        this(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")),amountOfItem);
    }

    public AmountVoucher(String codename, double percent, String description, LocalDateTime expiryDate, int amountOfItem) {
        super(codename,percent,description,expiryDate);
        this.amountOfItem = amountOfItem;
        this.type = "AmountVoucher";
    }

    public AmountVoucher(String codename, double percent, String description, String expiryDateStr, int amountOfItem) {
        super(codename,percent,description,expiryDateStr);
        this.amountOfItem = amountOfItem;
        this.type = "AmountVoucher";
    }

    public int getAmountOfItem() {
        return amountOfItem;
    }

    @Override
    public boolean checkCondition(String shopName, double price, int amountOfItem) {
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("GMT+7")),getExpiryDate());
        return amountOfItem >= this.amountOfItem && !duration.isNegative();
    }

    @Override
    public String toCsv() {
        return "Amount" + super.toCsv() + "," + amountOfItem;
    }
}
