package ku.cs.shop.models.vouchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PriceVoucher extends Voucher{

    private double price;

    public PriceVoucher(String codename, double percent, String description, double price) {
        this(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")),price);
    }

    public PriceVoucher(String codename, double percent, String description, LocalDateTime expiryDate, double price) {
        super(codename,percent,description,expiryDate);
        this.price = price;
        this.type = "PriceVoucher";
    }

    public PriceVoucher(String codename, double percent, String description, String expiryDateStr, double price) {
        super(codename,percent,description,expiryDateStr);
        this.price = price;
        this.type = "PriceVoucher";
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean checkCondition(String shopName, double price, int amountOfItem) {
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("GMT+7")),getExpiryDate());
        return price >= this.price && !duration.isNegative();
    }

    @Override
    public String toCsv() {
        return "Price" + super.toCsv() + "," + price;
    }
}
