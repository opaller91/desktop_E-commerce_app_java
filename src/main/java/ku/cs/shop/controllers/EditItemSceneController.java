package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BanListDataSource;
import ku.cs.shop.services.ItemListDataSource;
import com.github.saacsos.FXRouter;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class EditItemSceneController {

    @FXML
    private TextField editItemNameTextField;
    @FXML
    private TextArea editItemDescriptionTextArea;
    @FXML
    private TextField editItemPriceTextField;
    @FXML
    private TextField editItemStockTextField;
    @FXML
    private TextField editStockWarningTextField;
    @FXML
    private Label pictureChangedLabel;
    @FXML
    private ComboBox<String> editItemTypeCombo;
    @FXML
    private ImageView itemImageView;
    @FXML
    private AnchorPane anchorpane;


    Information info = (Information) FXRouter.getData();
    ItemListDataSource itemData = new ItemListDataSource();
    ItemList itemList;
    private BufferedImage bi = null;
    Item item = info.getItem();
    String itemType;


    public void initialize() {
        System.out.println("initialize EditItemSceneController");
        itemList = itemData.getItemList();
        showInformation();
        editItemTypeCombo.getItems().setAll("Electronics", "Clothing", "Utensils", "Other");
        if (item.isBanned()) {
            BanListDataSource banListDataSource = new BanListDataSource("Item");
            BanList banList = banListDataSource.getBanList();
            Ban ban = banList.findObjectID("Item",item.getItemID());
            Alert.AlertType type = Alert.AlertType.WARNING;
            System.out.println(ban.toCSV());
            Alert alert = new Alert(type,"Item :"+item.getItemName()+"\nReason:"+ban.getReason()+"\nBan Time:"+ban.getBanTimeStr());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.getDialogPane().setHeaderText("Item is banned,edit to fix");//ตอนทำUIทำตัวตัวอักษรเป็นสีแดง
            alert.showAndWait();
        }
    }

    @FXML
    public void select (ActionEvent event){
        itemType = editItemTypeCombo.getSelectionModel().getSelectedItem();
    }

    public void showInformation() {
        pictureChangedLabel.setText("");
        pictureChangedLabel.setStyle("-fx-prompt-text-fill: black");
        itemImageView.setImage(item.getItemImage());

        editItemNameTextField.setText(item.getItemName());
        editItemDescriptionTextArea.setText(item.getItemDescription());
        editItemPriceTextField.setText(String.valueOf(item.getItemPrice()));
        editStockWarningTextField.setText(String.valueOf(item.getStockWarning()));
        editItemTypeCombo.setPromptText(String.valueOf(item.getItemType()));

    }

    private boolean isEmpty(String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: #cc0000 ;-fx-border-color: #cc0000 ; -fx-border-width: 2px");
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
            field.setStyle("-fx-border-color: #cc0000 ; -fx-border-width: 2px");
            return false;
        }
    }


    private int isEmptyItemStock(String string) {
        if (string.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(string);
        }
    }


    @FXML
    public void handleSaveEditItem (ActionEvent event) {
        String itemName = editItemNameTextField.getText();
        String itemDescription = editItemDescriptionTextArea.getText();
        if (item.isBanned()) {
            BanListDataSource banListDataSource = new BanListDataSource("Item");
            BanList banList = banListDataSource.getBanList();
            Ban ban = banList.findObjectID("Item",item.getItemID());
            item.setBanned(false);
            banList.removeBan(ban.getBanID());
            banList.addBan(ban);
            banListDataSource.saveData();
        }

        String currentItemName = editItemNameTextField.getText();
        String currentItemDescription = editItemDescriptionTextArea.getText()
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        String itemPriceStr = editItemPriceTextField.getText();
        String itemStockStr = editItemStockTextField.getText();
        String stockWarningStr = editStockWarningTextField.getText();



        boolean add = isEmpty(itemName, editItemNameTextField) &&
                isEmpty(itemPriceStr, editItemPriceTextField) &&
                isCorrectFormat(itemPriceStr, editItemPriceTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE)) &&
                isCorrectFormat(itemStockStr, editItemStockTextField, Pattern.compile("^$|^[0-9]*$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(stockWarningStr, editStockWarningTextField) &&
                isCorrectFormat(stockWarningStr, editStockWarningTextField, Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE));


        if (add) {

            double itemPrice = Double.parseDouble(itemPriceStr);
            int itemStock = isEmptyItemStock(itemStockStr);
            int stockWarning = Integer.parseInt(stockWarningStr);

            itemList.removeItem(item.getItemID());
            item.setItemName(itemName);
            item.setItemDescription(itemDescription);
            item.setItemPrice(itemPrice);
            item.setItemStock( itemStock + item.getItemStock());
            item.setStockWarning(stockWarning);
            if (itemType == null){
                itemType = item.getItemType();
            }
            item.setItemType(itemType);
            item.setImage(bi);


            itemList.addItem(item);
            itemData.setItemList(itemList);
            itemData.saveData();

            try {
                FXRouter.goTo("shop_stock", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า shop_stock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }


    @FXML
    public void handleChangePicture (ActionEvent event) {
        Stage stage = new Stage();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File picFile = fileChooser.showOpenDialog(stage);
        if (picFile == null) {
            pictureChangedLabel.setText("Item picture not found");
            pictureChangedLabel.setStyle("-fx-text-fill: red");
            return;
        }
        itemImageView.setImage(new Image(picFile.toURI().toString()));
        try {
            bi = ImageIO.read(picFile);
        } catch (IOException e) {
            System.err.println("Cannot load picture");
            return;
        }
        pictureChangedLabel.setText("Item picture changed!");
        pictureChangedLabel.setStyle("-fx-text-fill: green");
    }





    @FXML
    public void handleBack(ActionEvent event) {
        try {
            info.setItem(null);
            FXRouter.goTo("shop_stock",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_stock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



}
