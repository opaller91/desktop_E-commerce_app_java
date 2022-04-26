package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.User;
import com.github.saacsos.FXRouter;
import ku.cs.shop.models.UserList;
import ku.cs.shop.services.UserListDataSource;

import java.util.regex.Pattern;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class CreateAccountSceneController {

//    @FXML private TextField fullNameTextField;
//    @FXML private TextField usernameTextField;
//    @FXML private TextField phoneNumberTextField;
//    @FXML private TextField emailTextField;
//    @FXML private PasswordField passwordField;
//    @FXML private PasswordField confirmPasswordField;
//    @FXML private Label passwordCheckLabel;
//    @FXML private ImageView imageView;
//
//    private BufferedImage bi = null;
//    UserListDataSource userListDataSource = new UserListDataSource();
//    UserList userList = null;
//
//    private boolean isEmpty (String string, TextField field) {
//        if (string.isEmpty()) {
//            field.setStyle("-fx-prompt-text-fill: red");
//            return false;
//        } else {
//            field.setStyle("-fx-prompt-text-fill: grey");
//            return true;
//        }
//    }
//
//    private boolean isEmpty (String string, PasswordField field) {
//        if (string.isEmpty()) {
//            field.setStyle("-fx-prompt-text-fill: red");
//            return false;
//        } else {
//            field.setStyle("-fx-prompt-text-fill: grey");
//            return true;
//        }
//    }
//
//    private boolean isCorrectFormat (String string, TextField field, Pattern regex) {
//        if (regex.matcher(string).find()) {
//            field.setStyle("-fx-text-fill: black");
//            return true;
//        } else {
//            field.setStyle("-fx-text-fill: red");
//            return false;
//        }
//    }
//
//    private boolean isCorrectFormat (String string, PasswordField field, Pattern regex) {
//        if (regex.matcher(string).find()) {
//            field.setStyle("-fx-text-fill: black");
//            return true;
//        } else {
//            field.setStyle("-fx-text-fill: red");
//            return false;
//        }
//    }
//
//    private boolean isAvailable(String username, TextField field) {
//        userList = userListDataSource.getUserList();
//        User temp = userList.findUser(username);
//        if (!username.contains("null") && temp == null) {
//            field.setStyle("-fx-text-fill: black");
//            return true;
//        } else {
//            field.setStyle("-fx-text-fill: red");
//            return false;
//        }
//    }
//
//    public void handleAddPhoto(MouseEvent event) {
//        Stage stage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*.png", "*.jpg"));
//        File picFile = fileChooser.showOpenDialog(stage);
//        if (picFile != null) {
//            imageView.setImage(new Image(picFile.toURI().toString()));
//        }
//        try {
//            bi = ImageIO.read(picFile);
//        } catch (IOException e) {
//            System.err.println("Cannot load picture");
//        }
//    }
//
//    @FXML
//    public void handleSignUpButton (ActionEvent actionEvent) throws IOException {
//        String fullName = fullNameTextField.getText();
//        String username = usernameTextField.getText().toLowerCase();
//        String phoneNumber = phoneNumberTextField.getText();
//        String email = emailTextField.getText().toLowerCase();
//        String password1 = passwordField.getText();
//        String password2 = confirmPasswordField.getText();
//
//        boolean signup = isEmpty(fullName, fullNameTextField) &&
//                isCorrectFormat(fullName, fullNameTextField, Pattern.compile("^[^\",]+$", Pattern.CASE_INSENSITIVE)) &&
//                isEmpty(username, usernameTextField) &&
//                isCorrectFormat(username, usernameTextField, Pattern.compile("^[^\",]+$", Pattern.CASE_INSENSITIVE)) &&
//                isAvailable(username, usernameTextField) &&
//                isEmpty(phoneNumber, phoneNumberTextField) &&
//                isCorrectFormat(phoneNumber, phoneNumberTextField, Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE)) &&
//                isEmpty(email, emailTextField) &&
//                isCorrectFormat(email, emailTextField, Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)) &&
//                isEmpty(password1, passwordField) &&
//                isCorrectFormat(password1, passwordField, Pattern.compile("^[A-Za-z0-9]{8,}$")) &&
//                isEmpty(password2, confirmPasswordField) &&
//                isCorrectFormat(password2, confirmPasswordField, Pattern.compile("^[A-Za-z0-9]{8,}$"));
//        if (!password1.equals(password2)) {
//            passwordCheckLabel.setText("Password doesn't match");
//            signup = false;
//        } else {
//            passwordCheckLabel.setText("");
//        }
//        if (signup) {
//            User user = new User(fullName, username, phoneNumber, email, password1, "null", false, false);
//            user.setImage(bi);
//            user.setShop(null);
//            user.setOnlineTime();
//            if (userListDataSource.getUserList().getAllUsers().size() == 0) {
//                userList = new UserList();
//            } else {
//                userList = userListDataSource.getUserList();
//            }
//            userList.addUser(user);
//            userListDataSource.setUserList(userList);
//            userListDataSource.saveData();
//            userListDataSource.logTime(user);
//            Information info = new Information(user);
//            try {
//                FXRouter.goTo("home",info);
//            } catch (IOException e) {
//                System.err.println("ไปที่หน้า home ไม่ได้");
//                System.err.println("ให้ตรวจสอบการกำหนด route");
//            }
//        }
//    }
//    @FXML
//    public void handleToLogin(ActionEvent actionEvent){
//        try {
//            FXRouter.goTo("login");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า login_scene ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
}
