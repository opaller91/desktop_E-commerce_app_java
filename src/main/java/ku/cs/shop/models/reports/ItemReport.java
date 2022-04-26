package ku.cs.shop.models.reports;

import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.models.User;
import ku.cs.shop.models.UserList;
import ku.cs.shop.services.ItemListDataSource;
import ku.cs.shop.services.UserListDataSource;

public class ItemReport extends Report{

    protected Item reportedItem;

    public ItemReport(String reportID, User reporter, String reason, boolean isDone, Item reportedItem) {
        super(reportID, reporter, reason, isDone);
        this.reportedItem = reportedItem;
        this.reportedUsername = reportedItem.getShop().getShopUsername();
        this.reportedObjectName = reportedItem.getItemName();
        this.reportedObjectDescription = reportedItem.getItemDescription();
        this.reportType = "ItemReport";
    }

    public Item getReportedItem() {
        return reportedItem;
    }

    @Override
    public void report(boolean doBan, boolean doRemove) {
        if (doRemove) {
            // remove item
            reportedItem.setBanned(true);
            ItemListDataSource ilData = new ItemListDataSource();
            ItemList il = ilData.getItemList();
            il.removeItem(reportedItem.getItemID());
            il.addItem(reportedItem);
            ilData.setItemList(il);
            ilData.saveData();
        }
        if (doBan) {
            // ban item owner
            User user = reportedItem.getShop().getShopUser();
            user.setBanned(true);
            UserListDataSource ulData = new UserListDataSource();
            UserList ul = ulData.getUserList();
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
                this.reportedItem.getItemID() + "," +
                reason + "," +
                isDone;
    }
}
