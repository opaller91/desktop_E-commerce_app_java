package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.ReviewListDataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;


public class ReviewSceneController {

    @FXML private Label itemNameLabel;
    @FXML private Label ratingLabel;
    @FXML private JFXSlider ratingSlider;
    @FXML private JFXTextArea commentTextArea;
    @FXML private AnchorPane anchorpane;
    @FXML private ImageView itemImageView;
    @FXML private ImageView userImageView;
    @FXML private Label usernameLabel;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Item item = info.getItem();
    ItemListDataSource itemData = new ItemListDataSource();
    Image itemImage;

    @FXML
    public void handleAddReviewButton(ActionEvent event) {
        String comment = commentTextArea.getText()
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");

        boolean confirm = true; // for future conditions
//                isCorrectFormat(comment, commentTextArea, Pattern.compile("^[^\",]*$", Pattern.CASE_INSENSITIVE));

        if (confirm) {
            ReviewListDataSource rlData = new ReviewListDataSource(true);
            ReviewList rl = rlData.getReviewList();
            String reviewID = rl.findNextReviewID() + "";
            String itemID = item.getItemID();
            String username = user.getUsername();
            int rating = (int) Math.round(ratingSlider.getValue());
            Review review = new Review(reviewID,itemID,username,rating,comment,false);
            rl.addReview(review);
            rlData.setReviewList(rl);
            rlData.saveData();
            System.out.println(rl.toCsv());
        }

        try {
            info.setItem(null);
            FXRouter.goTo("market", info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {

        Stage stage = (Stage) anchorpane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure? You cannot review again.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                info.setItem(null);
                FXRouter.goTo("market", info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า market ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

    }

    @FXML
    public void initialize(){
        ReviewListDataSource rlData = new ReviewListDataSource(true);

        String itemImageFilepath = "assets" + File.separator +
                "images" + File.separator +
                "item" + File.separator +
                item.getItemID() + ".png";
        itemImage = itemData.getImage(itemImageFilepath);

        usernameLabel.setText(user.getFullName());
        userImageView.setImage(user.getImage());
        itemNameLabel.setText(item.getItemName());
        itemImageView.setImage(itemImage);
        itemImageView.setStyle("-fx-effect: dropShadow(three-pass-box, rgba( 0, 0, 0, 0.1), 10, 0, 0, 10)");
        ratingLabel.setText("5");
        ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                ratingSlider.setValue(Math.round(newNumber.doubleValue()));
                ratingLabel.setText(Math.round(newNumber.doubleValue()) + "");
                ratingLabel.setStyle("-fx-text-fill: linear-gradient(to top left, #f42e78, #c17afc, #f3dcfb)");
            }
        });
    }


}
