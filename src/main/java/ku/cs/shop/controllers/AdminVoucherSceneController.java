package ku.cs.shop.controllers;

import com.github.saacsos.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.User;
import ku.cs.shop.models.vouchers.*;
import ku.cs.shop.services.VoucherListDataSource;

import java.io.IOException;

public class AdminVoucherSceneController {

    @FXML private TableView<Voucher> voucherTableView;
    TableColumn codenameColumn = new TableColumn("codename");
    TableColumn percentColumn = new TableColumn("percent");
    TableColumn expiryDateColumn = new TableColumn("expiryDate");
    @FXML private Label codenameLabel;
    @FXML private Label shopNameLabel;
    @FXML private Label voucherTypeLabel;
    @FXML private Label percentLabel;
    @FXML private Label expiryDateLabel;


    Information info = (Information) FXRouter.getData();
    VoucherListDataSource vlData = new VoucherListDataSource();
    VoucherList vl;
    Voucher voucher;

    @FXML
    public void handleBackButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("admin",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void showSelectedVoucher(Voucher voucher){
        codenameLabel.setText(voucher.getCodename());
        if (voucher.getType().equals("ShopVoucher") ||
                voucher.getType().equals("ShopAmountVoucher") ||
                voucher.getType().equals("ShopPriceVoucher")) {
            ShopVoucher temp = (ShopVoucher) voucher;
            shopNameLabel.setText(temp.getShopName());
        } else {
            shopNameLabel.setText("-");
        }
        voucherTypeLabel.setText(voucher.getType());
        percentLabel.setText(String.valueOf(voucher.getPercent()));
        expiryDateLabel.setText(String.valueOf(voucher.getExpiryDateStr()));
    }

    public void clearSelectedVoucher(){
        codenameLabel.setText("");
        shopNameLabel.setText("");
        voucherTypeLabel.setText("");
        percentLabel.setText("");
        expiryDateLabel.setText("");
    }

    private void handleSelectedListView() {
        voucherTableView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Voucher>() {
                    @Override
                    public void changed(ObservableValue<? extends Voucher> observable,
                                        Voucher oldValue, Voucher newValue) {
                        voucher = newValue;
                        showSelectedVoucher(voucher);
                    }
                });
    }

    @FXML
    public void handleAddVoucherButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("admin_add_voucher",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin add voucher ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void initialize() {

        codenameColumn.setCellValueFactory(new PropertyValueFactory<Voucher, String>("codename"));
        codenameColumn.setMinWidth(170);
        percentColumn.setCellValueFactory(new PropertyValueFactory<Voucher, String>("percent"));
        percentColumn.setMinWidth(170);
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<Voucher, String>("expiryDateStr"));
        expiryDateColumn.setMinWidth(170);


        vl = vlData.getVoucherList();
        ObservableList<Voucher> vouchers = FXCollections.observableArrayList();
        vouchers.addAll(vl.getVouchers());
        voucherTableView.setItems(vouchers);
        voucherTableView.getColumns().addAll(codenameColumn,percentColumn,expiryDateColumn);
        clearSelectedVoucher();
        handleSelectedListView();
    }
}
