package ku.cs.shop.models;

import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.ItemListDataSource;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ItemListTest {
    @Test
    void testSortItemPriceLtoH(){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();

        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemPrice() > o2.getItemPrice() ) return 1;
                if(o1.getItemPrice() < o2.getItemPrice()) return -1;
                return 0;
            }
        });
        System.out.println(itemList.toCsv());

    }

    @Test
    void testSortItemPriceHtoL(){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();
        ArrayList<Item> items;

        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemPrice() < o2.getItemPrice() ) return 1;
                if(o1.getItemPrice() > o2.getItemPrice()) return -1;
                return 0;
            }
        });
        System.out.println(itemList.toCsv());

    }

    @Test
    void testSortItemNewToOld(){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();
        ArrayList<Item> items;

        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemID().compareTo(o2.getItemID()) < 0) return 1;
                if(o1.getItemID().compareTo(o2.getItemID()) > 0) return -1;
                return 0;
            }
        });

    }

    @Test
    void test(){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();
        ArrayList<Item> itemLists = itemList.getAllItems();
        ArrayList<Item> items = new ArrayList<>();

        for (Item item: itemLists){
            if (item.getItemType().equals("Other")){
                items.add(item);
            }

        }
        items.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemPrice() > o2.getItemPrice() ) return 1;
                if(o1.getItemPrice() < o2.getItemPrice()) return -1;
                return 0;
            }
        });
        System.out.println(items);
//        ItemList itemList = null;
//        System.out.println(itemList);


    }


}