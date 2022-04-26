package ku.cs.market.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import ku.cs.shop.controllers.MarketSceneController;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.ShowStarRatings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ShowProductController {

    @FXML
    private GridPane productContainer;


    Information info;
    ItemListDataSource dataSource = new ItemListDataSource();
    ItemList itemList;
    ArrayList<Item> items = new ArrayList<>();


    public void setData(Information info, ItemList itemList){
        this.info = info;
        this.itemList = itemList;
        showProduct();
    }

    public void clearItem(){
        productContainer.getChildren().clear();
        items.clear();
    }

    private void showProduct() {
        int column = 0;
        int row = 1;

        items.addAll(itemList.getAllItems());
        try {

            for (Item item : items) {
                String filepath = "assets" + File.separator +
                        "images" + File.separator +
                        "item" + File.separator +
                        item.getItemID() + ".png";
                Image image = dataSource.getImage(filepath);


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/item.fxml"));
                AnchorPane itemBox = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                info.setItem(item);
                itemController.setData(info, image, new ShowStarRatings());

                if (column == 4) {
                    column = 0;
                    ++row;
                }
                productContainer.add(itemBox, column++, row);
                GridPane.setMargin(itemBox, new Insets(8.5));


            }
        } catch ( IOException e) {
            System.err.println("Not found item.fxml");
        }


    }

}
