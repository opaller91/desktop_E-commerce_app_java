package ku.cs.shop.models;

import javafx.scene.image.Image;
import ku.cs.shop.services.ShopListDataSource;
import ku.cs.shop.services.UserListDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shop {

    private String shopName;
    private String shopAddress;
    private String shopEmail;
    private String shopPhoneNumber;
    private String shopFacebook;
    private String shopInstagram;
    private String shopLine;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private Image picture;
    private User user;
    private int stockWarning;

    public Shop(String shopName,
                String shopAddress,
                String shopEmail,
                String shopPhoneNumber,
                String shopFacebook,
                String shopInstagram,
                String shopLine,
                String bankName,
                String accountName,
                String accountNumber,
                String username) {
        shopName = shopName.toLowerCase();
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopEmail = shopEmail;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopFacebook = shopFacebook;
        this.shopInstagram = shopInstagram;
        this.shopLine = shopLine;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountName = accountName;

        UserListDataSource userListDataSource = new UserListDataSource();
        UserList userList = userListDataSource.getUserList();
        this.user = userList.findUser(username);

    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public String getShopFacebook() {
        return shopFacebook;
    }

    public String getShopInstagram() {
        return shopInstagram;
    }

    public String getShopLine() {
        return shopLine;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public Image getPicture() {
        return picture;
    }

    public User getShopUser() {
        return user;
    }

    public String getShopUsername() {
        return user.getUsername();
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public void setShopFacebook(String shopFacebook) {
        this.shopFacebook = shopFacebook;
    }

    public void setShopInstagram(String shopInstagram) {
        this.shopInstagram = shopInstagram;
    }

    public void setShopLine(String shopLine) {
        this.shopLine = shopLine;
    }

    public void setAccountName(String accountName) { this.accountName = accountName; }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public void setImage(BufferedImage bi) {
        String directoryName = "assets" + File.separator +
                "images" + File.separator +
                "shop";
        String filepath = directoryName + File.separator +
                shopName + ".png";
        ShopListDataSource slData = new ShopListDataSource(false);
        slData.setImageFile(bi, directoryName, shopName + ".png");
        this.picture = slData.getImage(filepath);
    }




}


