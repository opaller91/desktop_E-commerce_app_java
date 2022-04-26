package ku.cs.market.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.controllers.ItemDetailSceneController;

import java.util.Objects;


public class RatingController {

    @FXML
    private ImageView ratingImageView;

    @FXML
    private Label ratingLabel;

    @FXML
    private ImageView ratingEffectImageView;

    public void setData(double rating, String path){

        ratingEffectImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/marketimages/rating.gif")).toExternalForm()));
        ratingLabel.setText(rating + "/5.0");
        ratingImageView.setImage(new Image(path));

    }
}
