package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import com.github.saacsos.FXRouter;
import javafx.util.Duration;
import ku.cs.shop.models.*;
import ku.cs.shop.models.vouchers.*;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.OrderListDataSource;
import ku.cs.shop.services.VoucherListDataSource;


import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class PurchaseOrderController {
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemWarningLabel;
    @FXML
    private TextField numberOfItemTextField;
    @FXML
    private TextField promptPayTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField voucherTextField;
    @FXML
    private JFXRadioButton promptPayRadioButton;
    @FXML
    private JFXRadioButton cashRadioButton;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label paymentMethodLabel;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton confrimBtn;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Label lb1;
    @FXML
    private Label lb2;
    @FXML
    private Text numberText;
    @FXML
    private JFXButton processBtn;
    @FXML
    private Text promoText;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Text paymentText;


    private RotateTransition rotateTransition1, rotateTransition2, rotateTransition3;


    Information info = (Information) FXRouter.getData();
    User user = info.getUser();;
    Item item = info.getItem();
    int itemsBuy;
    boolean buyable;
    VoucherListDataSource vlData = new VoucherListDataSource(item.getShopName(),true);
    ItemListDataSource itemData = new ItemListDataSource();
    VoucherList vl;
    Image itemImage;

    @FXML
    public void initialize(){
        userImageView.setImage(user.getImage());
        usernameLabel.setText(user.getFullName());

        String itemImageFilepath = "assets" + File.separator +
                "images" + File.separator +
                "item" + File.separator +
                item.getItemID() + ".png";
        itemImage = itemData.getImage(itemImageFilepath);
    }

    public void start(){
        vl = vlData.getVoucherList();
        itemNameLabel.setText(item.getItemName());
        itemImageView.setImage(itemImage);
        itemImageView.setStyle("-fx-effect: dropShadow(three-pass-box, rgba( 0, 0, 0, 0.1), 10, 0, 0, 10)");
        itemWarningLabel.setText("");
        paymentMethodLabel.setText("");
        promptPayTextField.setVisible(false);
        buyable = false;
        numberOfItemTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    if (!newValue.isEmpty()) {
                        itemsBuy = Integer.parseInt(newValue);
                        if (itemsBuy <= item.getItemStock()) {
                            itemWarningLabel.setText("");
                            buyable = true;
                        } else {
                            itemWarningLabel.setText("More than stock");
                            itemWarningLabel.setStyle("-fx-text-fill: red");
                            buyable = false;
                        }
                    }
                } catch (Exception e) {
                    itemsBuy = Integer.parseInt(oldValue);
                }
            }
        });

    }

    public void choosePaymentCash(){
        promptPayRadioButton.setSelected(false);
        paymentMethodLabel.setStyle("-fx-text-fill: #1EAE98");
        paymentMethodLabel.setText("*You are paying later");
        promptPayTextField.setVisible(false);
    }

    public void choosePaymentPromptPay(){
        cashRadioButton.setSelected(false);
        paymentMethodLabel.setStyle("-fx-text-fill: black");
        paymentMethodLabel.setText("Enter PromptPay");
        paymentMethodLabel.setStyle("-fx-text-fill: #F38EFF");
        promptPayTextField.setVisible(true);
    }

    @FXML
    public void handleConfirmButton(ActionEvent event) { // needs rework, is trash
        String address = addressTextField.getText();
        String voucherCodename = voucherTextField.getText();
        boolean confirm = isEmpty(numberOfItemTextField.getText(), numberOfItemTextField) &&
                isCorrectFormat(numberOfItemTextField.getText(), numberOfItemTextField, Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE)) &&
                (itemsBuy <= item.getItemStock()) && (itemsBuy > 0) &&
                isEmpty(address, addressTextField) &&
                isCorrectFormat(address, addressTextField, Pattern.compile("^[^\",]+$", Pattern.CASE_INSENSITIVE));
        if (!confirm) {
            return;
        }

        Stage stage = (Stage) anchorpane.getScene().getWindow();
        Alert alert;
        String promptPay;
        Voucher voucher = null;
        double price = itemsBuy * item.getItemPrice();
        String confirmString = "Item: " + item.getItemName() + "\n" +
                "Amount: " + itemsBuy + "\n";

        if(!voucherCodename.isEmpty()) { // use voucher
            System.out.println("use voucher");
            voucher = vl.findVoucher(voucherCodename);
            if (voucher == null) { // check if voucher exists
                alert = new Alert(Alert.AlertType.ERROR,"Invalid voucher");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(stage);
                alert.showAndWait();
                return;
            } else { // voucher exists
                price -= (price*voucher.getPercent());
                if (voucher.checkCondition(item.getShopName(),price,itemsBuy)) {
                    confirmString += "Voucher: " + voucherCodename + "\n" +
                            "Total Price: " + price + "\n\n";
                } else {
                    String voucherErrorStr = "Cannot use voucher in this purchase!" + "\n" +
                            "Reason: ";
                    if (!voucher.checkExpiryTime()) {
                        voucherErrorStr += "Voucher expired.";
                    } else if (voucher instanceof AmountVoucher) {
                        voucherErrorStr += "Must buy " + ((AmountVoucher) voucher).getAmountOfItem() + " items at minimum.";
                    } else if (voucher instanceof PriceVoucher) {
                        voucherErrorStr += "Must buy " + ((PriceVoucher) voucher).getPrice() + " Baht at minimum.";
                    } else if (voucher instanceof ShopAmountVoucher) {
                        voucherErrorStr += "Must buy from " + ((ShopAmountVoucher) voucher).getShopName() + "." +
                                " Must buy " + ((ShopAmountVoucher) voucher).getAmountOfItem() + " items at minimum.";
                    } else if (voucher instanceof ShopPriceVoucher) {
                        voucherErrorStr += "Must buy from " + ((ShopPriceVoucher) voucher).getShopName() + "." +
                                " Must buy " + ((ShopPriceVoucher) voucher).getPrice() + " Baht at minimum.";
                    }
                    System.out.println(voucherErrorStr);
                    Alert.AlertType type = Alert.AlertType.WARNING;
                    alert = new Alert(type,voucherErrorStr);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);
                    alert.getDialogPane().setHeaderText("Cannot use voucher in this purchase!");
                    alert.showAndWait();
                    return;
                }

            }
        } else { // doesn't use voucher
            System.out.println("no voucher");
            confirmString += "Voucher: -\n" +
                    "Total Price: " + price + "\n\n";
        }

        if (cashRadioButton.isSelected()) {
            confirmString += "Payment Method: Pay Later (Cash)\n";
            // maybe we can do something over here
        } else if (promptPayRadioButton.isSelected()) {
            confirmString += "Payment Method: PromptPay ";
            promptPay = promptPayTextField.getText();
            confirm = confirm &&
                    isEmpty(promptPay, promptPayTextField) &&
                    isCorrectFormat(promptPay, promptPayTextField, Pattern.compile("^([0-9]{10}|[0-9]{13})$", Pattern.CASE_INSENSITIVE));
            if (!confirm) {
                return;
            }
            confirmString += promptPay + "\n";
        } else {
            alert = new Alert(Alert.AlertType.ERROR,"Choose a payment method");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            return;
        }

        confirmString += "Address: " + address;

        alert = new Alert(Alert.AlertType.CONFIRMATION,confirmString);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println("OK Button Pressed");
                Stage stage1 = (Stage) anchorpane.getScene().getWindow();
                //orderList & orderListDataSource
                OrderListDataSource olData = new OrderListDataSource();
                OrderList ol = olData.getOrders();
                String orderID = ol.findNextOrderID() + "";
                System.out.println(ol.getAllOrders().size());
                System.out.println(orderID);
                Order newOrder = new Order(orderID,itemsBuy,address,info.getUser().getUsername(),info.getUser().getFullName(),item.getItemID(),item.getShopName(), false);


                ol.addOrder(newOrder);
                olData.setOrders(ol);
                olData.saveData();

                ItemListDataSource ilData = new ItemListDataSource();
                ItemList il = ilData.getItemList();
                il.removeItem(item.getItemID());
                item.sold(itemsBuy);
                il.addItem(item);
                ilData.setItemList(il);
                ilData.saveData();

                Alert.AlertType type1 = Alert.AlertType.INFORMATION;
                Alert alert1 = new Alert(type1, "Purchase has been sent!\n" +
                        "Tracking number: " + orderID);

                alert1.initModality(Modality.APPLICATION_MODAL);
                alert1.initOwner(stage1);

                alert1.setContentText("Purchase Success");
                alert1.showAndWait();

                try {
                    FXRouter.goTo("review", info);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("ไปที่หน้า review ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
            else if (result.get() == ButtonType.CANCEL){
                System.out.println("Cancel Button Pressed");
            }

    }

    @FXML
    private void startProcessOrderEffect(ActionEvent event) {
        img1.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/sync.png")).toExternalForm()));
        text1.setStyle("-fx-background-color: linear-gradient(to top left, #8929AD, #436AAC, #43B7B8)");
        text1.setText("Choose a Product");
        rotateTransition1 = new RotateTransition(Duration.seconds(2), img1);
        rotateTransition2 = new RotateTransition(Duration.seconds(2), img2);
        rotateTransition3 = new RotateTransition(Duration.seconds(2), img3);
        RotateTransition transition[] = {rotateTransition1, rotateTransition2, rotateTransition3};
        for(RotateTransition rTransition: transition) {
            rTransition.setCycleCount(1);
            rTransition.setAutoReverse(false);
            rTransition.setFromAngle(720);
            rTransition.setToAngle(0);
        }
        rotateTransition1.play();
        rotateTransition1.setOnFinished(actionEvent -> {
            img1.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/purchases.png")).toExternalForm()));
            lb1.setPrefHeight(4);
            lb1.setStyle("-fx-background-color: black");
            img2.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/sync.png")).toExternalForm()));
            text2.setStyle("-fx-background-color: linear-gradient(to top left, #EA5A6F #DE791E, #FCCF3A)");
            text2.setText("Free Shipping");
            rotateTransition2.play();
        });

        rotateTransition2.setOnFinished(actionEvent -> {
            img2.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/free-delivery-order.png")).toExternalForm()));
            lb2.setPrefHeight(4);
            lb2.setStyle("-fx-background-color: black");
            img3.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/sync.png")).toExternalForm()));
            text3.setText("Payment Method");
            text3.setStyle("-fx-background-color: linear-gradient(to top left, #276174 #33C58E, #63FD88)");
            rotateTransition3.play();
        });
        rotateTransition3.setOnFinished(actionEvent -> {
            img3.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/payment-method-order.png")).toExternalForm()));
            processBtn.setVisible(false);
            itemImageView.setVisible(true);
            itemNameLabel.setVisible(true);
            numberText.setVisible(true);
            numberOfItemTextField.setVisible(true);
            addressText.setVisible(true);
            addressTextField.setVisible(true);
            promoText.setVisible(true);
            voucherTextField.setVisible(true);
            paymentText.setVisible(true);
            cashRadioButton.setVisible(true);
            promptPayRadioButton.setVisible(true);
            confrimBtn.setVisible(true);
            paymentMethodLabel.setVisible(true);
            promptPayTextField.setVisible(true);
            itemWarningLabel.setVisible(true);
            start();

        });


    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("item", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า item ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    private boolean isEmpty(String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red; -fx-border-color: red");
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
            field.setStyle("-fx-text-fill: red; -fx-border-color: red");
            return false;
        }
    }
}
