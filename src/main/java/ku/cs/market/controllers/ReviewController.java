package ku.cs.market.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.controllers.ItemDetailSceneController;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.Review;
import ku.cs.shop.models.User;
import com.github.saacsos.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class ReviewController {


    @FXML
    private JFXTextArea commentTextArea;

    @FXML
    private ImageView ratingImageView;

    @FXML
    private ImageView usernameImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView textboxImageView;

    Information info;
    Review review;
    User user;


    public void setData(Information info, Image image, String path){

        this.info = info;
        review = info.getReview();
        user = info.getUser();
        usernameLabel.setText(review.getUsername());
        usernameImageView.setImage(image);
        usernameImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-background-radius: 5em; -fx-border-radius: 3em");
        ratingImageView.setImage(new Image(path));
        commentTextArea.setText(review.getComment());
        textboxImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/textbox.JPG")).toExternalForm()));

    }

    @FXML
    public void handleReportReviewButton(ActionEvent actionEvent) {
        try {
            info.setReview(review);
            FXRouter.goTo("report", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
