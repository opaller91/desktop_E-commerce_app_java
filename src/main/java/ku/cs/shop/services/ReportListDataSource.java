package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.*;
import ku.cs.shop.models.reports.ItemReport;
import ku.cs.shop.models.reports.Report;
import ku.cs.shop.models.reports.ReportList;
import ku.cs.shop.models.reports.ReviewReport;

import java.awt.image.BufferedImage;
import java.io.*;

public class ReportListDataSource implements DataSource<ReportList>{

    private ReportList reportList;

    public ReportListDataSource(boolean doReadData) {
        reportList = new ReportList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    @Override
    public void readData() { // read everything
        String line;
        String filename = "assets" + File.separator + "reports.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                // "reportType,reportID,reporterUsername,reportedObjectID,reason,isDone"
                String[] data = line.split(",");
                String reportType = data[0].trim();
                String reportID = data[1].trim();
                String reporterUsername = data[2].trim();
                UserListDataSource ulData = new UserListDataSource();
                UserList ul = ulData.getUserList();
                User reporter = ul.findUser(reporterUsername);
                String reportedObjectID = data[3].trim();
                String reason = data[4].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                boolean isDone = Boolean.parseBoolean(data[5].trim());
                switch (reportType) {
                    case "Report": {
                        Report report = new Report(reportID, reporter, reason, isDone);
                        reportList.addReport(report);
                        break;
                    }
                    case "ItemReport": {
                        ItemListDataSource ilData = new ItemListDataSource();
                        ItemList il = ilData.getItemList();
                        Item reportedItem = il.findID(reportedObjectID);
                        ItemReport report = new ItemReport(reportID, reporter, reason, isDone, reportedItem);
                        reportList.addReport(report);
                        break;
                    }
                    case "ReviewReport": {
                        ReviewListDataSource rlData = new ReviewListDataSource(true);
                        ReviewList rl = rlData.getReviewList();
                        Review reportedReview = rl.findReview(reportedObjectID);
                        ReviewReport report = new ReviewReport(reportID, reporter, reason, isDone, reportedReview);
                        reportList.addReport(report);
                        break;
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    @Override
    public void saveData() {
        String reviewFilename = "assets" + File.separator + "reports.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(reviewFilename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("reportType,reportID,reporterUsername,reportedObjectID,reason,isDone");
            printWriter.print(reportList.toCsv());

        } catch (FileNotFoundException e) {
            System.err.println("Cannot save file " + reviewFilename);
        } catch (IOException e) {
            System.err.println("Error saving to file");
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file");
            }
        }
    }

    @Override
    public void ifPathNotExist() {
        String directoryName = "assets";
        String filename = "reports.csv";
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ReportList getReportList() {
        return reportList;
    }

    public void setReportList(ReportList reportList) {
        this.reportList = reportList;
    }
}
