package ku.cs.market.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Line;

import java.io.IOException;

public class ContactUsBlockController {

    @FXML
    private JFXButton contactBtn;

    @FXML
    private Line lineBtn;

    public void initialize() {

        lineBtn.setStyle("-fx-stroke: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");

        contactBtn.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                lineBtn.setVisible(true);
                contactBtn.setStyle("-fx-text-fill: linear-gradient(to bottom left, #9120a6, #ec5544, #F0bf54)");
            } else {
                lineBtn.setVisible(false);
                contactBtn.setStyle("-fx-text-fill: white");
            }
        });

    }


}
