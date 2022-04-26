package ku.cs.shop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import ku.cs.shop.models.Ban;
import ku.cs.shop.models.BanList;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.reports.ItemReport;
import ku.cs.shop.models.reports.Report;
import ku.cs.shop.models.reports.ReportList;
import ku.cs.shop.models.reports.ReviewReport;
import ku.cs.shop.services.BanListDataSource;
import ku.cs.shop.services.ReportListDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminAcceptReportSceneController {

    @FXML private Label reporterLabel;
    @FXML private Label typeLabel;
    @FXML private Label reportedNameLabel;
    @FXML private TextArea reportedDescriptionTextArea;
    @FXML private TextArea reasonTextArea;
    @FXML private JFXRadioButton removeObjectRadioButton;
    @FXML private JFXRadioButton banUserRadioButton;
    @FXML private TextArea removeReasonTextArea;
    @FXML private TextArea banReasonTextArea;

    Information info = (Information) FXRouter.getData();
    Report report = info.getReport();

    @FXML
    public void initialize() {
        setText();
    }

    @FXML
    public void handleBackButton (ActionEvent actionEvent){
        try {
            info.setReport(null);
            FXRouter.goTo("admin_report",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAcceptReport (ActionEvent actionEvent){
        boolean doBan = false;
        boolean doRemove = false;
        String removeReason = "";
        String banReason = "";
        if (removeObjectRadioButton.isSelected()) { // remove obj
            doRemove = true;
            removeReason = removeReasonTextArea.getText();
        }
        if (banUserRadioButton.isSelected()) { // ban user
            doBan = true;
            banReason = banReasonTextArea.getText();
        }

        BanListDataSource blData = new BanListDataSource(true);
        BanList bl = blData.getBanList();

        if (doRemove) { // if remove object
            switch (report.getReportType()) {
                case "Report":
                    // dummy report
                    break;
                case "ReviewReport": // removing the review
                    Ban removeObjectReview = new Ban(bl.findNextID() + "",
                            "Review",((ReviewReport) report).getReportedReview().getReviewID(),
                            removeReason,
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                            true);
                    bl.addBan(removeObjectReview);
                    break;
                case "ItemReport": // removing the item
                    Ban removeObjectItem = new Ban(bl.findNextID() + "",
                            "Item",((ItemReport) report).getReportedItem().getItemID(),
                            removeReason,
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                            true);
                    bl.addBan(removeObjectItem);
                    break;
            }
        }
        if (doBan) { // if ban
            switch (report.getReportType()) {
                case "Report":
                    // dummy report
                    break;
                case "ReviewReport": // banning the reviewer (user)
                case "ItemReport": // banning the shop owner (user)
                    Ban banObjectReview = new Ban(bl.findNextID() + "",
                            "User",
                            report.getReportedUsername(),
                            banReason,
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                            true);
                    bl.addBan(banObjectReview);
                    break;
            }
        }

        ReportListDataSource rlData = new ReportListDataSource(true);
        ReportList rl = rlData.getReportList();
        report.report(doBan,doRemove);
        rl.removeReport(report.getReportID());
        rl.addReport(report);
        rlData.setReportList(rl);
        rlData.saveData();

        blData.setBanList(bl);
        blData.saveData();
        try {
            info.setReport(null);
            FXRouter.goTo("admin_report",info);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า admin_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRemoveObjectRadioButton (ActionEvent actionEvent){
        removeReasonTextArea.setVisible(removeObjectRadioButton.isSelected());
    }

    @FXML
    public void handleBanUserRadioButton (ActionEvent actionEvent){
        banReasonTextArea.setVisible(banUserRadioButton.isSelected());
    }

    public void setText() {
        reporterLabel.setText(report.getReporter().getUsername());
        typeLabel.setText(report.getReportType());
        reportedNameLabel.setText(report.getReportedObjectName());
        reportedDescriptionTextArea.setText(report.getReportedObjectDescription());
        reasonTextArea.setText(report.getReason());
        removeReasonTextArea.setVisible(false);
        banReasonTextArea.setVisible(false);
        switch (report.getReportType()) {
            case "Report":
                // dummy report
                removeObjectRadioButton.setText("Remove something");
                banUserRadioButton.setText("Ban someone");
                break;
            case "ReviewReport":
                removeObjectRadioButton.setText("Remove review");
                banUserRadioButton.setText("Ban User [" +
                        report.getReportedObjectName() + "]");
                break;
            case "ItemReport":
                removeObjectRadioButton.setText("Remove item [" +
                        report.getReportedObjectName() + "]");
                banUserRadioButton.setText("Ban User [" +
                        report.getReportedUsername() +
                        "]");
                break;
        }
    }
}
