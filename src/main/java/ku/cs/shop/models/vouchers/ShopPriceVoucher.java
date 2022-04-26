package ku.cs.shop.models.vouchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShopPriceVoucher extends ShopVoucher{

    private double price;

    public ShopPriceVoucher(String codename, double percent, String description, LocalDateTime expiryDate, String shopName, double price) {
        super(codename,percent,description,expiryDate,shopName);
        this.price = price;
        this.type = "ShopPriceVoucher";
    }

    public ShopPriceVoucher(String codename, double percent, String description, String expiryDateStr, String shopName, double price) {
        super(codename,percent,description,expiryDateStr,shopName);
        this.price = price;
        this.type = "ShopPriceVoucher";
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean checkCondition(String shopName, double price, int amountOfItem) {
        return super.checkCondition(shopName,price,amountOfItem) && (price >= this.price);
    }

    @Override
    public String toCsv() {
        return "Price" + super.toCsv() + "," + price;
    }
}
