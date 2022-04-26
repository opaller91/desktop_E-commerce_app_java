package ku.cs.shop.models.vouchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Voucher {

    protected String type;
    private String codename;
    private double percent; // 0-1
    private String description; // no " , \n allowed, too lazy to implement a fix (or it.. didn't work)
    private LocalDateTime expiryDate;
    private String expiryDateStr;

    public Voucher(String codename, double percent, String description) { // new fresh voucher
        this(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")));
    }

    public Voucher(String codename, double percent, String description, LocalDateTime expiryDate) { // old voucher with specified expiry date
        this.codename = codename;
        this.percent = percent;
        this.description = description;
        this.expiryDate = expiryDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.expiryDateStr = formatter.format(expiryDate);
        this.type = "Voucher";
    }

    public Voucher(String codename, double percent, String description, String expiryDateStr) { // old voucher with specified expiry date str
        this.codename = codename;
        this.percent = percent;
        this.description = description;
        this.expiryDateStr = expiryDateStr;
        this.type = "Voucher";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.expiryDate = LocalDateTime.parse(expiryDateStr, formatter);
    }

    public String getCodename() {
        return codename;
    }

    public double getPercent() {
        return percent;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public String getType() {
        return type;
    }

    public boolean checkCondition(String shopName, double price, int amountOfItem) { // because you can use it on anything duh
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("GMT+7")),expiryDate);
        return !duration.isNegative();
    }

    public String toCsv() {
        String description = this.description.replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        return "Voucher," +
                codename + "," +
                percent + "," +
                description + "," +
                expiryDateStr;
    }

    public boolean checkExpiryTime() {
        Duration duration = Duration.between(LocalDateTime.now(ZoneId.of("GMT+7")),expiryDate);
        return !duration.isNegative();
    }
}
