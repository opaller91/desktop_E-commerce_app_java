package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.shop.models.*;
import com.github.saacsos.FXRouter;
import ku.cs.shop.services.BanListDataSource;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.UserListDataSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginSceneController {


    @FXML private Label at1;

    @FXML private Label at2;

    @FXML private Label at3;

    @FXML private Label at4;

    @FXML private Label at5;

    @FXML private Label at6;

    @FXML private JFXButton btnAddPhoto;

    @FXML private JFXButton btnAdmin;

    @FXML private JFXButton btnCredit;

    @FXML private JFXButton btnSignIn;

    @FXML private JFXButton btnSignUp;

    @FXML private Label confirmPassword;

    @FXML private PasswordField confirmPasswordField;

    @FXML private Label createAccount;

    @FXML private Label createPassword;

    @FXML private Label email;

    @FXML private TextField emailTextField;

    @FXML private TextField fullNameTextField;

    @FXML private ImageView imageView;

    @FXML private AnchorPane layer1;

    @FXML private AnchorPane layer2;

    @FXML private AnchorPane layerSignUp;

    @FXML private Label name;

    @FXML private PasswordField passwordField;

    @FXML private Label phoneNumber;

    @FXML private TextField phoneNumberTextField;

    @FXML private JFXButton signIn;

    @FXML private Label signInTo;

    @FXML private JFXButton signUp;

    @FXML private TextField userNameTextField;

    @FXML private Label username;

    @FXML private TextField usernameTextField;

    @FXML private PasswordField passwordTextField;

    @FXML private Label passwordCheckLabel;

    @FXML private StackPane stack;

    @FXML private Label userNotFoundLabel;

    @FXML private Label wrongPasswordLabel;

    @FXML private Label noUsernameLabel;

    @FXML private Label noPassword;

    @FXML private AnchorPane show;

    @FXML private Label usernameLabel;

    @FXML private Label passwordLabel;

    @FXML private Line loginLine;

    @FXML private Line createLine;

    @FXML private Label dnHaveLabel;

    @FXML private Label accountLabel;

    @FXML private Label welcomeLabel;

    @FXML private Label backLabel;

    @FXML private Label createLabel;

    @FXML private Label personalLabel;

    @FXML private Label readyLabel;

    @FXML private Label enjoyLabel;

    @FXML private Label passwordUse;

    private UserListDataSource userListDataSource = new UserListDataSource();
    private UserList userList;
    private Information info;
    //----------------------------------------
    private BufferedImage bi = null;
    User user;

    @FXML
    public void handleLogin(ActionEvent event){
        String username = usernameTextField.getText().toLowerCase();
        String password = passwordTextField.getText();
        usernameTextField.clear();
        passwordTextField.clear();
        boolean login = isLogin(username, password);
        if (login) {
            try {
                User user = userList.findUser(username);
                user.setShop();
                user.setImage(null);
                user.setOnlineTime();
                userListDataSource.logTime(user);
                info = new Information(user);
                FXRouter.goTo("home", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    public void handleLoginAdmin(ActionEvent event){
        String username = usernameTextField.getText().toLowerCase();
        String password = passwordTextField.getText();
        usernameTextField.clear();
        passwordTextField.clear();
        boolean login = isLogin(username, password);
        if (login) {
            try {
                User user = userList.findUser(username);
                if (!user.isAdmin()) {
                    System.err.println(username + " is not an admin");
                    return;
                }
                user.setShop();
                user.setImage(null);
                user.setOnlineTime();
                userListDataSource.logTime(user);
                info = new Information(user);
                FXRouter.goTo("admin", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า admin ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    private boolean isLogin(String username, String password) {
        passwordTextField.setStyle("-fx-prompt-text-fill: black; -fx-border-color: black ;");
        usernameTextField.setStyle("-fx-prompt-text-fill: black; -fx-border-color: black ;");
        userNotFoundLabel.setVisible(false);
        wrongPasswordLabel.setVisible(false);
        noUsernameLabel.setVisible(false);
        noPassword.setVisible(false);
        User user = userList.findUser(username);
        if (password.isEmpty() || username.isEmpty() ) {
            System.err.println("No username/password");
            if(password.isEmpty() && username.isEmpty()){
                passwordTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
                noPassword.setVisible(true);
                noPassword.setStyle("-fx-prompt-text-fill: red;");
                usernameTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
                noUsernameLabel.setVisible(true);
                noUsernameLabel.setStyle("-fx-prompt-text-fill: red;");
            }
            else if(username.isEmpty()){
                usernameTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
                noUsernameLabel.setVisible(true);
                noUsernameLabel.setStyle("-fx-prompt-text-fill: red;");
            }
            else{
                passwordTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
                noPassword.setVisible(true);
                noPassword.setStyle("-fx-prompt-text-fill: red;");
            }
            return false;
        }else if (user == null) {
            System.err.println("User not found");
            userNotFoundLabel.setVisible(true);
            usernameTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
            userNotFoundLabel.setStyle("-fx-prompt-text-fill: red;");
            return false;
        } else if (!password.equals(user.getPassword())) {
            System.err.println("Wrong password");
            wrongPasswordLabel.setVisible(true);
            passwordTextField.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red ;");
            wrongPasswordLabel.setStyle("-fx-prompt-text-fill: red;");
            return false;
        } else if (user.isBanned()) {
            BanListDataSource banListDataSource = new BanListDataSource("User");
            BanList banList = banListDataSource.getBanList();
            Ban ban = banList.findObjectID("User",username);
            System.out.println(ban.toCSV());
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type,"User:"+ban.getObjectID()+"\nReason:"+ban.getReason()+"\nBan Time:"+ban.getBanTimeStr());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stack.getScene().getWindow());
            alert.getDialogPane().setHeaderText("Cannot Sign In!");
            alert.showAndWait();
            userListDataSource.logTime(user);
            System.err.println(username + "is banned");
            return false;
        }
        return true;
    }
//-----------------------------------create account-----------------
    private boolean isEmpty (String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red;-fx-border-color: red ");
            return false;
        } else {
            field.setStyle("-fx-prompt-text-fill: Gray");
            return true;
        }
    }

    private boolean isEmpty (String string, PasswordField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red;-fx-border-color: red");
            return false;
        } else {
            field.setStyle("-fx-prompt-text-fill: Gray");
            return true;
        }
    }

    private boolean isCorrectFormat (String string, TextField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: black;-fx-border-color: black");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red;-fx-border-color: red");
            return false;
        }
    }

    private boolean isCorrectFormat (String string, PasswordField field, Pattern regex) {
        if (regex.matcher(string).find()) {
            field.setStyle("-fx-text-fill: black;-fx-border-color: black");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red;-fx-border-color: red");
            return false;
        }
    }

    private boolean isAvailable(String username, TextField field) {
        userList = userListDataSource.getUserList();
        User temp = userList.findUser(username);
        if (!username.contains("null") && temp == null) {
            field.setStyle("-fx-text-fill: black;-fx-border-color: black");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red;-fx-border-color: red");
            return false;
        }
    }

    public void handleAddPhoto(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File picFile = fileChooser.showOpenDialog(stage);
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        if (picFile != null) {
            imageView.setImage(new Image(picFile.toURI().toString()));
        }
        try {
            bi = ImageIO.read(picFile);
        } catch (IOException e) {
            System.err.println("Cannot load picture");
        }
    }

    @FXML
    public void handleSignUpButton (ActionEvent actionEvent) throws IOException {
        String fullName = fullNameTextField.getText();
        String username = userNameTextField.getText().toLowerCase();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText().toLowerCase();
        String password1 = passwordField.getText();
        String password2 = confirmPasswordField.getText();

        boolean signup = isEmpty(fullName, fullNameTextField) &&
                isCorrectFormat(fullName, fullNameTextField, Pattern.compile("^[^\",]+$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(username, userNameTextField) &&
                isCorrectFormat(username, userNameTextField, Pattern.compile("^[^\",\\s]+$", Pattern.CASE_INSENSITIVE)) &&
                isAvailable(username, userNameTextField) &&
                isEmpty(phoneNumber, phoneNumberTextField) &&
                isCorrectFormat(phoneNumber, phoneNumberTextField, Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(email, emailTextField) &&
                isCorrectFormat(email, emailTextField, Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(password1, passwordField) &&
                isCorrectFormat(password1, passwordField, Pattern.compile("^[A-Za-z0-9]{8,}$")) &&
                isEmpty(password2, confirmPasswordField) &&
                isCorrectFormat(password2, confirmPasswordField, Pattern.compile("^[A-Za-z0-9]{8,}$"));
        if(password2.isEmpty()&&!password1.isEmpty()){
            passwordCheckLabel.setVisible(false);
        }
        else if (!password1.equals(password2)) {
            passwordCheckLabel.setVisible(true);
            confirmPasswordField.setStyle("-fx-text-fill: red;-fx-border-color: red");
            passwordCheckLabel.setText("Password doesn't match");
            signup = false;
        } else {
            passwordCheckLabel.setVisible(false);
            passwordCheckLabel.setText("");
        }
        if (signup) {
            user = new User(fullName, username, phoneNumber, email, password1, "null", false, false);
            user.setImage(bi);
            user.setShop(null);
            user.setOnlineTime();
            if (userListDataSource.getUserList().getAllUsers().size() == 0) {
                userList = new UserList();
            } else {
                userList = userListDataSource.getUserList();
            }
            userList.addUser(user);
            userListDataSource.setUserList(userList);
            userListDataSource.saveData();
            userListDataSource.logTime(user);
            Information info = new Information(user);
            try {
                FXRouter.goTo("home",info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    public void initialize() throws IOException {
        System.out.println("initialize LoginController");
        if (!WelcomeSceneController.isWelcomeLoad){
            loadWelcomeScene();
        }
        StackPane pane = FXMLLoader.load(getClass().getResource("/ku/cs/login_slider_show.fxml"));
        show.getChildren().setAll(pane);
        userList = userListDataSource.getUserList();
        btnSignIn.setVisible(true);
        btnAdmin.setVisible(true);
        usernameTextField.setVisible(true);
        passwordField.setVisible(true);
        btnCredit.setVisible(true);
        signUp.setVisible(true);
        passwordTextField.setVisible(true);
        dnHaveLabel.setVisible(true);
        accountLabel.setVisible(true);
        welcomeLabel.setVisible(false);
        backLabel.setVisible(false);
        createLabel.setVisible(true);
        personalLabel.setVisible(true);
        passwordUse.setVisible(false);
        readyLabel.setVisible(false);
        enjoyLabel.setVisible(false);
        createLine.setVisible(false);
        signIn.setVisible(false);
        createAccount.setVisible(false);
        imageView.setVisible(false);
        btnAddPhoto.setVisible(false);
        at1.setVisible(false);
        at2.setVisible(false);
        at3.setVisible(false);
        at4.setVisible(false);
        at5.setVisible(false);
        at6.setVisible(false);
        name.setVisible(false);
        username.setVisible(false);
        phoneNumber.setVisible(false);
        email.setVisible(false);
        createPassword.setVisible(false);
        confirmPassword.setVisible(false);
        fullNameTextField.setVisible(false);
        userNameTextField.setVisible(false);
        phoneNumberTextField.setVisible(false);
        emailTextField.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordField.setVisible(false);
        btnSignUp.setVisible(false);
        passwordCheckLabel.setVisible(false);
        userNotFoundLabel.setVisible(false);
        wrongPasswordLabel.setVisible(false);
        noUsernameLabel.setVisible(false);
        noPassword.setVisible(false);

    }

    @FXML
    public void handleSwitchToSignUp(ActionEvent event){
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);

        //804
        slide.setToX(768);
        slide.play();

        layer1.setTranslateX(-549);
        btnSignIn.setVisible(false);
        btnAdmin.setVisible(false);
        usernameTextField.setVisible(false);
        passwordField.setVisible(false);
        btnCredit.setVisible(false);
        signInTo.setVisible(false);
        signUp.setVisible(false);
        passwordTextField.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        loginLine.setVisible(false);
        dnHaveLabel.setVisible(false);
        accountLabel.setVisible(false);
        welcomeLabel.setVisible(true);
        backLabel.setVisible(true);
        createLabel.setVisible(false);
        personalLabel.setVisible(false);
        readyLabel.setVisible(true);
        enjoyLabel.setVisible(true);
        createLine.setVisible(true);
        signIn.setVisible(true);
        createAccount.setVisible(true);
        imageView.setVisible(true);
        btnAddPhoto.setVisible(true);
        at1.setVisible(true);
        at2.setVisible(true);
        at3.setVisible(true);
        at4.setVisible(true);
        at5.setVisible(true);
        at6.setVisible(true);
        name.setVisible(true);
        username.setVisible(true);
        phoneNumber.setVisible(true);
        email.setVisible(true);
        createPassword.setVisible(true);
        confirmPassword.setVisible(true);
        fullNameTextField.setVisible(true);
        userNameTextField.setVisible(true);
        phoneNumberTextField.setVisible(true);
        emailTextField.setVisible(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
        btnSignUp.setVisible(true);
        usernameTextField.clear();
        passwordTextField.clear();
        userNotFoundLabel.setVisible(false);
        wrongPasswordLabel.setVisible(false);
        noUsernameLabel.setVisible(false);
        noPassword.setVisible(false);
        passwordUse.setVisible(true);
        passwordTextField.setStyle("-fx-prompt-text-fill: black; -fx-border-color: black ;");
        usernameTextField.setStyle("-fx-prompt-text-fill: black; -fx-border-color: black ;");

        slide.setOnFinished((e->{

        }));

    }

    @FXML
    public void handleSwitchToSignIn(ActionEvent event){
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);

        slide.setToX(0);
        slide.play();

        layer1.setTranslateX(0);
        btnSignIn.setVisible(true);
        btnAdmin.setVisible(true);
        usernameTextField.setVisible(true);
        passwordField.setVisible(true);
        btnCredit.setVisible(true);
        signInTo.setVisible(true);
        signUp.setVisible(true);
        passwordTextField.setVisible(true);
        usernameLabel.setVisible(true);
        passwordLabel.setVisible(true);
        loginLine.setVisible(true);
        dnHaveLabel.setVisible(true);
        accountLabel.setVisible(true);
        welcomeLabel.setVisible(false);
        backLabel.setVisible(false);
        createLabel.setVisible(true);
        personalLabel.setVisible(true);
        readyLabel.setVisible(false);
        enjoyLabel.setVisible(false);
        createLine.setVisible(false);
        signIn.setVisible(false);
        createAccount.setVisible(false);
        imageView.setVisible(false);
        btnAddPhoto.setVisible(false);
        at1.setVisible(false);
        at2.setVisible(false);
        at3.setVisible(false);
        at4.setVisible(false);
        at5.setVisible(false);
        at6.setVisible(false);
        name.setVisible(false);
        username.setVisible(false);
        phoneNumber.setVisible(false);
        email.setVisible(false);
        createPassword.setVisible(false);
        confirmPassword.setVisible(false);
        fullNameTextField.setVisible(false);
        userNameTextField.setVisible(false);
        phoneNumberTextField.setVisible(false);
        emailTextField.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordField.setVisible(false);
        btnSignUp.setVisible(false);
        passwordCheckLabel.setVisible(false);
        passwordUse.setVisible(false);
        fullNameTextField.clear();
        userNameTextField.clear();
        phoneNumberTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        imageView.setImage(null);

        slide.setOnFinished((e->{

        }));
    }

    private void loadWelcomeScene(){
        try {
            StackPane pane = FXMLLoader.load(getClass().getResource("/ku/cs/welcome_scene.fxml"));
            WelcomeSceneController.isWelcomeLoad = true;
            stack.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished((e)->{
                fadeOut.play();
            });

            fadeOut.setOnFinished((e)->{
                try {
                    StackPane parentContent = FXMLLoader.load(getClass().getResource("/ku/cs/login_scene.fxml"));
                    stack.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCreditButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("credits");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า credits ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}