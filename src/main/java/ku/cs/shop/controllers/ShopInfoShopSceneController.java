package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.github.saacsos.FXRouter;
import javafx.scene.image.ImageView;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.User;

import java.io.IOException;

public class ShopInfoShopSceneController {

    @FXML private Label shopNameLabel;
    @FXML private JFXTextArea addressLabel;
    @FXML private Label shopEmailLabel;
    @FXML private Label shopPhoneNumberLabel;
    @FXML private Label shopFacebookLabel;
    @FXML private Label shopInstagramLabel;
    @FXML private Label shopLineLabel;
    @FXML private ImageView shopImage;

    Information info = (Information) FXRouter.getData();
    private User user = info.getUser();
    private Shop shop = user.getShop();


    @FXML
    public void handleHomeButton (ActionEvent event) {
        try {
            FXRouter.goTo("home", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void initialize () {
        System.out.println("initialize SellerSceneController");
        shopNameLabel.setText(shop.getShopName());
        addressLabel.setText(shop.getShopAddress());
        shopEmailLabel.setText(shop.getShopEmail());
        shopPhoneNumberLabel.setText(shop.getShopPhoneNumber());
        shopFacebookLabel.setText(shop.getShopFacebook());
        shopInstagramLabel.setText(shop.getShopInstagram());
        shopLineLabel.setText(shop.getShopLine());
        shopImage.setImage(shop.getPicture());
    }

    @FXML
    public void handleItemsButton (ActionEvent event) {
        try {
            FXRouter.goTo("shop_stock",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_stock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    @FXML
    public void handleOrderListButton (ActionEvent event) {
        try {
            FXRouter.goTo("order_list",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า order_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    @FXML
    public void handleVouchersButton (ActionEvent event) {
        try {
            FXRouter.goTo("voucher_list",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า voucher_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

}
