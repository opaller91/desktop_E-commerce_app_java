package ku.cs.market.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.Review;
import ku.cs.shop.models.ReviewList;
import ku.cs.shop.services.ReviewListDataSource;
import ku.cs.shop.services.ShowPicture;
import com.github.saacsos.FXRouter;

import java.io.IOException;
import java.util.ArrayList;

public class ItemShopController {

    @FXML
    private ImageView itemImageView;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private ImageView ratingImageView;

    @FXML
    private VBox box;

    Information info;
    Item item;
    ReviewListDataSource reviewData;
    ReviewList reviewList;
    ArrayList<Review> reviews = new ArrayList<>();
    String[] colors = { ": #F7C1C1", ": #EEA058", ": #4F5F79", ": #70977A", ": #828282" };

    public void setData(Information info, Image image, ShowPicture showPicture, int count) {
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
        box.setStyle("-fx-background-color " + colors[count] + ";" +
                "-fx-effect: dropShadow(three-pass-box, rgba( 0, 0, 0, 0.1), 10, 0, 0, 10);");
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
