package ku.cs.shop.models;

import javafx.scene.image.Image;
import ku.cs.shop.services.ShopListDataSource;
import ku.cs.shop.services.UserListDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class User implements Comparable{
    private String fullName;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String shopName;
    private Image picture;
    private Shop shop;
    private String lastOnlineTimeStr;
    private boolean isAdmin;
    private boolean isBanned;
    private LocalDateTime lastLogin;
//    private BankAccount []banks;

    public User(String fullName,
                String username,
                String phoneNumber,
                String email,
                String password,
                String shopName,
                boolean isAdmin,
                boolean isBanned) {
        username = username.toLowerCase();
        this.fullName = fullName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.shopName = shopName;
        this.isBanned = isBanned;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getShopName() {
        return shopName;
    }

    public String getLastOnlineTimeStr() {
        return lastOnlineTimeStr;
    }

    public void setOnlineTime() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+7"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.lastOnlineTimeStr = now.format(formatter);
    }

    public Image getImage() {
        return picture;
    }

    public Shop getShop() {
        return shop;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setShop() {
        ShopListDataSource shopListDataSource = new ShopListDataSource();
        ShopList shopList = shopListDataSource.getShopList();
        this.shop = shopList.findShop(shopName);
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setImage(BufferedImage bi) {
        String directoryName = "assets" + File.separator +
                "images" + File.separator +
                "avatar";

        String filepath = directoryName + File.separator +
                username + ".png";
        UserListDataSource ulData = new UserListDataSource(false);
        ulData.setImageFile(bi, directoryName, username + ".png");
        this.picture = ulData.getImage(filepath);
    }

    @Override
    public String toString() {
        String shopName;
        if (this.shop == null) {
            shopName = "null";
        } else {
            shopName = this.shopName;
        }
        return lastOnlineTimeStr + " " + fullName + " [" + username + "]";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public void setLastOnlineTimeStr(String lastOnlineTimeStr) {
        this.lastOnlineTimeStr = lastOnlineTimeStr;
    }


    public void setLastLogin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(lastOnlineTimeStr, formatter);
        this.lastLogin = dateTime;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public int compareTo(Object o) {
        User s = (User) o;
        Duration diffTime = Duration.between(this.getLastLogin(),s.getLastLogin());
        double diff = diffTime.toSeconds();
        return diff > 0? 1:
                diff == 0? 0:-1;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
