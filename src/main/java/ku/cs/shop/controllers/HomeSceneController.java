package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.ShopList;
import ku.cs.shop.models.User;
import ku.cs.shop.services.ShopListDataSource;
import ku.cs.shop.services.UserListDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeSceneController {


    @FXML private Label userNameLabel;
    @FXML private Label shopNameLabel;
    @FXML private Label dateLabel;
    @FXML private ImageView imageView;
    @FXML private Label joinLabel;
    @FXML private Label joinShadowLabel;
    @FXML private Label createShopLabel;
    @FXML private Label goShopShadow;
    @FXML private Label goShop;
    @FXML private JFXButton btGoShop;
    @FXML private JFXButton btCreateShop;
    @FXML private ImageView showIcon;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    UserListDataSource userListDataSource = new UserListDataSource();
    int count;

    public void showInformation() {
        userNameLabel.setText(user.getUsername());
        if (user.getShop() != null) {
            info.setShop(user.getShop());
            shopNameLabel.setText(user.getShopName());
            joinLabel.setVisible(false);
            joinShadowLabel.setVisible(false);
            createShopLabel.setVisible(false);
            goShopShadow.setVisible(true);
            goShop.setVisible(true);
            btCreateShop.setVisible(false);
            btGoShop.setVisible(true);
        } else {
            shopNameLabel.setStyle("-fx-text-fill: red");
            shopNameLabel.setText("No shop found");
            joinLabel.setVisible(true);
            joinShadowLabel.setVisible(true);
            createShopLabel.setVisible(true);
            goShopShadow.setVisible(false);
            goShop.setVisible(false);
            btCreateShop.setVisible(true);
            btGoShop.setVisible(false);

        }
        dateLabel.setText(user.getLastOnlineTimeStr());
        imageView.setImage(user.getImage());
    }

    public void initialize() {
        System.out.println("initialize HomeSceneController");
        showInformation();
        slideShow();
    }

    public void slideShow(){
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image(Objects.requireNonNull(getClass().getResource("/userimages/1A.png")).toExternalForm()));
        images.add(new Image(Objects.requireNonNull(getClass().getResource("/userimages/2A.png")).toExternalForm()));
        images.add(new Image(Objects.requireNonNull(getClass().getResource("/userimages/3A.png")).toExternalForm()));
        images.add(new Image(Objects.requireNonNull(getClass().getResource("/userimages/4A.png")).toExternalForm()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event-> {
            showIcon.setImage(images.get(count));
            count++;
            if (count == 4)
                count = 0;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            userListDataSource.saveData();
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleShop(ActionEvent event) {
        if (user.getShop() != null) {
            Shop shop = user.getShop();
            shop.setImage(null);
            user.setShop(shop);
            try {
                FXRouter.goTo("shop_info_shop", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า shop_info_shop ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else {
            try {
                FXRouter.goTo("create_shop", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า create_shop ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    public void handleShopLabelMouseEnter(MouseEvent event) {//ถ้าทำเป็นปุ่มแล้วไม่ต้องมี
        if (user.getShop() != null) {
            shopNameLabel.setStyle("-fx-text-fill: green");
        } else {
            shopNameLabel.setStyle("-fx-text-fill: #a10000");
        }

    }

    public void handleShopLabelMouseExit(MouseEvent event) {
        if (user.getShop() != null) {
            shopNameLabel.setStyle("-fx-text-fill: black");
        } else {
            shopNameLabel.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    public void handleGoShopButton (ActionEvent event) {
        try {
            FXRouter.goTo("market",info);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSettings (ActionEvent event) {
        try {
            FXRouter.goTo("user_setting",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user_setting ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
