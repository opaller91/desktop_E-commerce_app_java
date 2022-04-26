package ku.cs.shop.models;

import java.util.ArrayList;

public class ShopList {

    private ArrayList<Shop> shopList;

    public ShopList() {
        shopList = new ArrayList<>();
    }

    public void addShop(Shop shop) {
        shopList.add(shop);
    }

    public ArrayList<Shop> getAllShops() {
        return shopList;
    }

    public Shop findShop(String shopName) {
        Shop shop = null;
        if (shopName.equals("null")) {
            return null;
        }
        for (Shop temp : shopList) {
            if (shopName.equals(temp.getShopName())) {
                shop = temp;
            }
        }
        return shop;
    }

    public void setAllShopImage() {
        for (Shop temp : shopList) {
            temp.setImage(null);
        }
    }

}
