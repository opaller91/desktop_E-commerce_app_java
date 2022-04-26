package ku.cs.market.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.models.vouchers.Voucher;
import ku.cs.shop.models.vouchers.VoucherList;
import ku.cs.shop.services.VoucherListDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import com.github.saacsos.FXRouter;

public class MarketVoucherShowController {

    @FXML
    private ImageView flashSaleImageView;

    @FXML
    private HBox voucherContainer;


    Information info;
    ArrayList<Voucher> vouchers = new ArrayList<>();
    VoucherListDataSource vlData = new VoucherListDataSource();
    VoucherList vl;


    @FXML
    public void initialize() {

        Image flashSaleImage = new Image(Objects.requireNonNull(getClass().getResource("/marketimages/flashsale.gif")).toExternalForm());
        flashSaleImageView.setImage(flashSaleImage);

    }

    public void showVoucher(Information info) {
        this.info = info;
        vl = vlData.getVoucherList();
        vouchers.addAll(vl.getVouchers());
        try {
        for(Voucher voucher: vouchers) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/voucher.fxml"));
            VBox voucherBox = fxmlLoader.load();
            VoucherController voucherController = fxmlLoader.getController();
            info.setVoucher(voucher);
            voucherController.setData(info);
            voucherContainer.getChildren().add(voucherBox);

            }
        } catch ( IOException e) {
            System.err.println("Not found voucher.fxml");

        }

    }
}
