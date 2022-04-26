package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ReviewListDataSource implements DataSource<ReviewList> {

    private ReviewList reviewList;

    public ReviewListDataSource(boolean doReadData) {
        reviewList = new ReviewList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    public ReviewListDataSource(String itemID) {
        reviewList = new ReviewList();
        ifPathNotExist();
        readData(itemID, "item");
    }

    @Override
    public void readData() { // read all reviews
        String line;
        String filename = "assets" + File.separator + "reviews.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String reviewID = data[0].trim();
                String itemID = data[1].trim();
                String username = data[2].trim();
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                boolean isBanned = Boolean.parseBoolean(data[5].trim());
                Review review = new Review(reviewID,itemID,username,rating,comment,isBanned);
                reviewList.addReview(review);
            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void readData(String str, String type) { // read a specific type
        String line;
        String filename = "assets" + File.separator + "reviews.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String reviewID = data[0].trim();
                String itemID = data[1].trim();
                String username = data[2].trim();
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                boolean isBanned = Boolean.parseBoolean(data[5].trim());
                if (type.equals("item") && str.equals(itemID)) {
                    Review review = new Review(reviewID,itemID,username,rating,comment,isBanned);
                    reviewList.addReview(review);
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
        String reviewFilename = "assets" + File.separator + "reviews.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(reviewFilename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("reviewID,itemID,username,rating,comment,isBanned");
            printWriter.print(reviewList.toCsv());

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
        String filename = "reviews.csv";
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

    public ReviewList getReviewList() {
        return reviewList;
    }

    public void setReviewList(ReviewList reviewList) {
        this.reviewList = reviewList;
    }
}
