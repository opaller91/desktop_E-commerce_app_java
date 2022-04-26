package ku.cs.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.github.saacsos.FXRouter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ItemListDataSource;

public class ShopStockSceneController {
    @FXML private TableView<Item>  itemTableView;
    TableColumn itemImageColumn = new TableColumn("itemImage");
   TableColumn itemNameColumn = new TableColumn("itemName");
   TableColumn itemTypeColumn = new TableColumn("itemType");
   TableColumn itemPriceColumn = new TableColumn("itemPrice");
   TableColumn itemStockColumn = new TableColumn("itemStock");
    @FXML private ImageView itemImage;
    @FXML private Label itemNameLabel;
    @FXML private Label itemTypeLabel;
    @FXML private Label itemPriceLabel;
    @FXML private Label itemStockLabel;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Shop shop = user.getShop();
    ItemListDataSource dataSource = new ItemListDataSource(shop.getShopName());
    ItemList itemList;
    Item item;

    //------------------กลับไปหน้า Home-------------------
    @FXML
    public void handleBackButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("shop_info_shop",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_info_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



    //------------------------แสดงหน้า item ที่เลือก ------------------------
     public void showSelectedItem(Item item){
         itemNameLabel.setText(item.getItemName());
         itemTypeLabel.setText(item.getItemType());
         itemPriceLabel.setText(String.valueOf(item.getItemPrice()));
         itemStockLabel.setText(String.valueOf(item.getItemStock()));
         itemImage.setImage(item.getItemImage());
         if(item.getItemStock() > item.getStockWarning()){
             itemStockLabel.setStyle("-fx-text-fill: black"); //ถ้าสินค้ายังเหลือเยอะเป็นสีดำ
         }
         else{
             itemStockLabel.setStyle("-fx-text-fill: red"); //ถ้าเหลือน้อยเป็นสีแดง
         }

     }


     public void clearSelectedItem(){
         itemNameLabel.setText("");
         itemTypeLabel.setText("");
         itemPriceLabel.setText("");
         itemStockLabel.setText("");
     }


    private void handleSelectedListView() {
        itemTableView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Item>() {
                    @Override
                    public void changed(ObservableValue<? extends Item> observable,
                                        Item oldValue, Item newValue) {
                        item = newValue;
                        showSelectedItem(item);
                    }
                });
    }

    @FXML
    public void handleEditItemButton (ActionEvent actionEvent){
        try {
            info.setItem(item);
            FXRouter.goTo("edit_item",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit item ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAddItemButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("add_item",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add item ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void initialize() {

        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        itemNameColumn.setMinWidth(170);
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemType"));
        itemTypeColumn.setMinWidth(170);
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("itemPrice"));
        itemPriceColumn.setMinWidth(170);
        itemStockColumn.setCellValueFactory(new PropertyValueFactory<Item,Integer>("itemStock"));
        itemStockColumn.setMinWidth(170);

        itemList = dataSource.getItemList();
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(itemList.getAllItems());
        System.out.println(items);
        itemTableView.setItems(items);
        itemTableView.getColumns().addAll(itemNameColumn, itemTypeColumn, itemPriceColumn,itemStockColumn);
        clearSelectedItem();
        handleSelectedListView();
    }

}
