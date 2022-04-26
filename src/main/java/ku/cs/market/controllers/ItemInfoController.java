package ku.cs.market.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.shop.controllers.ItemDetailSceneController;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import com.github.saacsos.FXRouter;
import ku.cs.shop.models.Shop;
import ku.cs.shop.services.ShowPicture;


import java.io.IOException;

public class ItemInfoController {

    @FXML
    private TextArea itemDetailTextArea;

    @FXML
    private ImageView itemImageView;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label itemStockLabel;

    @FXML
    private ImageView shopImageView;

    @FXML
    private Label shopNameLabel;

    @FXML
    private JFXButton buyNowButton;

    @FXML
    private Line lineBtn;


    @FXML
    private Label clickShopDetailLabel;

    Information info;
    Item item;
    ItemDetailSceneController itemDetailSceneController = null;

    public void setItemDetailSceneController(ItemDetailSceneController itemDetailSceneControllerI) {
        this.itemDetailSceneController = itemDetailSceneControllerI;
    }

    public void setData(Information info, Image itemImage, Image shopImage) {

        buyNowButton.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                lineBtn.setVisible(true);
            } else {
                lineBtn.setVisible(false);
            }
        });

        this.info = info;
        item = info.getItem();
        itemImageView.setImage(itemImage);
        itemNameLabel.setText(item.getItemName());
        itemPriceLabel.setText("THB " + item.getItemPrice());
        itemDetailTextArea.setText(item.getItemDescription());
        shopNameLabel.setText(item.getShopName());
        shopImageView.setImage(shopImage);
        shopImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");
        itemStockLabel.setText("In stock  " + item.getItemStock());
        if(item.getItemStock() > item.getStockWarning()){
            itemStockLabel.setStyle("-fx-text-fill: black");
        } else if (item.getItemStock() == 0){
            itemStockLabel.setStyle("-fx-text-fill: red");
            itemStockLabel.setText("Out of stock");
        }
        else {
            itemStockLabel.setStyle("-fx-text-fill: red");
            itemStockLabel.setText("In stock  " + item.getItemStock() + "  Nearly out of stock");
        }


    }

    @FXML
    public void handleShopLabelMouseEnter(MouseEvent event) {
        shopNameLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #FDD819, #E80505)");
        clickShopDetailLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #FDD819, #E80505)");
    }

    public void handleShopLabelMouseExit(MouseEvent event) {
        shopNameLabel.setStyle("-fx-text-fill: black");
        clickShopDetailLabel.setStyle("-fx-text-fill: black");
    }

    @FXML
    public void handleBuyNowButton (ActionEvent actionEvent) {
        if(item.getItemStock() == 0) {
            itemDetailSceneController.setErrorOutOfStock();
            return;
        }
        try {
            FXRouter.goTo("purchase_order", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า purchase_order ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleReportItemButton (ActionEvent actionEvent) {
        try {
            info.setItem(item);
            info.setReview(null);
            FXRouter.goTo("report", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleShopLabel(MouseEvent event) {
        try {
            Shop shop = item.getShop();
            info.setShop(shop);
            FXRouter.goTo("shop_info_customer", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }
}
