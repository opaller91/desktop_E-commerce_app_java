package ku.cs.market.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.shop.controllers.ItemDetailSceneController;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;

import java.io.IOException;
import java.util.ArrayList;

import com.github.saacsos.FXRouter;
import ku.cs.shop.models.Review;
import ku.cs.shop.models.ReviewList;
import ku.cs.shop.services.ReviewListDataSource;
import ku.cs.shop.services.ShowPicture;

public class ItemController {


    @FXML
    private ImageView itemImageView;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private ImageView ratingImageView;



    Information info;
    Item item;
    ReviewListDataSource reviewData;
    ReviewList reviewList;
    ArrayList<Review> reviews = new ArrayList<>();


    public void setData(Information info, Image image, ShowPicture showPicture){

        this.info = info;
        item = info.getItem();
        reviewData = new ReviewListDataSource(item.getItemID());
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
        itemImageView.setImage(image);
        itemNameLabel.setText(item.getItemName());
        itemPriceLabel.setText("THB " + item.getItemPrice());
        ratingImageView.setImage(new Image(path));

    }


    @FXML
    private void mouseEntered(MouseEvent mouseEvent) {
        try {
            info.setItem(item);
            FXRouter.goTo("item", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า item ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }


}
