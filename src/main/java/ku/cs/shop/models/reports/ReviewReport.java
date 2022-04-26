package ku.cs.shop.models.reports;

import ku.cs.shop.models.*;
import ku.cs.shop.services.ReviewListDataSource;
import ku.cs.shop.services.UserListDataSource;

public class ReviewReport extends Report{

    protected Review reportedReview;

    public ReviewReport(String reportID, User reporter, String reason, boolean isDone, Review reportedReview) {
        super(reportID, reporter, reason, isDone);
        this.reportedReview = reportedReview;
        this.reportedUsername = reportedReview.getUsername();
        this.reportedObjectName = reportedReview.getUsername();
        this.reportedObjectDescription = reportedReview.getComment();
        this.reportType = "ReviewReport";
    }

    public Review getReportedReview() {
        return reportedReview;
    }

    @Override
    public void report(boolean doBan, boolean doRemove) {
        if (doRemove) {
            // remove review
            reportedReview.setBanned(true);
            ReviewListDataSource rlData = new ReviewListDataSource(true);
            ReviewList rl = rlData.getReviewList();
            rl.removeReview(reportedReview.getReviewID());
            rl.addReview(reportedReview);
            rlData.setReviewList(rl);
            rlData.saveData();
        }
        if (doBan) {
            // ban review poster
            UserListDataSource ulData = new UserListDataSource();
            UserList ul = ulData.getUserList();
            User user = ul.findUser(reportedReview.getUsername());
            user.setBanned(true);
            ul.removeUser(user.getUsername());
            ul.addUser(user);
            ulData.setUserList(ul);
            ulData.saveData();
        }
        this.isDone = true;
    }

    @Override
    public String toCSV() {
        String reason = this.reason
                .replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        return this.reportType + "," +
                this.reportID + "," +
                this.reporter.getUsername() + "," +
                this.reportedReview.getReviewID() + "," +
                reason + "," +
                isDone;
    }
}
