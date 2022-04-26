package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ItemListDataSource;
import com.github.saacsos.FXRouter;
import ku.cs.shop.services.OrderListDataSource;


import java.io.IOException;
import java.util.Optional;

public class OrderListSceneController {
    @FXML private Label orderNameLabel;
    @FXML private Label orderAmountOfItemLabel;
    @FXML private Label orderAddressLabel;
    @FXML private Label orderIDLabel;
    @FXML private Label orderReceiverNameLabel;
    @FXML private JFXListView<Order> newOrderListView;
    @FXML private JFXListView<Order> orderAlreadyShippedListView;
    @FXML private ImageView itemImageView;
    @FXML private AnchorPane anchorpane;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Shop shop = user.getShop();
    //ต้องเอาข้อมูลมาจากฝั่งลูกค้า
    OrderListDataSource olData = new OrderListDataSource(shop.getShopName());
    Order currentOrder = null;

    @FXML
    public void initialize() {
        clearSelectedItem();
        showListViewNewOrder();
        showListViewOrderAlready();
        handleSelectedListViewNewOrder();
        handleSelectedListViewOrderAlready();
    }

    public void clearSelectedItem(){
        orderNameLabel.setText("");
        orderAmountOfItemLabel.setText("");
        orderReceiverNameLabel.setText("");
        orderAddressLabel.setText("");
        orderIDLabel.setText("");
        itemImageView.setImage(null);
    }

    public void showSelectedItem(Order order){
        clearSelectedItem();
        currentOrder = order;
        Item item = order.getItem();
        item.setImage(null);
        orderNameLabel.setText(item.getItemName());
        orderAmountOfItemLabel.setText(order.getAmountOfItems() + "");
        orderReceiverNameLabel.setText(order.getFullname());
        orderAddressLabel.setText(order.getAddress());
        orderIDLabel.setText(order.getOrderID());
        itemImageView.setImage(item.getItemImage());
    }

    private void showListViewNewOrder(){
        OrderList ol = olData.getOrders().checkShipped(false);
        newOrderListView.getItems().addAll(ol.getAllOrders());
        newOrderListView.refresh();

    }

    private void showListViewOrderAlready(){
        OrderList ol = olData.getOrders().checkShipped(true);
        orderAlreadyShippedListView.getItems().addAll(ol.getAllOrders());
        orderAlreadyShippedListView.refresh();
    }

    private void handleSelectedListViewNewOrder() {
        newOrderListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable,
                                        Order oldValue, Order newValue) {
                        if (newValue == null) {
                            return;
                        }
                        System.out.println(newValue + " is selected");
                        showSelectedItem(newValue);
                    }
                });
    }

    private void handleSelectedListViewOrderAlready() {
        orderAlreadyShippedListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override
                    public void changed(ObservableValue<? extends Order> observable,
                                        Order oldValue, Order newValue) {
                        if (newValue == null) {
                            return;
                        }
                        System.out.println(newValue + " is selected");
                        showSelectedItem(newValue);
                    }
                });
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("shop_info_shop",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_info_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleShipped(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        Alert alert;
        if (currentOrder == null) {
            alert = new Alert(Alert.AlertType.ERROR,"Choose an order to ship");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            return;
        }
        if (currentOrder.isShipped()) {
            alert = new Alert(Alert.AlertType.ERROR,"Already shipped!");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            return;
        }
        alert = new Alert(Alert.AlertType.CONFIRMATION,"This action cannot be reverted!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.out.println("OK Button Pressed");
            Stage stage1 = (Stage) anchorpane.getScene().getWindow();

            Alert.AlertType type1 = Alert.AlertType.INFORMATION;
            Alert alert1 = new Alert(type1, "Order marked as shipped.");

            alert1.initModality(Modality.APPLICATION_MODAL);
            alert1.initOwner(stage1);

            alert1.setContentText("Order marked as shipped.");
            alert1.showAndWait();

            OrderListDataSource olAllData = new OrderListDataSource(); // gets all orders from file
            OrderList ol = olAllData.getOrders();
            ol.removeOrder(currentOrder.getOrderID()); // remove order & update
            currentOrder.setShipped(true);
            ol.addOrder(currentOrder);
            olAllData.setOrders(ol);
            olAllData.saveData();

            olData = new OrderListDataSource(shop.getShopName()); // refresh orderlist & update everything
            clearSelectedItem();
            newOrderListView.getItems().clear();
            orderAlreadyShippedListView.getItems().clear();
            showListViewNewOrder();
            showListViewOrderAlready();
        }
        else if (result.get() == ButtonType.CANCEL){
            System.out.println("Cancel Button Pressed");
        }
    }
}

