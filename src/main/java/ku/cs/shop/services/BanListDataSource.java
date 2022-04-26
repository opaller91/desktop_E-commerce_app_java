package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.Ban;
import ku.cs.shop.models.BanList;
import ku.cs.shop.models.Review;

import java.awt.image.BufferedImage;
import java.io.*;

public class BanListDataSource implements DataSource<BanList>{

    private BanList banList;

    public BanListDataSource(boolean doReadData) {
        banList = new BanList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    public BanListDataSource(String type) {
        banList = new BanList();
        ifPathNotExist();
        readData(type);
    }

    public BanList getBanList() {
        return banList;
    }

    public void setBanList(BanList banList) {
        this.banList = banList;
    }

    @Override
    public void readData() {
        String line;
        String filename = "assets" + File.separator + "bans.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                // banID,type,objectID,reason,banTimeStr,isActive
                String[] data = line.split(",");
                String banID = data[0].trim();
                String type = data[1].trim();
                String objectID = data[2].trim();
                String reason = data[3].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                String banTimeStr = data[4].trim();
                boolean isActive = Boolean.parseBoolean(data[5].trim());
                Ban ban = new Ban(banID,type,objectID,reason,banTimeStr,isActive);
                banList.addBan(ban);
            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void readData(String t) {
        String line;
        String filename = "assets" + File.separator + "bans.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                // banID,type,objectID,reason,banTimeStr,isActive
                String[] data = line.split(",");
                String banID = data[0].trim();
                String type = data[1].trim();
                String objectID = data[2].trim();
                String reason = data[3].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                String banTimeStr = data[4].trim();
                boolean isActive = Boolean.parseBoolean(data[5].trim());
                if (t.equals(type)) {
                    Ban ban = new Ban(banID,type,objectID,reason,banTimeStr,isActive);
                    banList.addBan(ban);
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
        String filename = "assets" + File.separator + "bans.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("banID,type,objectID,reason,banTimeStr,isActive");
            printWriter.print(banList.toCsv());

        } catch (FileNotFoundException e) {
            System.err.println("Cannot save file " + filename);
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
        String filename = "bans.csv";
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
}
