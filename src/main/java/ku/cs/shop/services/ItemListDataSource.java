package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.models.ShopList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ItemListDataSource implements DataSource<ItemList> {

    private ItemList itemList;

    public ItemListDataSource() {
        itemList = new ItemList();
        ifPathNotExist();
        readData(); //อ่านข้อมูลสินค้า
    }

    public ItemListDataSource(String shopName) {
        itemList = new ItemList();
        ifPathNotExist();
        readData(shopName); //อ่านข้อมูลสินค้า
    }

    public ItemListDataSource(boolean doReadData) {
        itemList = new ItemList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    //----------------ส่วนใส่ข้อมูลสินค้า------------------
    public void readData(){
        String line;
        String filename = "assets" + File.separator + "items.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String itemID = data[0].trim();
                String shopName = data[1].trim();
                String itemName = data[2].trim();
                String itemDescription = data[3].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                String itemType = data[4].trim();
                double itemPrice = Double.parseDouble(data[5].trim());
                int itemStock = Integer.parseInt(data[6].trim());
                int stockWarning = Integer.parseInt(data[7].trim());
                boolean isBanned = Boolean.parseBoolean(data[8].trim());

                Item tempItem = new Item (itemID,shopName,itemName,itemDescription, itemPrice, itemStock,stockWarning,itemType,isBanned);
                ShopListDataSource slData = new ShopListDataSource();
                ShopList sl = slData.getShopList();
                tempItem.setShop(sl.findShop(shopName));
                itemList.addItem(tempItem);

            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void readData(String shopName){
        String line;
        String filename = "assets" + File.separator + "items.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String itemID = data[0].trim();
                String shopFileName = data[1].trim();
                String itemName = data[2].trim();
                String itemDescription = data[3].trim()
                        .replace("\\[newline]","\n")
                        .replace("\\[doublequote]","\"")
                        .replace("\\[comma]",",");
                String itemType = data[4].trim();
                double itemPrice = Double.parseDouble(data[5].trim());
                int itemStock = Integer.parseInt(data[6].trim());
                int stockWarning = Integer.parseInt(data[7].trim());
                boolean isBanned = Boolean.parseBoolean(data[8].trim());
                if (shopName.equals(shopFileName)) {
                    Item tempItem = new Item (itemID,shopFileName,itemName,itemDescription, itemPrice, itemStock,stockWarning,itemType,isBanned);
                    ShopListDataSource slData = new ShopListDataSource();
                    ShopList sl = slData.getShopList();
                    tempItem.setShop(sl.findShop(shopFileName));
                    tempItem.setImage(null);
                    itemList.addItem(tempItem);
                }
            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void saveData() {
        String filename = "assets" + File.separator + "items.csv";
        ArrayList<Item> il = itemList.getAllItems();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("itemID,shopName,itemName,itemDescription,itemType,itemPrice,itemStock,StockWarning,isBanned");

            for (Item item : il) {
                String itemDescription = item.getItemDescription()
                        .replace("\n","\\[newline]")
                        .replace("\"","\\[doublequote]")
                        .replace(",","\\[comma]");
                printWriter.println(item.getItemID() + ',' +
                        item.getShopName() + ',' +
                        item.getItemName() + ',' +
                        itemDescription + ',' +
                        item.getItemType() + ',' +
                        item.getItemPrice() + ',' +
                        item.getItemStock() + ',' +
                        item.getStockWarning() + ',' +
                        item.isBanned());
            }
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

    public Image getImage(String filepath) {
        File file = new File(filepath);
        return new Image(file.toURI().toString());
    }

    public void setImageFile(BufferedImage bi, String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (bi == null) { // if image is not given
            System.err.println("Image not given, finding one");
            try { // find image
                bi = ImageIO.read(file); // tries to get the image from filepath
                // if filepath doesn't have image, go to IOException
            } catch (IOException ex) {
                System.err.println(filepath + " has no image, creating one");
                try {
                    file.createNewFile();
                    bi = ImageIO.read(new File("assets" + File.separator +
                            "images" + File.separator +
                            "item" + File.separator +
                            "default.png"));
                    ImageIO.write(bi, "PNG", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                ImageIO.write(bi, "PNG", file); // if image is given, use image
            } catch (IllegalArgumentException e) {
                file.delete();
                System.err.println("Cannot save picture");
            } catch (IOException e) {
                System.err.println("Can't write image");
            }
        }
    }

    public void setImageFile(BufferedImage bi, String directory, String filename) {
        setImageFile(bi,directory + File.separator + filename);
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    @Override
    public void ifPathNotExist() {
        String directoryName = "assets";
        String filename = "items.csv";
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
