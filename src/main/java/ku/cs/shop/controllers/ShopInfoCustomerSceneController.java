package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.market.controllers.ItemShopController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.ShopListDataSource;
import ku.cs.shop.services.ShowStarRatings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ShopInfoCustomerSceneController {

    @FXML
    private JFXTextArea addressTextArea;

    @FXML
    private Label emailLabel;

    @FXML
    private Label facebookLabel;

    @FXML
    private Label instagramLabel;

    @FXML
    private Label lineIDLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private HBox productBox;

    @FXML
    private Label shopNameLabel;

    @FXML
    private ImageView userImageView;

    @FXML
    private Label usernameLabel;



    Information info = (Information) FXRouter.getData();
    Shop shop = info.getShop();
    User user = info.getUser();
    ArrayList<Item> items = new ArrayList<>();
    ItemListDataSource itemData = new ItemListDataSource();
    ItemList itemList = itemData.getItemList();



    @FXML
    public void initialize () {

        showUserInformation();
        showShopInformation();
        showProduct();

    }

    public void showUserInformation() {
        usernameLabel.setText(user.getFullName());
        userImageView.setImage(user.getImage());
    }

    public void showShopInformation() {


        shopNameLabel.setText(shop.getShopName());
        addressTextArea.setText(shop.getShopAddress());
        emailLabel.setText(shop.getShopEmail());
        phoneNumberLabel.setText(shop.getShopPhoneNumber());
        facebookLabel.setText(shop.getShopFacebook());
        instagramLabel.setText(shop.getShopInstagram());
        lineIDLabel.setText(shop.getShopLine());

    }

    public void showProduct() {
        items.clear();
        items.addAll(itemList.getAllItems());
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.getItemID().compareTo(o2.getItemID()) < 0) return 1;
                if(o1.getItemID().compareTo(o2.getItemID()) > 0) return -1;
                return 0;
            }
        });
        for (Item item : items){
            if (item.isBanned()){
                itemList.removeItem(item.getItemID());
            }
        }
        items.clear();
        items.addAll(itemList.getAllItems());
        items.addAll(itemList.getAllItems());
        for (Item item : items) {
            if (!item.getShopName().equals(shop.getShopName())) {
                itemList.removeItem(item.getItemID());
            }
        }
        items.clear();
        items.addAll(itemList.getAllItems());
        try {
            int count = 0;
            for (Item item : items) {
                String filepath = "assets" + File.separator +
                        "images" + File.separator +
                        "item" + File.separator +
                        item.getItemID() + ".png";
                Image image = itemData.getImage(filepath);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/item_shop.fxml"));
                VBox itemBox = fxmlLoader.load();
                ItemShopController itemShopController = fxmlLoader.getController();
                info.setItem(item);
                if (count == 5) count = 0;
                itemShopController.setData(info, image, new ShowStarRatings(), count++);
                productBox.getChildren().add(itemBox);
            }
        } catch ( IOException e) {
                System.err.println("Not found item_shop.fxml");
        }



    }


    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("item",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
