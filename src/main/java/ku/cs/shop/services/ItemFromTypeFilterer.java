package ku.cs.shop.services;

import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;

import java.util.ArrayList;

public class ItemFromTypeFilterer implements Filterer<ItemList>{

    private String fromType;

    public ItemFromTypeFilterer(String fromType1) {
        this.fromType = fromType1;
    }

    @Override
    public ItemList filter(ItemList itemList) {
        ArrayList<Item> items = new ArrayList<>();
        items.clear();
        items.addAll(itemList.getAllItems());
        for (Item item: items){
            if (!item.getItemType().equals(fromType)){
                itemList.removeItem(item.getItemID());
            }
        }
        return itemList;
    }

}
