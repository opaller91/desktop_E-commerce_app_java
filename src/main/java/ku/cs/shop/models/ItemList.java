package ku.cs.shop.models;

import ku.cs.shop.services.ConditionFilterer;
import ku.cs.shop.services.Filterer;
import ku.cs.shop.services.ItemListDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItemList {

    private ArrayList<Item> itemList;

    public ItemList(){
        itemList = new ArrayList<>();
    }

    public Item max(Comparator<Item> itemComparator){
        if(itemList.isEmpty()) return null;
        Item max = itemList.get(0);
        for(Item item : itemList){
            if(itemComparator.compare(item,max) > 0){
                max = item;
            }
        }
        return  max;
    }
    //-----------Sort--------------
    public void sort(Comparator<Item> itemComparator){
        Collections.sort(this.itemList,itemComparator);
    }

    public ArrayList<Item> filter(Filterer<ItemList> filterer){
        return filterer.filter(this).getAllItems();
    }

    public ArrayList<Item> filter(ConditionFilterer<Item> itemConditionFilterer) {
        ArrayList<Item> filtered = new ArrayList<>();
        for (Item item : this.itemList) {
            if (itemConditionFilterer.check(item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
    public void addItem(Item item){
        //เรียก method add จาก ArrayList เพื่อเพิ่มข้อมูล
        itemList.add(item);
    }

    public ArrayList<Item> getAllItems() {
        return itemList;
    }

    public Item findID(String itemID){
        Item item = null;
        if(itemID.equals("null")){
            return null;
        }
        for(Item temp : itemList){
            if(itemID.equals(temp.getItemID())){
                item = temp;
                break;
            }
        }
        return item;
    }

    public void removeItem(String itemID) {
        Item item = findID(itemID);
        itemList.remove(item);
    }

    public int findNextItemID() {
        int i = 1;
        boolean idFound = false;
        for (; i < itemList.size(); i++) { // loop ids
            idFound = false; // set to false
            for (Item temp : itemList) { // search all items
                if (Integer.parseInt(temp.getItemID()) == i) { // if id exists
                    idFound = true; // found id
                    break; // go to next id
                }
            }
            if (!idFound) { // if there's no id
                return i; // return the id
            }
        }
        return ++i; // if 1-n ids are taken, send the next one
    }

    public String toCsv() {
        String result = "";
        for (Item item : this.itemList) {
            result += item.toCsv() + "\n";
        }
        return result;
    }

    public void filterBannedItems() {
        ArrayList<Item> temp = new ArrayList<>();
        for (Item item : itemList) {
            if (!item.isBanned()) {
                temp.add(item);
            }
        }
        itemList = temp;
    }

}
