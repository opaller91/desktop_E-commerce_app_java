package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ShopListDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import com.github.saacsos.FXRouter;
import ku.cs.shop.services.UserListDataSource;

public class CreateShopSceneController {

    @FXML private TextField shopNameTextField;
    @FXML private TextField shopAddressTextField;
    @FXML private TextField shopEmailTextField;
    @FXML private TextField shopPhoneNumberTextField;
    @FXML private TextField shopFacebookTextField;
    @FXML private TextField shopInstagramTextField;
    @FXML private TextField shopLineTextField;
    @FXML private TextField bankNameTextField;
    @FXML private TextField accountNumberTextField;
    @FXML private TextField accountNameTextField;
    @FXML private ImageView imageView;


    private BufferedImage bi = null;
    ShopListDataSource shopListDataSource = new ShopListDataSource();
    UserListDataSource userListDataSource = new UserListDataSource();
    UserList userList;
    ShopList shopList;
    Shop shop;
    Information info = (Information) FXRouter.getData();
    User user = info.getUser();


    private boolean isEmpty (String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red;-fx-border-color: red");
            return false;
        } else {
            field.setStyle("-fx-prompt-text-fill: grey");
            return true;
        }
    }

    private boolean isCorrectFormat (String string, TextField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: black");
            return true;
        } else {
            field.setStyle("-fx-border-color: red ");
            return false;
        }
    }

    private boolean isAvailable(String shopName, TextField field) {
        shopList = shopListDataSource.getShopList();
        Shop temp = shopList.findShop(shopName);
        if (!shopName.contains("null") && temp == null) {
            field.setStyle("-fx-text-fill: black");
            return true;
        } else {
            field.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public void handleAddPhoto(ActionEvent event) {
        Stage stage = new Stage();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png","*.jpg","*.jpeg"));
        File picFile = fileChooser.showOpenDialog(stage);
        imageView.setImage(new Image(picFile.toURI().toString()));
        try {
            bi = ImageIO.read(picFile);
        } catch (IOException e) {
            System.err.println("Cannot load picture");
        }
    }

    @FXML
    public void handleSignUpButton (ActionEvent actionEvent) throws IOException {
        String shopName = shopNameTextField.getText();
        String shopAddress = shopAddressTextField.getText();
        String shopEmail = shopEmailTextField.getText().toLowerCase();
        String shopPhoneNumber = shopPhoneNumberTextField.getText();
        String shopFacebook = shopFacebookTextField.getText();
        String shopInstagram = shopInstagramTextField.getText().toLowerCase();
        String shopLine = shopLineTextField.getText();
        String bankName = bankNameTextField.getText();
        String accountNumber = accountNumberTextField.getText();
        String accountName = accountNameTextField.getText();


        boolean signup = isEmpty(shopName, shopNameTextField) &&
                isAvailable(shopName, shopNameTextField) &&
                isCorrectFormat(shopName, shopNameTextField, Pattern.compile("^[^\",\\s]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopAddress, shopAddressTextField) &&
                isCorrectFormat(shopAddress, shopAddressTextField, Pattern.compile("^[A-za-z0-9@#()–:;'/~<>\\s]*$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopEmail, shopEmailTextField) &&
                isCorrectFormat(shopEmail, shopEmailTextField, Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopPhoneNumber, shopPhoneNumberTextField) &&
                isCorrectFormat(shopPhoneNumber, shopPhoneNumberTextField, Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopFacebook, shopFacebookTextField) &&
                isCorrectFormat(shopFacebook, shopFacebookTextField, Pattern.compile("^[A-Za-z0-9\\s]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopInstagram, shopInstagramTextField) &&
                isCorrectFormat(shopInstagram, shopInstagramTextField, Pattern.compile("^[A-Za-z0-9._]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(shopLine, shopLineTextField) &&
                isCorrectFormat(shopLine, shopLineTextField, Pattern.compile("^[A-Za-z0-9._]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(bankName, bankNameTextField) &&
                isCorrectFormat(bankName, bankNameTextField, Pattern.compile("^[A-Za-z0-9\\s]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(accountNumber, accountNumberTextField) &&
                isCorrectFormat(accountNumber, accountNumberTextField, Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(accountName, accountNameTextField) &&
                isCorrectFormat(accountName, accountNameTextField, Pattern.compile("^[A-Za-z\\s]+$", Pattern.CASE_INSENSITIVE));


        if (signup) {
            shop = new Shop(shopName, shopAddress, shopEmail, shopPhoneNumber, shopFacebook,
                            shopInstagram, shopLine, bankName, accountName, accountNumber, user.getUsername());
            shop.setImage(bi);
            user.setShop(shop);
            user.setShopName(shop.getShopName());
            shopList = shopListDataSource.getShopList();
            shopList.addShop(shop);
            shopListDataSource.setShopList(shopList);
            shopListDataSource.saveData();
            userList = userListDataSource.getUserList();
            userList.removeUser(user.getUsername());
            userList.addUser(user);
            userListDataSource.saveData();
            FXRouter.goTo("shop_info_shop",info);
       }
    }


    @FXML
    public void handleHomeButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("home",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home_scene ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
