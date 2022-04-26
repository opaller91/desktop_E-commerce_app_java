package ku.cs.shop.models;

import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.UserListDataSource;

public class Order {

    private String orderID;
    private int amountOfItems;
    private String address;
    private String username;
    private String fullname;
    private User user;
    private String itemID;
    private Item item;
    private String shopName;
    private Shop shop;
    private boolean isShipped;

    public Order(String orderID, int amountOfItems, String address,String username,String fullName, String itemID, String shopName, boolean isShipped) {
        this.orderID = orderID;
        this.amountOfItems = amountOfItems;
        this.address = address;
        this.username = username;
        this.fullname = fullName;
        this.itemID = itemID;
        this.shopName = shopName;
        this.isShipped = isShipped;

        ItemListDataSource ilData = new ItemListDataSource();
        ItemList il = ilData.getItemList();
        this.item = il.findID(itemID);

        UserListDataSource ulData = new UserListDataSource();
        UserList ul = ulData.getUserList();
        this.user = ul.findUser(username);
    }

    public Item getItem() {
        return item;
    }

    public String getOrderID() {
        return orderID;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getItemID() {
        return itemID;
    }

    public String getShopName() {
        return shopName;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return fullname + " [ID: " + orderID + "]";
    }

    public String getFullname() {
        return fullname;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }
}
