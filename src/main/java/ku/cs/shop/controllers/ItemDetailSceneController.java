package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.market.controllers.ItemInfoController;
import ku.cs.market.controllers.RatingController;
import ku.cs.market.controllers.ReviewController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.*;

public class ItemDetailSceneController {


    @FXML
    private ImageView userImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    private GridPane itemInfoContainer;

    @FXML
    private AnchorPane anchorpane;



    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Item item = info.getItem();
    ReviewList reviewList;
    UserListDataSource ulData = new UserListDataSource();
    ItemListDataSource itemData = new ItemListDataSource();
    ShopListDataSource shopData = new ShopListDataSource();
    ReviewListDataSource reviewData = new ReviewListDataSource(item.getItemID());
    ArrayList<Review> reviews = new ArrayList<>();
    Image itemImage, userImage, shopImage;


    @FXML
    public void initialize() {
        System.out.println("initialize ItemSceneController");

        showUserInformation();
        showItemInfo();
        showRating(new ShowStarRatings());
        showReview(new ShowStarRatings());


    }

    public void showUserInformation() {
        usernameLabel.setText(user.getFullName());
        userImageView.setImage(user.getImage());
    }

    public void showItemInfo() {

        try {
            String itemImageFilepath = "assets" + File.separator +
                    "images" + File.separator +
                    "item" + File.separator +
                    item.getItemID() + ".png";
            itemImage = itemData.getImage(itemImageFilepath);

            String shopImageFilepath = "assets" + File.separator +
                    "images" + File.separator +
                    "shop" + File.separator +
                     item.getShopName() + ".png";
            shopImage = shopData.getImage(shopImageFilepath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/item_info.fxml"));
            AnchorPane itemInfo = fxmlLoader.load();
            ItemInfoController itemInfoController = fxmlLoader.getController();
            info.setItem(item);
            itemInfoController.setItemDetailSceneController(this);
            itemInfoController.setData(info, itemImage, shopImage);
            itemInfoContainer.add(itemInfo, 0, 1);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRating(ShowPicture showPicture) {
        reviewList = reviewData.getReviewList();
        reviews.addAll(reviewList.getAllReviews());
        for (Review review: reviews) {

            if (review.isBanned()) {
                reviewList.removeReview(review.getReviewID());
            }
        }
        reviews.clear();
        double rating = reviewList.averageRating();
        String path = showPicture.showStarRatings(rating);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/rating.fxml"));
            AnchorPane ratingBox = fxmlLoader.load();
            RatingController ratingController = fxmlLoader.getController();
            ratingController.setData(rating, path);
            itemInfoContainer.add(ratingBox,0,2);
            GridPane.setMargin(ratingBox, new Insets(8));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showReview(ShowPicture showPicture) {
        reviewList = reviewData.getReviewList();
        reviews.addAll(reviewList.getAllReviews());
        for (Review review: reviews) {

            if (review.isBanned()) {
                reviewList.removeReview(review.getReviewID());
            }
        }
        reviews.clear();
        reviews.addAll(reviewList.getAllReviews());
        int row = 3;

        try {
            for (Review review: reviews){

                String userImageFilepath = "assets" + File.separator +
                "images" + File.separator +
                "avatar" + File.separator +
                review.getUsername() + ".png";
                userImage = ulData.getImage(userImageFilepath);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/review.fxml"));
                AnchorPane reviewBox = fxmlLoader.load();
                ReviewController reviewController= fxmlLoader.getController();
                info.setReview(review);
                String path = showPicture.showStarRatings(review.getRating());
                reviewController.setData(info, userImage, path);
                itemInfoContainer.add(reviewBox,0,row++);
                GridPane.setMargin(reviewBox, new Insets(8));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setErrorOutOfStock() {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("Product is Out of stock.");
        alert.getDialogPane().setHeaderText("CAN'T BUY!");
        alert.showAndWait();
    }



    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            info.setItem(null);
            FXRouter.goTo("market", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
