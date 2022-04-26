package ku.cs.shop.models.reports;

import ku.cs.shop.models.User;

public class Report { // superclass, for using with other subclasses, don't use this alone

    protected User reporter;
    protected String reporterUsername;
    protected String reportedUsername;
    protected String reason;
    protected String reportID;
    protected String reportType;
    protected String reportedObjectName;
    protected String reportedObjectDescription;
    protected boolean isDone;

    public Report(String reportID, User reporter, String reason, boolean isDone) {
        this.reporter = reporter;
        this.reporterUsername = reporter.getUsername();
        this.reason = reason;
        this.reportID = reportID;
        this.isDone = isDone;
        reportType = "Report";
        reportedObjectName = "";
        reportedObjectDescription = "";
        reportedUsername = "";
    }

    public User getReporter() {
        return reporter;
    }

    public String getReason() {
        return reason;
    }

    public String getReportID() {
        return reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public String getReportedObjectName() {
        return reportedObjectName;
    }

    public String getReportedObjectDescription() {
        return reportedObjectDescription;
    }

    public void report(boolean doBan, boolean doRemove) {
        isDone = true;
    }

    public String toCSV() {
        String reason = this.reason
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        return this.reportType + "," +
                this.reportID + "," +
                this.reporter.getUsername() + "," +
                "null" + "," +
                reason + "," +
                isDone;
    }

    public boolean isDone() {
        return isDone;
    }

}
