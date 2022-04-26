package ku.cs.shop.models;

import ku.cs.shop.models.Review;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReviewList {

    private ArrayList<Review> reviews;

    public ReviewList() {
        reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(String reviewID) {
        Review review = findReview(reviewID);
        reviews.remove(review);
    }

    public ArrayList<Review> getAllReviews() {
        return reviews;
    }

    public Review findReview(String reviewID) {
        Review review = null;
        for (Review temp : reviews) {
            if (reviewID.equals(temp.getReviewID())) {
                review = temp;
            }
        }
        return review;
    }

    public int findNextReviewID() {
        int i = 1;
        boolean idFound = false;
        if (reviews.size() == 0) return i;
        for (; i < reviews.size(); i++) { // loop ids
            idFound = false; // set to false
            for (Review temp : reviews) { // search all items
                if (Integer.parseInt(temp.getReviewID()) == i) { // if id exists
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
        String csv = "";
        for (Review review : reviews) {
            csv += review.toCSV() + "\n";
        }
        return csv;
    }

    public double averageRating() {
        int count = 0;
        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
            count++;
        }
        if (count == 0) {
            return 0;
        } else {
            return (double) totalRating/count;
        }
    }

    public void filterBannedReviews() {
        ArrayList<Review> temp = new ArrayList<>();
        for (Review review : reviews) {
            if (!review.isBanned()) {
                temp.add(review);
            }
        }
        reviews = temp;
    }
}
