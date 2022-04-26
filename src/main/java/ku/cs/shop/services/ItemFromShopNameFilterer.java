package ku.cs.shop.services;

import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;

import java.util.ArrayList;

public class ItemFromShopNameFilterer implements Filterer<ItemList>{
    private String fromShopName;

    public ItemFromShopNameFilterer(String fromShopName) {
        this.fromShopName = fromShopName;
    }

    @Override
    public ItemList filter(ItemList itemList) {
        ArrayList<Item> items = new ArrayList<>();
        items.clear();
        items.addAll(itemList.getAllItems());
        for (Item item : items) {
            if (!item.getShopName().contains(fromShopName)) {
                itemList.removeItem(item.getItemID());
            }
        }
        return itemList;
    }

    public boolean isFilterable(ItemList itemList){
        ArrayList<Item> items = new ArrayList<>();
        items.clear();
        items.addAll(itemList.getAllItems());
        for (Item item : items) {
            if (!item.getShopName().contains(fromShopName)) {
                itemList.removeItem(item.getItemID());
            }
        }
        if (itemList.getAllItems().isEmpty()){
            return false;
        }
       return true;
    }
}
