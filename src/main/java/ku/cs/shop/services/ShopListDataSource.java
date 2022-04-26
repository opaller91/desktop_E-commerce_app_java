package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.ShopList;
import ku.cs.shop.models.User;
import ku.cs.shop.models.UserList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ShopListDataSource implements DataSource<ShopList>{

    private ShopList shopList;

    public ShopListDataSource() {
        shopList = new ShopList();
        ifPathNotExist();
        readData();
    }

    public ShopListDataSource(boolean doReadData) {
        shopList = new ShopList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    public void readData() {
        String filename = "assets" + File.separator + "shops.csv";
        String line;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();

            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                String shopName = data[0].trim();
                String shopAddress = data[1].trim();
                String shopEmail = data[2].trim();
                String shopPhoneNumber = data[3].trim();
                String shopFacebook = data[4].trim();
                String shopInstagram = data[5].trim();
                String shopLine = data[6].trim();
                String bankName = data[7].trim();
                String accountName = data[8].trim();
                String accountNumber = data[9].trim();
                String username = data[10].trim();

                Shop tempShop = new Shop(shopName, shopAddress, shopEmail, shopPhoneNumber,
                        shopFacebook, shopInstagram, shopLine, bankName, accountName, accountNumber, username);
                //tempShop.setImage(null);
                shopList.addShop(tempShop);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
    }

    public void saveData() {
        String line;
        String shopFilename = "assets" + File.separator + "shops.csv";
        ArrayList<Shop> ul = shopList.getAllShops();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(shopFilename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("shopName,shopAddress,shopEmail,shopPhoneNumber,shopFacebook,shopInstagram,shopLine,bankName,accountName,accountNumber,username");

            for (Shop shop : ul) {
                printWriter.println(shop.getShopName() + ',' +
                        shop.getShopAddress() + ',' +
                        shop.getShopEmail() + ',' +
                        shop.getShopPhoneNumber() + ',' +
                        shop.getShopFacebook() + ',' +
                        shop.getShopInstagram() + ',' +
                        shop.getShopLine() + ',' +
                        shop.getBankName() + ',' +
                        shop.getAccountName() + ',' +
                        shop.getAccountNumber() + ',' +
                        shop.getShopUsername());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + shopFilename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
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
                            "shop" + File.separator +
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

    public ShopList getShopList() {
        return shopList;
    }

    public void setShopList(ShopList shopList) {
        this.shopList = shopList;
    }

    @Override
    public void ifPathNotExist() {
        String directoryName = "assets";
        String filename = "shops.csv";
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
