package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.vouchers.Voucher;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class VoucherDetailSceneController {

    @FXML
    private Label codeNameLabel;

    @FXML
    private Label codeNameLabel2;

    @FXML
    private Label codeTypeLabel;

    @FXML
    private Label codeTypeLabel2;

    @FXML
    private JFXTextArea descriptionVoucherTextArea;

    @FXML
    private Label expiryDateLabel;

    @FXML
    private Label expiryDateLabel2;

   Information info = (Information) FXRouter.getData();
   Voucher voucher = info.getVoucher();

    @FXML
    public void initialize() {

        codeNameLabel.setText("CODE : " + voucher.getCodename());
        codeNameLabel2.setText("CODE : " + voucher.getCodename());
        codeTypeLabel.setText("TYPE : " + voucher.getType());
        codeTypeLabel2.setText("TYPE : " + voucher.getType());
        expiryDateLabel.setText("Expiry Date : " + voucher.getExpiryDateStr());
        expiryDateLabel2.setText("Expiry Date : " + voucher.getExpiryDateStr());
        descriptionVoucherTextArea.setText(voucher.getDescription());

    }

    @FXML
    public void handleBackButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("market");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
