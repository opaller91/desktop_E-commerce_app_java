package ku.cs.market.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import ku.cs.shop.controllers.MarketSceneController;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.services.ItemFromShopNameFilterer;
import ku.cs.shop.services.ItemFromTypeFilterer;
import ku.cs.shop.services.ItemListDataSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

public class FilterBarController {

    @FXML
    private JFXCheckBox electronicsCheckBox;
    @FXML
    private JFXCheckBox clothingCheckBox;
    @FXML
    private JFXCheckBox utensilsCheckBox;
    @FXML
    private JFXCheckBox otherCheckBox;
    @FXML
    private JFXRadioButton priceLowToHighCheckbox;
    @FXML
    private JFXRadioButton priceHighToLowCheckbox;
    @FXML
    private TextField lowPriceTextField;
    @FXML
    private TextField highPriceTextField;
    @FXML
    private TextField shopNameTextField;
    @FXML
    private ToggleGroup price;

    Information info;
    ItemList itemList;
    MarketSceneController marketSceneController = null;
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> itemArrayList = new ArrayList<>();
    String error = "";

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }


    public void setMarketSceneController(MarketSceneController marketSceneController) {
        this.marketSceneController = marketSceneController;
    }

    public void setInfo(Information info){
        this.info = info;
    }

    public ItemList chooseSortHighLow(){
        String shopName = shopNameTextField.getText();
        shopName = shopName.toLowerCase();
        String highPrice = highPriceTextField.getText();
        String lowPrice = lowPriceTextField.getText();
        if (priceHighToLowCheckbox.isSelected()){
            error = "";
            ItemListDataSource dataSource = new ItemListDataSource();
            this.itemList = dataSource.getItemList();
            filterBannedItems(itemList);
            priceHighToLow(itemList);
            priceLowToHighCheckbox.setSelected(false);
        }

        if (priceLowToHighCheckbox.isSelected()){
            error = "";
            ItemListDataSource dataSource = new ItemListDataSource();
            this.itemList = dataSource.getItemList();
            filterBannedItems(itemList);
            priceLowToHigh(itemList);
            priceHighToLowCheckbox.setSelected(false);
        }


        if (!highPrice.isEmpty() && !lowPrice.isEmpty()){
            ItemListDataSource dataSource = new ItemListDataSource();
            this.itemList = dataSource.getItemList();
            filterBannedItems(itemList);
            rangePrice(itemList);
            if (error.equals("price")){
                return itemList;
            }
            else {
                error = "";
            }
            if (priceHighToLowCheckbox.isSelected()){
                this.itemList = priceHighToLow(itemList);
                priceLowToHighCheckbox.setSelected(false);
            }
            if (priceLowToHighCheckbox.isSelected()){
                this.itemList = priceLowToHigh(itemList);
                priceHighToLowCheckbox.setSelected(false);
            }
            if(!shopName.isEmpty()){
                ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
                if (!shop.isFilterable(itemList)){
                    error = "shopName";
                }
                else{
                    error = "";
                    itemList.filter(shop);
                }
                newToOld(itemList);
            }
        }

        if(!shopName.isEmpty()){
            ItemListDataSource dataSource = new ItemListDataSource();
            this.itemList = dataSource.getItemList();
            filterBannedItems(itemList);
            ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
            if (!shop.isFilterable(itemList)){
                error = "shopName";
            }
            else {
                error="";
                itemList.filter(shop);
            }
            newToOld(itemList);
            if (priceHighToLowCheckbox.isSelected()) {
                priceHighToLow(itemList);
                priceLowToHighCheckbox.setSelected(false);
            }
            if (priceLowToHighCheckbox.isSelected()) {
                priceLowToHigh(itemList);
                priceHighToLowCheckbox.setSelected(false);
            }
        }

        if (electronicsCheckBox.isSelected()){
            error="";
            ItemListDataSource dataSource = new ItemListDataSource();
            this.itemList = dataSource.getItemList();
            itemList.filter(new ItemFromTypeFilterer("Electronics"));
            if(!shopName.isEmpty()){
                ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
                if (!shop.isFilterable(itemList)){
                    error = "shopName";
                    return itemList;
                }
                else{
                    error = "";
                    itemList.filter(shop);
                }
            }
            filterBannedItems(itemList);
            newToOld(itemList);
            if (!highPrice.isEmpty() && !lowPrice.isEmpty()){
                rangePrice(itemList);
            }
            if (priceHighToLowCheckbox.isSelected()){
                priceHighToLow(itemList);
            }
            if (priceLowToHighCheckbox.isSelected()){
                priceLowToHigh(itemList);
            }

        }

        if (clothingCheckBox.isSelected()){
            error="";
            if (electronicsCheckBox.isSelected()){
               sortType("Clothing");
            }
            else{
                ItemListDataSource dataSource = new ItemListDataSource();
                this.itemList = dataSource.getItemList();
                itemList.filter(new ItemFromTypeFilterer("Clothing"));
            }
            if(!shopName.isEmpty()){
                ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
                if (!shop.isFilterable(itemList)){
                    error = "shopName";
                    return itemList;
                }
                else{
                    error = "";
                    itemList.filter(shop);
                }
            }
            filterBannedItems(itemList);
            newToOld(itemList);
            if (!highPrice.isEmpty() && !lowPrice.isEmpty()){
                rangePrice(itemList);
            }
            if (priceHighToLowCheckbox.isSelected()){
                priceHighToLow(itemList);
            }
            if (priceLowToHighCheckbox.isSelected()){
                priceLowToHigh(itemList);
            }

        }

        if (utensilsCheckBox.isSelected()){
            error="";
            if (electronicsCheckBox.isSelected() || clothingCheckBox.isSelected()){
               sortType("Utensils");
            }
            else{
                ItemListDataSource dataSource = new ItemListDataSource();
                this.itemList = dataSource.getItemList();
                itemList.filter(new ItemFromTypeFilterer("Utensils"));
            }
            if(!shopName.isEmpty()){
                ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
                if (!shop.isFilterable(itemList)){
                    error = "shopName";
                    return itemList;
                }
                else{
                    error = "";
                    itemList.filter(shop);
                }
            }
            filterBannedItems(itemList);
            newToOld(itemList);
            if (!highPrice.isEmpty() && !lowPrice.isEmpty()){
                rangePrice(itemList);
            }
            if (priceHighToLowCheckbox.isSelected()){
                priceHighToLow(itemList);
            }
            if (priceLowToHighCheckbox.isSelected()){
                priceLowToHigh(itemList);
            }
        }

        if (otherCheckBox.isSelected()) {
            error="";
            if (electronicsCheckBox.isSelected() || clothingCheckBox.isSelected() || utensilsCheckBox.isSelected()){
                sortType("Other");
            }
            else{
                ItemListDataSource dataSource = new ItemListDataSource();
                this.itemList = dataSource.getItemList();
                itemList.filter(new ItemFromTypeFilterer("Other"));
            }
            filterBannedItems(itemList);
            if(!shopName.isEmpty()){
                ItemFromShopNameFilterer shop = new ItemFromShopNameFilterer(shopName);
                if (!shop.isFilterable(itemList)){
                    error = "shopName";
                    return itemList;
                }
                else {
                    error = "";
                    itemList.filter(shop);
                }
            }
            newToOld(itemList);
            if (!highPrice.isEmpty() && !lowPrice.isEmpty()){
                rangePrice(itemList);
            }
            if (priceHighToLowCheckbox.isSelected()) {
                priceHighToLow(itemList);
            }
            if (priceLowToHighCheckbox.isSelected()) {
                priceLowToHigh(itemList);
            }
        }
        return itemList;
    }

    public ItemList priceHighToLow(ItemList itemList){
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemPrice() < o2.getItemPrice() ) return 1;
                if(o1.getItemPrice() > o2.getItemPrice()) return -1;
                return 0;
            }
        });
        return this.itemList = itemList;
    }

    public ItemList priceLowToHigh(ItemList itemList){
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemPrice() > o2.getItemPrice() ) return 1;
                if(o1.getItemPrice() < o2.getItemPrice()) return -1;
                return 0;
            }
        });
        return this.itemList = itemList;
    }

    public ItemList newToOld(ItemList itemList){
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                int object1 = Integer.parseInt(o1.getItemID());
                int object2 = Integer.parseInt(o2.getItemID());
                if(object1 - object2 < 0) return 1;
                if(object1 - object2 > 0) return -1;
                return 0;
            }
        });
        return this.itemList = itemList;
    }

    public ItemList rangePrice(ItemList itemList){
        String highPrice = highPriceTextField.getText();
        String lowPrice = lowPriceTextField.getText();
        boolean highP = isCorrectFormat(highPrice, highPriceTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE));
        boolean lowP = isCorrectFormat(lowPrice, lowPriceTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE));
        if (!highP || !lowP) {
            error = "price";
            highPriceTextField.setStyle("-fx-text-fill: red");
            lowPriceTextField.setStyle("-fx-text-fill: red");
            items.clear();
            items.addAll(itemList.getAllItems());
            for (Item item: items){
                itemList.removeItem(item.getItemID());
            }
        }
        else{
            double high  = Double.parseDouble(highPrice);
            double low = Double.parseDouble(lowPrice);
            items.clear();
            items.addAll(itemList.getAllItems());
            for (Item item: items){
                if (item.getItemPrice() < low || item.getItemPrice() > high){
                    itemList.removeItem(item.getItemID());
                }
            }
            if (itemList.getAllItems().isEmpty()){
                highPriceTextField.setStyle("-fx-text-fill: red");
                lowPriceTextField.setStyle("-fx-text-fill: red");
                error = "price";
            }
            else {
                error="";
            }
        }
        newToOld(itemList);
        return this.itemList = itemList;
    }

    public ItemList sortType(String type){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();
        filterBannedItems(itemList);
        itemList.filter(new ItemFromTypeFilterer(type));
        itemArrayList.clear();
        itemArrayList.addAll(itemList.getAllItems());
        for (Item item: itemArrayList){
            this.itemList.addItem(item);
        }
        return this.itemList;
    }

    private boolean isCorrectFormat(String string, TextField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: white; -fx-border-color: white");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red;");
            return false;
        }
    }

    public ItemList reset(){
        ItemListDataSource dataSource = new ItemListDataSource();
        ItemList itemList = dataSource.getItemList();
        filterBannedItems(itemList);
        newToOld(itemList);
        this.itemList = itemList;
        return this.itemList;
    }



    public void filterBannedItems(ItemList itemList){
        items.clear();
        items.addAll(itemList.getAllItems());
        for (Item item : items){
            if (item.isBanned()){
                itemList.removeItem(item.getItemID());
            }
        }
    }

    @FXML
    public void handleFilterButton(ActionEvent event) {
        marketSceneController.setItemFilter(chooseSortHighLow(),error);
        shopNameTextField.clear();
        highPriceTextField.clear();
        lowPriceTextField.clear();
        electronicsCheckBox.setSelected(false);
        clothingCheckBox.setSelected(false);
        utensilsCheckBox.setSelected(false);
        otherCheckBox.setSelected(false);
        priceHighToLowCheckbox.setSelected(false);
        priceLowToHighCheckbox.setSelected(false);
    }

    @FXML
    public void handleResetFilterButton(ActionEvent event) {
        marketSceneController.setItemFilter(reset(),error);
        shopNameTextField.clear();
        highPriceTextField.clear();
        lowPriceTextField.clear();
        priceHighToLowCheckbox.setSelected(false);
        priceLowToHighCheckbox.setSelected(false);
        electronicsCheckBox.setSelected(false);
        clothingCheckBox.setSelected(false);
        utensilsCheckBox.setSelected(false);
        otherCheckBox.setSelected(false);
    }




}
