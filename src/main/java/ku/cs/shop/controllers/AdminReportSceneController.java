package ku.cs.shop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.shop.models.Information;
import ku.cs.shop.models.reports.Report;
import ku.cs.shop.models.reports.ReportList;
import ku.cs.shop.services.ReportListDataSource;
import com.github.saacsos.FXRouter;
import java.io.IOException;

public class AdminReportSceneController {

    @FXML private TableView<Report> reportTableView;
    TableColumn reporterColumn = new TableColumn("reporter");
    TableColumn reportTypeColumn = new TableColumn("type");
    TableColumn reportedColumn = new TableColumn("name");
    @FXML private Label reporterLabel;
    @FXML private Label typeLabel;
    @FXML private Label reportedNameLabel;
    @FXML private TextArea reportedDescriptionTextArea;
    @FXML private TextArea reasonTextArea;
    @FXML private AnchorPane anchorpane;

    Information info = (Information) FXRouter.getData();
    Report report;

    @FXML
    public void initialize() {
        reporterColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("reporterUsername"));
        reporterColumn.setMinWidth(170);
        reportTypeColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("reportType"));
        reportTypeColumn.setMinWidth(170);
        reportedColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("reportedObjectName"));
        reportedColumn.setMinWidth(170);

        ReportListDataSource rlData = new ReportListDataSource(true);
        ReportList rl = rlData.getReportList();
        rl.isDoneFilter(false);
        ObservableList<Report> reports = FXCollections.observableArrayList();
        reports.addAll(rl.getAllReports());
        reportTableView.setItems(reports);
        reportTableView.getColumns().addAll(reporterColumn,reportTypeColumn,reportedColumn);
        clearSelectedReport();
        handleSelectedListView();
    }

    public void clearSelectedReport(){
        reporterLabel.setText("");
        typeLabel.setText("");
        reportedNameLabel.setText("");
        reportedDescriptionTextArea.setText("");
        reasonTextArea.setText("");
    }

    private void handleSelectedListView() {
        reportTableView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Report>() {
                    @Override
                    public void changed(ObservableValue<? extends Report> observable,
                                        Report oldValue, Report newValue) {
                        report = newValue;
                        showSelectedReport(report);
                    }
                });
    }

    public void showSelectedReport(Report report){
        reporterLabel.setText(report.getReporter().getUsername());
        typeLabel.setText(report.getReportType());
        reportedNameLabel.setText(report.getReportedObjectName());
        reportedDescriptionTextArea.setText(report.getReportedObjectDescription());
        reasonTextArea.setText(report.getReason());
    }

    @FXML
    public void handleBackButton (ActionEvent actionEvent){
        try {
            FXRouter.goTo("admin",info);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleDenyReport(ActionEvent event) {
        report.report(false,false);
        ReportListDataSource rlData = new ReportListDataSource(true);
        ReportList rl = rlData.getReportList();
        rl.removeReport(report.getReportID());
        rl.addReport(report);
        rlData.setReportList(rl);
        rlData.saveData();
        rl.isDoneFilter(false);
        ObservableList<Report> reports = FXCollections.observableArrayList();
        reports.addAll(rl.getAllReports());
        reportTableView.setItems(reports);

        Stage stage = (Stage) anchorpane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.setHeaderText("Report denied!");
        alert.showAndWait();
    }

    @FXML
    public void handleAcceptReport(ActionEvent event) {
        try {
            info.setReport(report);
            FXRouter.goTo("admin_accept_report",info);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า admin_accept_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
