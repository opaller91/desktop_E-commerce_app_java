package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.User;
import ku.cs.shop.models.UserList;
import ku.cs.shop.services.UserListDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserSettingSceneController {

    @FXML private TextField fullNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private Label passwordChangedLabel;
    @FXML private Label pictureChangedLabel;
    @FXML private PasswordField currentPasswordPasswordField;
    @FXML private PasswordField newPasswordPasswordField;
    @FXML private PasswordField confirmPasswordPasswordField;
    @FXML private ImageView imageView;
    @FXML private AnchorPane pane;

    UserListDataSource ulData = new UserListDataSource();
    UserList ul;
    Information info = (Information) FXRouter.getData();
    User user = info.getUser();

    public void showInformation() {
        passwordChangedLabel.setText("");
        passwordChangedLabel.setStyle("-fx-prompt-text-fill: black");
        pictureChangedLabel.setText("");
        pictureChangedLabel.setStyle("-fx-prompt-text-fill: black");

        fullNameTextField.setText(user.getFullName());
        usernameTextField.setText(user.getUsername());
        phoneNumberTextField.setText(user.getPhoneNumber());
        emailTextField.setText(user.getEmail());
        imageView.setImage(user.getImage());
    }

    public void initialize() {
        System.out.println("initialize UserSettingSceneController");
        pane.setStyle("-fx-background-color: linear-gradient(to bottom, #000000 0%, #ffffff 74%)");
        ul = ulData.getUserList();
        showInformation();
    }

    @FXML
    public void handleChangePicture (ActionEvent event) {
        Stage stage = new Stage();
        BufferedImage bi = null;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg","*.jpeg"));
        File picFile = fileChooser.showOpenDialog(stage);
        if (picFile == null) {
            pictureChangedLabel.setText("Profile picture not found");
            pictureChangedLabel.setStyle("-fx-text-fill: red");
            return;
        }
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        imageView.setImage(new Image(picFile.toURI().toString()));
        try {
            bi = ImageIO.read(picFile);
        } catch (IOException e) {
            System.err.println("Cannot load picture");
            return;
        }
        refreshUser(user, bi);
        pictureChangedLabel.setText("Profile picture changed!");
        pictureChangedLabel.setStyle("-fx-text-fill: green");
    }

    @FXML
    public void handleChangePassword (ActionEvent event) {
        String userPassword = user.getPassword();
        String currentPassword = currentPasswordPasswordField.getText();
        String newPassword = newPasswordPasswordField.getText();
        String confirmPassword = confirmPasswordPasswordField.getText();

        currentPasswordPasswordField.clear();
        newPasswordPasswordField.clear();
        confirmPasswordPasswordField.clear();
        if (!currentPassword.equals(userPassword)) { // password is the same as the current one
            passwordChangedLabel.setText("This is not your current password");
            currentPasswordPasswordField.setStyle("-fx-prompt-text-fill: red");
            passwordChangedLabel.setStyle("-fx-text-fill: red");
            return;
        } else {
            currentPasswordPasswordField.setStyle("-fx-prompt-text-fill: black");
        }

        if (!isEmpty(newPassword, newPasswordPasswordField) ||
            !isCorrectFormat(newPassword, newPasswordPasswordField, Pattern.compile("^[A-Za-z0-9]{8,}$"))) {
            passwordChangedLabel.setText("Invalid new password");
            passwordChangedLabel.setStyle("-fx-text-fill: red");
            newPasswordPasswordField.setStyle("-fx-prompt-text-fill: red");
            return;
        }
        else{
            newPasswordPasswordField.setStyle("-fx-prompt-text-fill: Black");
        }

        if (newPassword.equals(confirmPassword)) {
            newPasswordPasswordField.setStyle("-fx-prompt-text-fill: black");
            confirmPasswordPasswordField.setStyle("-fx-prompt-text-fill: black");
            passwordChangedLabel.setText("Password changed!");
            passwordChangedLabel.setStyle("-fx-text-fill: green");
        } else {
            newPasswordPasswordField.setStyle("-fx-prompt-text-fill: red");
            confirmPasswordPasswordField.setStyle("-fx-prompt-text-fill: red");
            passwordChangedLabel.setText("New password doesn't match");
            passwordChangedLabel.setStyle("-fx-text-fill: red");
            return;
        }
        refreshUser(user, newPassword);
    }

    private boolean isEmpty (String string, PasswordField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red");
            return false;
        } else {
            field.setStyle("-fx-prompt-text-fill: grey");
            return true;
        }
    }

    private boolean isCorrectFormat (String string, PasswordField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: black");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red");
            return false;
        }
    }

    @FXML
    public void handleBack (ActionEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("home",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void refreshUser(User user, String password) {
        ul.removeUser(user.getUsername());
        user.setPassword(password);
        ul.addUser(user);
        ulData.setUserList(ul);
        ulData.saveData();
    }

    public void refreshUser(User user, BufferedImage bi) {
        ul.removeUser(user.getUsername());
        user.setImage(bi);
        ul.addUser(user);
        ulData.setUserList(ul);
        ulData.saveData();
    }
}
