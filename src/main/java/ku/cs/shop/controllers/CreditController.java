package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;

import java.io.File;
import java.io.IOException;

public class CreditController {
    @FXML private ImageView imageView1;
    @FXML private ImageView imageView2;
    @FXML private ImageView imageView3;
    @FXML private ImageView imageView4;

    @FXML
    public void initialize() {

        imageView1.setImage(new Image
                (new File("assets" + File.separator +
                        "images" + File.separator +
                        "credit" + File.separator +
                        "leonic.png").toURI().toString()));
        imageView2.setImage(new Image
                (new File("assets" + File.separator +
                        "images" + File.separator +
                        "credit" + File.separator +
                        "ratchathorn.png").toURI().toString()));
        imageView3.setImage(new Image
                (new File("assets" + File.separator +
                        "images" + File.separator +
                        "credit" + File.separator +
                        "rinrada.png").toURI().toString()));
        imageView4.setImage(new Image
                (new File("assets" + File.separator +
                        "images" + File.separator +
                        "credit" + File.separator +
                        "jiratchaya.jpg").toURI().toString()));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }




}
