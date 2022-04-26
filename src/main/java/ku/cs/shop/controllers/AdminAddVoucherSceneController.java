package ku.cs.shop.controllers;

import com.github.saacsos.FXRouter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.User;
import ku.cs.shop.models.vouchers.*;
import ku.cs.shop.services.VoucherListDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;


public class AdminAddVoucherSceneController {

    @FXML private TextField codenameTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField percentTextField;
    @FXML private TextField durationTextField;
    @FXML private TextField voucherTypeEnterTextField;
    @FXML private Label voucherTypeEnterLabel;
    @FXML private JFXComboBox<String> voucherTypeCombo;

    Information info = (Information) FXRouter.getData();
    VoucherListDataSource vlData = new VoucherListDataSource();
    VoucherList vl;
    String voucherType;

    private boolean isEmpty(String string, TextField field) {
        if (string.isEmpty()) {
            field.setStyle("-fx-prompt-text-fill: red ;-fx-border-color: red ; -fx-border-width: 2px\"");
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
            field.setStyle("-fx-text-fill: red ;-fx-border-color: red ; -fx-border-width: 2px");
            return false;
        }
    }

    public boolean isComboBoxEmpty() {
        return voucherTypeCombo.getValue().isEmpty();
    }

    private boolean isAvailable(String codename, TextField field) {
        Voucher v = vl.findVoucher(codename);
        if (!codename.contains("null") && v == null) {
            field.setStyle("-fx-text-fill: black");
            return true;
        } else {
            field.setStyle("-fx-text-fill: red ;-fx-border-color: red ; -fx-border-width: 2px");
            return false;
        }
    }

    @FXML
    public void initialize() {
        vl = vlData.getVoucherList();
        voucherTypeCombo.getItems().setAll("Voucher","Amount Voucher","Price Voucher");


        voucherTypeEnterLabel.setText("");
        voucherTypeEnterTextField.setPromptText("");
        voucherTypeEnterLabel.setVisible(false);
        voucherTypeEnterTextField.setVisible(false);
    }

    @FXML
    public void select (ActionEvent event){
        voucherType = voucherTypeCombo.getSelectionModel().getSelectedItem();
        if (voucherType.equals("Voucher")) {
            voucherTypeEnterLabel.setVisible(false);
            voucherTypeEnterTextField.setVisible(false);
        } else if (voucherType.equals("Amount Voucher")) {
            voucherTypeEnterLabel.setText("Minimum amount of items");
            voucherTypeEnterTextField.setPromptText("Enter amount");
            voucherTypeEnterLabel.setVisible(true);
            voucherTypeEnterTextField.setVisible(true);
        } else if (voucherType.equals("Price Voucher")) {
            voucherTypeEnterLabel.setText("Minimum price");
            voucherTypeEnterTextField.setPromptText("Enter price");
            voucherTypeEnterLabel.setVisible(true);
            voucherTypeEnterTextField.setVisible(true);
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("admin_voucher",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_voucher ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAddVoucherButton(ActionEvent event) {
        String codename = codenameTextField.getText().toLowerCase();
        String description = descriptionTextField.getText();
        String percentStr = percentTextField.getText();
        String durationStr = durationTextField.getText();
        String minimumStr = voucherTypeEnterTextField.getText();

        boolean add = isEmpty(codename, codenameTextField) &&
                isCorrectFormat(codename, codenameTextField, Pattern.compile("^[A-Za-z0-9]+$", Pattern.CASE_INSENSITIVE)) &&
                isAvailable(codename, codenameTextField) &&
                isEmpty(description, descriptionTextField) &&
                isEmpty(percentStr, percentTextField) &&
                isCorrectFormat(percentStr, percentTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE)) &&
                isEmpty(durationStr, durationTextField) &&
                isCorrectFormat(durationStr, durationTextField, Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE)) &&
                !isComboBoxEmpty();
        if (voucherType.equals("Amount Voucher")) {
            add = add && isEmpty(minimumStr, voucherTypeEnterTextField) &&
                    isCorrectFormat(minimumStr, voucherTypeEnterTextField, Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE));
        } else if (voucherType.equals("Price Voucher")) {
            add = add && isEmpty(minimumStr, voucherTypeEnterTextField) &&
                    isCorrectFormat(minimumStr, voucherTypeEnterTextField, Pattern.compile("^[0-9]+(\\.)?[0-9]{0,2}$", Pattern.CASE_INSENSITIVE));
        }
        if (add) {
            double percent = Double.parseDouble(percentStr) / 100;
            int days = Integer.parseInt(durationStr);

            if (voucherType.equals("Voucher")) {
                Voucher voucher = new Voucher(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")).plusDays(days));
                vl.addVoucher(voucher);
            } else if (voucherType.equals("Amount Voucher")) {
                int minimum = Integer.parseInt(minimumStr);
                AmountVoucher voucher = new AmountVoucher(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")).plusDays(days),minimum);
                vl.addVoucher(voucher);
            } else if (voucherType.equals("Price Voucher")) {
                double minimum = Double.parseDouble(minimumStr);
                PriceVoucher voucher = new PriceVoucher(codename,percent,description,LocalDateTime.now(ZoneId.of("GMT+7")).plusDays(days),minimum);
                vl.addVoucher(voucher);
            }

            vlData.setVoucherList(vl);
            vlData.saveData();
            try {
                FXRouter.goTo("admin_voucher",info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า admin_voucher ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
