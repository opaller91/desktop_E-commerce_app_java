package ku.cs.shop.models.vouchers;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShopAmountVoucher extends ShopVoucher {

    private int amountOfItem;

    public ShopAmountVoucher(String codename, double percent, String description, LocalDateTime expiryDate, String shopName, int amountOfItem) {
        super(codename,percent,description,expiryDate,shopName);
        this.amountOfItem = amountOfItem;
        this.type = "ShopAmountVoucher";
    }

    public ShopAmountVoucher(String codename, double percent, String description, String expiryDateStr, String shopName, int amountOfItem) {
        super(codename,percent,description,expiryDateStr,shopName);
        this.amountOfItem = amountOfItem;
        this.type = "ShopAmountVoucher";
    }

    public int getAmountOfItem() {
        return amountOfItem;
    }

    @Override
    public boolean checkCondition(String shopName, double price, int amountOfItem) {
        return super.checkCondition(shopName,price,amountOfItem) && (amountOfItem >= this.amountOfItem);
    }

    @Override
    public String toCsv() {
        return "Amount" + super.toCsv() + "," + amountOfItem;
    }
}
