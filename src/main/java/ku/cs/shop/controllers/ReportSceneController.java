package ku.cs.shop.controllers;
import com.github.saacsos.FXRouter;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.shop.models.*;
import ku.cs.shop.models.reports.ItemReport;
import ku.cs.shop.models.reports.ReportList;
import ku.cs.shop.models.reports.ReviewReport;
import ku.cs.shop.services.ReportListDataSource;

import java.io.IOException;

public class ReportSceneController {

    @FXML private Label reportNameLabel1;
    @FXML private Label objectNameLabel1;
    @FXML private TextArea reasonTextArea;
    @FXML private AnchorPane anchorpane;
    @FXML private JFXTextArea objectDescriptionTextArea;
    @FXML private Label objectNameLabel2;
    @FXML private ImageView userImageView;
    @FXML private Label usernameLabel;
    @FXML private Label reportNameLabel2;

    Information info = (Information) FXRouter.getData();
    User user = info.getUser();
    Review review = info.getReview();
    Item item = info.getItem();

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("item",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า item ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAddReport(ActionEvent event) {
        String reason = reasonTextArea.getText()
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");

        boolean confirm = true; // for future conditions

        if (confirm) {
            ReportListDataSource rlData = new ReportListDataSource(true);
            ReportList rl = rlData.getReportList();
            String reportID = rl.findNextID() + "";
            if (review != null) {
                ReviewReport report = new ReviewReport(reportID,user,reason,false,review);
                rl.addReport(report);
            } else if (item != null) {
                ItemReport report = new ItemReport(reportID,user,reason,false,item);
                rl.addReport(report);
            }
            rlData.setReportList(rl);
            rlData.saveData();
            System.out.println(rl.toCsv());
        }

        try {
            info.setReview(null);
            info.setItem(null);
            FXRouter.goTo("market",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void initialize(){
        usernameLabel.setText(user.getFullName());
        userImageView.setImage(user.getImage());
        objectNameLabel1.setText("");
        objectNameLabel2.setText("");
        objectDescriptionTextArea.setText("");
        if (review != null) {
            reportNameLabel1.setText("Reporting a review");
            reportNameLabel2.setText("Reporting a review");
            objectNameLabel1.setText(review.getUsername());
            objectNameLabel2.setText(review.getUsername());
            objectDescriptionTextArea.setText(review.getComment());
        } else if (item != null) {
            reportNameLabel1.setText("Reporting an item");
            reportNameLabel2.setText("Reporting an item");
            objectNameLabel1.setText(item.getItemName());
            objectNameLabel2.setText(item.getItemName());
            objectDescriptionTextArea.setText(item.getItemDescription());
        } else {
            try {
                System.err.println("Couldn't find anything to report, did someone make a mistake?");
                info.setReview(null);
                info.setItem(null);
                FXRouter.goTo("market",info);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า market ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

}
