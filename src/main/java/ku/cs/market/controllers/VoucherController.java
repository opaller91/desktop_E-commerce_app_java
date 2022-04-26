package ku.cs.market.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.vouchers.Voucher;
import ku.cs.shop.models.vouchers.VoucherList;
import ku.cs.shop.services.VoucherListDataSource;

import java.io.IOException;


import com.github.saacsos.FXRouter;

public class VoucherController {

    @FXML
    private Label codeNameLabel;

    @FXML
    private Label voucherDetailLabel;

    @FXML
    private Label percentLabel;


    Information info;
    Voucher voucher;


    public void setData(Information info) {
        this.info = info;
        voucher = info.getVoucher();
        codeNameLabel.setText("CODE : " + voucher.getCodename());
        voucherDetailLabel.setText(voucher.getDescription());
        double percent = voucher.getPercent() * 100;
        percentLabel.setText((int)percent + "%");
        percentLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
        codeNameLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
        voucherDetailLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");


        percentLabel.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                percentLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
            } else {
                percentLabel.setStyle("-fx-text-fill: linear-gradient(to top right, #580c4a, #e68caf, #6a47c7)");
            }
        });

        codeNameLabel.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                codeNameLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
            } else {
                codeNameLabel.setStyle("-fx-text-fill: linear-gradient(to top right, #580c4a, #e68caf, #6a47c7)");
            }
        });

        voucherDetailLabel.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                voucherDetailLabel.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
            } else {
                voucherDetailLabel.setStyle("-fx-text-fill: linear-gradient(to top right, #580c4a, #e68caf, #6a47c7)");
            }
        });


    }

    
    @FXML
    private void mouseEntered(MouseEvent mouseEvent) {
        try {
            info.setVoucher(voucher);
            FXRouter.goTo("voucher", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า voucher ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}