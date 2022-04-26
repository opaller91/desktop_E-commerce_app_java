package ku.cs.shop.models;

import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.ShopListDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item{
    private String itemName;
    private String itemDescription;
    private String itemType;
    private double itemPrice;
    private int itemStock;
    private int stockWarning;
    private Image itemImage;
    private String itemID;
    private String shopName;
    private Shop shop;
    private boolean isBanned;

    public Item(String itemID,String shopName,String itemName, String itemDescription, double itemPrice, int itemStock,int stockWarning,String itemType,boolean isBanned) {
        this.itemID = itemID;
        this.shopName = shopName;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.stockWarning = stockWarning;
        this.isBanned = isBanned;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemStock() {
        return itemStock;
    }

    public Image getPicture() {
        return itemImage;
    }

    public int getStockWarning(){return stockWarning;}

    public String getItemType() {
        return itemType;
    }

    public String getItemID() {
        return itemID;
    }

    public void setImage(BufferedImage bi) {
        String directoryName = "assets" + File.separator +
                "images" + File.separator +
                "item";
        String filepath = directoryName + File.separator +
                itemID + ".png";
        ItemListDataSource ilData = new ItemListDataSource(false);
        ilData.setImageFile(bi, directoryName, itemID + ".png");
        this.itemImage = ilData.getImage(filepath);
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }

    public void setStockWarning(int stockWarning){this.stockWarning = stockWarning;}

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getShopName() {
        return shopName;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Image getItemImage() {
        return itemImage;
    }

    @Override
    public String toString() {
        return  itemID + ',' +
                shopName + ',' +
                itemName + ',' +
                itemDescription + ',' +
                itemType + ',' +
                itemPrice + ',' +
                itemStock + ',' +
                stockWarning + ',' +
                isBanned;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String toCsv() {
        return "Item : " + itemID + "," + itemName + "," + itemType + "," + itemPrice ;
    }

    public void sold(int amount) {
        this.itemStock -= amount;
    }
}
