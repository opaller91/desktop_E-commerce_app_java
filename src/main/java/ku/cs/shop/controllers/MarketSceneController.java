package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.saacsos.FXRouter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.market.controllers.*;
import ku.cs.shop.models.*;
import ku.cs.shop.models.vouchers.Voucher;
import ku.cs.shop.models.vouchers.VoucherList;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.VoucherListDataSource;

public class MarketSceneController {

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView userImageView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;


    @FXML
    private StackPane stackPane;


    @FXML
    private GridPane marketContainer;



    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    ItemListDataSource dataSource = new ItemListDataSource();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> itemArrayList = new ArrayList<>();
    ArrayList<Voucher> vouchers = new ArrayList<>();
    ItemList itemList;
    VoucherListDataSource vlData = new VoucherListDataSource();
    VoucherList vl;
    String error = "";




    @FXML
    public void initialize() {

        initProduct();
        initDrawer();
        showMarketSliderBackground();
        showInformation();
        showVoucherSlider();
        showProduct();
        showContactUs();



    }


    private void showInformation() {
        usernameLabel.setText(user.getFullName());
        userImageView.setImage(user.getImage());
    }

    private void showMarketSliderBackground() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/market_slider_show.fxml"));
            StackPane marketSliderBackground = fxmlLoader.load();
            marketContainer.add(marketSliderBackground, 0, 1);


        } catch (IOException e) {
            System.err.println("Not found market_slider_show.fxml");
        }

    }

    private void showVoucherSlider() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/market_voucher_show.fxml"));
            AnchorPane voucherSlider = fxmlLoader.load();
            MarketVoucherShowController marketVoucherShowController = fxmlLoader.getController();
            marketVoucherShowController.showVoucher(info);
            marketContainer.add(voucherSlider, 0, 2);

        } catch (IOException e) {
            System.err.println("Not found market_voucher_show.fxml");
        }

    }

    public void showProduct() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
                AnchorPane showProduct = fxmlLoader.load(getClass().getResource("/ku/cs/show_product.fxml").openStream());
                ShowProductController showProductController = fxmlLoader.getController();
                showProductController.clearItem();
                showProductController.setData(info, itemList);
                marketContainer.add(showProduct, 0, 3);

        } catch (IOException e) {
            System.err.println("Not found show_product.fxml");
        }
    }

    public void showContactUs() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            VBox contactBox = fxmlLoader.load(getClass().getResource("/ku/cs/contact_us_block.fxml").openStream());
            marketContainer.add(contactBox, 0, 4);

        } catch (IOException e) {
            System.err.println("Not found contact_us_market.fxml");
        }
    }


    public void initProduct() {
        itemList = dataSource.getItemList();

        itemArrayList.clear();
        itemArrayList.addAll(itemList.getAllItems());

        for (Item item : itemArrayList) {
            if (item.isBanned()) {
                itemList.removeItem(item.getItemID());
            }
        }

        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                int object1 = Integer.parseInt(o1.getItemID());
                int object2 = Integer.parseInt(o2.getItemID());
                if(object1 - object2 < 0) return 1;
                if(object1 - object2 > 0) return -1;
                return 0;
            }
        });


    }

    private void initDrawer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/filter_bar.fxml"));
            AnchorPane filterBar = fxmlLoader.load();
            FilterBarController filterBarController = fxmlLoader.getController();
            filterBarController.setItemList(this.itemList);
            filterBarController.setMarketSceneController(this);
            filterBarController.setInfo(info);
            drawer.setSidePane(filterBar);
        } catch (IOException e) {
            Logger.getLogger(MarketSceneController.class.getName()).log(Level.SEVERE, null, e);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });

    }



    public void setItemFilter(ItemList itemList,String error){

        if (error.equals("price")){
            Stage stage = (Stage) stackPane.getScene().getWindow();
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type,"");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.getDialogPane().setContentText("Please enter new range price");
            alert.getDialogPane().setHeaderText("WRONG RANGE PRICE");
            alert.showAndWait();
            itemList = dataSource.getItemList();
        }
        else if (error.equals("shopName")){
            Stage stage = (Stage) stackPane.getScene().getWindow();
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type,"");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.getDialogPane().setContentText("Please enter new shop name");
            alert.getDialogPane().setHeaderText("NO SHOP FOUND");
            alert.showAndWait();
            itemList = dataSource.getItemList();
        }
        this.itemList = itemList;
        showProduct();

    }


    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("home",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
