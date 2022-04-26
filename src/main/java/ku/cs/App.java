package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.models.ReviewList;
import ku.cs.shop.models.UserList;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.ReviewListDataSource;
import ku.cs.shop.services.UserListDataSource;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

//    @Override
//    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("login_scene"), 800, 600);
//        stage.setScene(scene);
//        stage.show("");

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Project Namkhing", 1315.0, 800.0);
        configRoute();
        FXRouter.goTo("login");
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("login", packageStr + "login_scene.fxml");
        FXRouter.when("admin", packageStr + "admin_scene.fxml");
        FXRouter.when("user_setting", packageStr + "user_setting_scene.fxml");
        FXRouter.when("home", packageStr + "home_scene.fxml");
        FXRouter.when("credits", packageStr + "credit_scene.fxml");
        FXRouter.when("register", packageStr + "create_account_scene.fxml");
        FXRouter.when("shop_info_shop", packageStr + "shop_info_shop_scene.fxml");
        FXRouter.when("create_shop", packageStr + "create_shop_scene.fxml");
        FXRouter.when("shop_stock", packageStr + "shop_stock_scene.fxml");
        FXRouter.when("item", packageStr + "item_detail_scene.fxml");
        FXRouter.when("shop_info_customer", packageStr + "shop_info_customer_scene.fxml");
        FXRouter.when("add_item", packageStr + "add_item_scene.fxml");
        FXRouter.when("market", packageStr + "market_scene.fxml");
        FXRouter.when("order_list", packageStr + "order_list_scene.fxml");
        FXRouter.when("edit_item", packageStr + "edit_item_scene.fxml");
        FXRouter.when("order_list", packageStr + "order_list_scene.fxml");
        FXRouter.when("purchase_order", packageStr + "purchase_order_scene.fxml");
        FXRouter.when("voucher_list", packageStr + "voucher_list_scene.fxml");
        FXRouter.when("add_voucher", packageStr + "add_voucher_scene.fxml");
        FXRouter.when("admin_voucher", packageStr + "admin_voucher_scene.fxml");
        FXRouter.when("admin_add_voucher", packageStr + "admin_add_voucher_scene.fxml");
        FXRouter.when("admin_report", packageStr + "admin_report_scene.fxml");
        FXRouter.when("admin_accept_report", packageStr + "admin_accept_report_scene.fxml");
        FXRouter.when("review", packageStr + "review_scene.fxml");
        FXRouter.when("voucher", packageStr + "voucher_detail_scene.fxml");
        FXRouter.when("report", packageStr + "report_scene.fxml");
        FXRouter.when("show_product", packageStr + "show_product.fxml");
        FXRouter.when("market_voucher_show", packageStr + "market_voucher_show.fxml");
        FXRouter.when("item_info", packageStr + "item_info.fxml");
    }


    public static void main(String[] args) {
        launch();
    }



}


