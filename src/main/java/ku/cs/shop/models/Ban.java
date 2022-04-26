package ku.cs.shop.models;

public class Ban {
    private String banID;
    private String type;
    private String objectID; // type = user -> username ; type = item/review -> ids
    private String reason;
    private String banTimeStr;
    private boolean isActive;

    public Ban(String banID, String type, String objectID, String reason, String banTimeStr, boolean isActive) {
        this.banID = banID;
        this.type = type;
        this.objectID = objectID;
        this.reason = reason;
        this.banTimeStr = banTimeStr;
        this.isActive = isActive;
    }


    public String getBanID() {
        return banID;
    }

    public String getType() {
        return type;
    }

    public String getObjectID() {
        return objectID;
    }

    public String getReason() {
        return reason;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getBanTimeStr() {
        return banTimeStr;
    }

    public String toCSV() {
        String r = reason.replace("\n","\\[newline]")
                .replace("\"","\\[doublequote]")
                .replace(",","\\[comma]");
        return  banID + "," +
                type + "," +
                objectID + "," +
                r + "," +
                banTimeStr + "," +
                isActive;
    }
}
