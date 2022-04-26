package ku.cs.shop.models;

public class Review {

    private String reviewID;
    private String itemID;
    private String username;
    private int rating;
    private String comment;
    private boolean isBanned;

    public Review(String reviewID, String itemID, String username, int rating, String comment, boolean isBanned) {
        this.reviewID = reviewID;
        this.itemID = itemID;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.isBanned = isBanned;
    }

    public String getReviewID() {
        return reviewID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String toCSV() {
        return reviewID + "," +
                itemID + "," +
                username + "," +
                rating + "," +
                comment.replace("\n","\\[newline]")
                        .replace("\"","\\[doublequote]")
                        .replace(",","\\[comma]") + "," +
                isBanned;
    }
}
