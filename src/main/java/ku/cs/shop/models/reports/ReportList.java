package ku.cs.shop.models.reports;

import java.util.ArrayList;

public class ReportList {

    private ArrayList<Report> reports;

    public ReportList() {
        reports = new ArrayList<>();
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public ArrayList<Report> getAllReports() {
        return reports;
    }

    public Report findID(String reportID){
        Report report = null;
        if(reportID.equals("null")){
            return null;
        }
        for(Report temp : reports){
            if(reportID.equals(temp.getReportID())){
                report = temp;
                break;
            }
        }
        return report;
    }

    public void removeReport(String reportID) {
        Report report = findID(reportID);
        reports.remove(report);
    }

    public int findNextID() {
        int i = 1;
        boolean idFound = false;
        if (reports.size() == 0) return 1;
        for (; i < reports.size(); i++) { // loop ids
            idFound = false; // set to false
            for (Report temp : reports) { // search all items
                if (Integer.parseInt(temp.getReportID()) == i) { // if id exists
                    idFound = true; // found id
                    break; // go to next id
                }
            }
            if (!idFound) { // if there's no id
                return i; // return the id
            }
        }
        return ++i; // if 1-n ids are taken, send the next one
    }

    public String toCsv() {
        String result = "";
        for (Report report : reports) {
            result += report.toCSV() + "\n";
        }
        return result;
    }

    public void isDoneFilter(boolean isDone) {
        ArrayList<Report> temp = new ArrayList<>();
        for (Report report : reports) {
            if (report.isDone() == isDone) {
                temp.add(report);
            }
        }
        reports = temp;
    }
}
