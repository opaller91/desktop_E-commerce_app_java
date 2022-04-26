package ku.cs.shop.models.vouchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShopVoucher extends Voucher {

    private String shopName;

    public ShopVoucher(String codename, double percent, String description, String shopName) {
        this(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")),shopName);
    }

    public ShopVoucher(String codename, double percent, String description, LocalDateTime expiryDate, String shopName) {
        super(codename,percent,description,expiryDate);
        this.shopName = shopName;
        this.type = "ShopVoucher";
    }

    public ShopVoucher(String codename, double percent, String description, String expiryDateStr, String shopName) {
        super(codename,percent,description,expiryDateStr);
        this.shopName = shopName;
        this.type = "ShopVoucher";
    }

    public String getShopName() {
        return shopName;
    }

    @Override
    public boolean checkCondition(String shopName, double price, int amountOfItem) {
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("GMT+7")),getExpiryDate());
        return shopName.equals(this.shopName) && !duration.isNegative();
    }

    @Override
    public String toCsv() {
        return "Shop" + super.toCsv() + "," + shopName;
    }
}
