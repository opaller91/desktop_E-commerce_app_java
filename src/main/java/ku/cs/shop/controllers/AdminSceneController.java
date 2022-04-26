package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.User;
import ku.cs.shop.models.UserList;
import ku.cs.shop.services.UserListDataSource;
import com.github.saacsos.FXRouter;
import java.io.IOException;
import java.util.Objects;

public class AdminSceneController {

    @FXML private ListView<User> userListView;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane backPane;
    @FXML private AnchorPane frontPane;
    @FXML private Label fullNameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label emailLabel;
    @FXML private Label shopNameLabel;
    @FXML private Label lastOnlineTimeLabel;
    @FXML private ImageView imageView;
    @FXML private Line line1;
    @FXML private Line line2;
    @FXML private JFXButton voucherBtn;
    @FXML private JFXButton reportBtn;

    Information info = (Information) FXRouter.getData();

    private UserListDataSource ulData = new UserListDataSource();
    private UserList ul;
    private final ObservableList<Image> backImages= FXCollections.observableArrayList();
    private final ObservableList<Image> frontImages=FXCollections.observableArrayList();
    private static FadeTransition transition;
    private int backIndex=0;
    private int frontIndex=0;

    @FXML
    public void initialize() {
        ul = ulData.getUserList();
        ul.setAllUserShop();
        ul.setAllUserImage();
        ul.sortByLoginTime();
        showListView();
        clearSelectedCard();
        handleSelectedListView();

        Image i1=new Image(Objects.requireNonNull(getClass().getResource("/userimages/1B.jpg")).toExternalForm());
        Image i2=new Image(Objects.requireNonNull(getClass().getResource("/userimages/2B.jpg")).toExternalForm());
        Image i3=new Image(Objects.requireNonNull(getClass().getResource("/userimages/3B.jpg")).toExternalForm());

        setImages(i1,i3,i2);
        initSliderShow(2,2);
        frontPane.toFront();
        frontPane.setOpacity(0);
        backPane.toBack();
        setAnchor(stackPane,0.0,0.0,0.0,0.0);

        voucherBtn.pressedProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                line1.setVisible(false);
            } else {
                line1.setVisible(true);
            }
        });
        reportBtn.pressedProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                line2.setVisible(false);
            } else {
                line2.setVisible(true);
            }
        });
    }

    public static void setAnchor(Node node, Double left, Double top, Double right, Double bottom){
        AnchorPane.setLeftAnchor(node,left);
        AnchorPane.setTopAnchor(node,top);
        AnchorPane.setRightAnchor(node,right);
        AnchorPane.setBottomAnchor(node, bottom);
    }

    public void setImages(Image... images){
        if (images.length<3){
            try {
                throw new Exception("The image quantity must be 3 or more!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            for (int i=0;i<images.length;i++){
                if (i%2==0){
                    frontImages.add(images[i]);
                }else {
                    backImages.add(images[i]);
                }
            }
        }
        setBackgroundImage(backPane,backImages.get(0));
        setBackgroundImage(frontPane,frontImages.get(0));

    }

    public void initSliderShow(int animationDelay, int visibilityDelay){
        Runnable rn=()->{
            Platform.runLater(()->{
                frontPane.opacityProperty().addListener((observable, oldValue, newValue) -> {
                    PauseTransition pt;
                    if (newValue.doubleValue()==0){
                        frontIndex++;
                        if (frontIndex==frontImages.size()){
                            frontIndex=0;
                        }
                        setBackgroundImage(frontPane,frontImages.get(frontIndex));
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }else if (newValue.doubleValue()==1){
                        backIndex++;
                        if (backIndex==backImages.size()){
                            backIndex=0;
                        }
                        setBackgroundImage(backPane,backImages.get(backIndex));
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }
                });
            });

            transition=new FadeTransition(Duration.seconds(animationDelay),frontPane);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.setAutoReverse(true);
            transition.setCycleCount(-1);
            transition.play();

        };
        Thread t=new Thread(rn);
        t.start();
    }

    public synchronized void stop(){
        if (transition!=null){
            transition.stop();
        }
    }

    private void setBackgroundImage(AnchorPane target, Image image){
        BackgroundSize backgroundSize=new BackgroundSize(100, 100,true, true,true, true);
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,backgroundSize);
        Background background=new Background(backgroundImage);
        target.setBackground(background);
    }

    private void showListView() {
        userListView.getItems().addAll(ul.getAllUsers());
        userListView.refresh();
    }

    private void handleSelectedListView() {
        userListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable,
                                        User oldValue, User newValue) {
                        showSelectedUser(newValue);
                    }
                });
    }

    private void showSelectedUser(User user) {
        fullNameLabel.setText(user.getFullName());
        usernameLabel.setText(user.getUsername());
        phoneNumberLabel.setText(user.getPhoneNumber());
        emailLabel.setText(user.getEmail());
        shopNameLabel.setText(user.getShopName());
        lastOnlineTimeLabel.setText(user.getLastOnlineTimeStr());
        imageView.setImage(user.getImage());
    }

    private void clearSelectedCard() {
        fullNameLabel.setText("");
        usernameLabel.setText("");
        phoneNumberLabel.setText("");
        emailLabel.setText("");
        shopNameLabel.setText("");
        lastOnlineTimeLabel.setText("");
        imageView.setImage(null);
    }

    @FXML
    public void handleReport(ActionEvent event){
        try {
            FXRouter.goTo("admin_report",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleVoucher(ActionEvent event){
        try {
            FXRouter.goTo("admin_voucher",info);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า admin_voucher ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            ulData.saveData();
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
