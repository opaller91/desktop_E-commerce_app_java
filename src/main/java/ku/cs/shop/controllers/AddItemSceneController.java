package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ItemListDataSource;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class AddItemSceneController {
    @FXML
    private TextField itemNameTextField;
    @FXML
    private TextArea itemDescriptionTextArea;
    @FXML
    private TextField itemPriceTextField;
    @FXML
    private TextField itemStockTextField;
    @FXML
    private TextField stockWarningTextField;
    @FXML
    private ImageView itemImageView;
    @FXML
    private ComboBox<String> itemTypeCombo;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Shop shop = user.getShop();
    private BufferedImage bi = null;
    ItemListDataSource itemListDataSource = new ItemListDataSource();
    ItemList itemList;
    String itemType;

    private boolean isEmpty(String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle(" -fx-prompt-text-fill: #cc0000 ;-fx-border-color: #cc0000 ; -fx-border-width: 2px");
            return false;
        } else {
            field.setStyle("-fx-prompt-text-fill: grey");
            return true;
        }
    }

    private boolean isCorrectFormat(String string, TextField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: black");
            return true;
        } else {
            field.setStyle(" -fx-border-color: #cc0000 ; -fx-border-width: 2px");
            return false;
        }
    }

    public boolean isComboBoxEmpty() {
        return itemTypeCombo.getValue().isEmpty();
    }

    @FXML
    public void initialize() {
        itemList = itemListDataSource.getItemList();
        itemTypeCombo.getItems().setAll("Electronics", "Clothing", "Utensils", "Other");

    }

    @FXML
    public void select (ActionEvent event){
        itemType = itemTypeCombo.getSelectionModel().getSelectedItem();
    }


    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("shop_stock",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_stock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleAddItemPhoto(ActionEvent event) {
        Stage stage = new Stage();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg","*.jpeg"));
        File picFile = fileChooser.showOpenDialog(stage);
        if (picFile != null) {
            itemImageView.setImage(new Image(picFile.toURI().toString()));
        }
        try {
            bi = ImageIO.read(picFile);
        } catch (IOException e) {
            System.err.println("Cannot load picture");
        }
    }

    @FXML
    public void handleAddItemButton(ActionEvent event) {
        //รับข้อมมูลจากTextField
        String itemName = itemNameTextField.getText();
        String itemDescription = itemDescriptionTextArea.getText()
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        String itemPriceStr = itemPriceTextField.getText();
        String itemStockStr = itemStockTextField.getText();
        String stockWarningStr = stockWarningTextField.getText();

        boolean add = isEmpty(itemName, itemNameTextField) &&
                isCorrectFormat(itemName, itemNameTextField, Pattern.compile("^[^\",]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(itemPriceStr, itemPriceTextField) &&
                isCorrectFormat(itemPriceStr, itemPriceTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(itemStockStr, itemStockTextField) &&
                isCorrectFormat(itemStockStr, itemStockTextField, Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(stockWarningStr, stockWarningTextField) &&
                isCorrectFormat(stockWarningStr, stockWarningTextField, Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE))&&
                !isComboBoxEmpty();
        if (add) {
            double itemPrice = Double.parseDouble(itemPriceStr);
            int itemStock = Integer.parseInt(itemStockStr);
            int stockWarning = Integer.parseInt(stockWarningStr);
            String itemID = itemList.findNextItemID() + "";
            System.out.println(itemID);

            String shopName = shop.getShopName();
            Item item = new Item(itemID, shopName, itemName, itemDescription,itemPrice, itemStock,stockWarning,itemType,false);
            item.setImage(bi);
            itemList.addItem(item);
            itemListDataSource.setItemList(itemList);
            itemListDataSource.saveData();
            try {
                FXRouter.goTo("shop_stock",info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า shop_stock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}




