package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.Item;
import ku.cs.shop.models.ItemList;
import ku.cs.shop.models.ShopList;
import ku.cs.shop.models.vouchers.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class VoucherListDataSource implements DataSource<VoucherList> {

    private VoucherList voucherList;

    public VoucherListDataSource() {
        voucherList = new VoucherList();
        ifPathNotExist();
        readData(); //อ่านข้อมูลสินค้า
    }

    public VoucherListDataSource(String shopName, boolean includeNonShop) {
        voucherList = new VoucherList();
        ifPathNotExist();
        readData(shopName, includeNonShop); //อ่านข้อมูลสินค้า
    }

    public VoucherListDataSource(boolean doReadData) {
        voucherList = new VoucherList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    @Override
    public void readData() {
        String line;
        String filename = "assets" + File.separator + "vouchers.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String voucherType = data[0].trim();

                if (voucherType.equals("Voucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    Voucher voucher = new Voucher(codename,percent,description,expiryDateStr);
                    voucherList.addVoucher(voucher);
                } else if (voucherType.equals("AmountVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    int amountOfItem = Integer.parseInt(data[5].trim());
                    AmountVoucher voucher = new AmountVoucher(codename,percent,description,expiryDateStr,amountOfItem);
                    voucherList.addVoucher(voucher);
                } else if (voucherType.equals("PriceVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    double price = Double.parseDouble(data[5].trim());
                    PriceVoucher voucher = new PriceVoucher(codename,percent,description,expiryDateStr,price);
                    voucherList.addVoucher(voucher);
                } else if (voucherType.equals("ShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopName = data[5].trim();
                    ShopVoucher voucher = new ShopVoucher(codename,percent,description,expiryDateStr,shopName);
                    voucherList.addVoucher(voucher);
                } else if (voucherType.equals("AmountShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopName = data[5].trim();
                    int amountOfItem = Integer.parseInt(data[6].trim());
                    ShopAmountVoucher voucher = new ShopAmountVoucher(codename,percent,description,expiryDateStr,shopName,amountOfItem);
                    voucherList.addVoucher(voucher);
                } else if (voucherType.equals("PriceShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopName = data[5].trim();
                    double price = Double.parseDouble(data[6].trim());
                    ShopPriceVoucher voucher = new ShopPriceVoucher(codename,percent,description,expiryDateStr,shopName,price);
                    voucherList.addVoucher(voucher);
                }
            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void readData(String shopName, boolean includeNonShop) {
        String line;
        String filename = "assets" + File.separator + "vouchers.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String voucherType = data[0].trim();

                if (voucherType.equals("Voucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                    .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    if (includeNonShop) {
                        Voucher voucher = new Voucher(codename,percent,description,expiryDateStr);
                        voucherList.addVoucher(voucher);
                    }
                } else if (voucherType.equals("AmountVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    int amountOfItem = Integer.parseInt(data[5].trim());
                    if (includeNonShop) {
                        AmountVoucher voucher = new AmountVoucher(codename,percent,description,expiryDateStr,amountOfItem);
                        voucherList.addVoucher(voucher);
                    }
                } else if (voucherType.equals("PriceVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    double price = Double.parseDouble(data[5].trim());
                    if (includeNonShop) {
                        PriceVoucher voucher = new PriceVoucher(codename,percent,description,expiryDateStr,price);
                        voucherList.addVoucher(voucher);
                    }
                } else if (voucherType.equals("ShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopNameStr = data[5].trim();
                    if (shopNameStr.equals(shopName)) {
                        ShopVoucher voucher = new ShopVoucher(codename,percent,description,expiryDateStr,shopNameStr);
                        voucherList.addVoucher(voucher);
                    }
                } else if (voucherType.equals("AmountShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopNameStr = data[5].trim();
                    int amountOfItem = Integer.parseInt(data[6].trim());
                    if (shopNameStr.equals(shopName)) {
                        ShopAmountVoucher voucher = new ShopAmountVoucher(codename,percent,description,expiryDateStr,shopNameStr,amountOfItem);
                        voucherList.addVoucher(voucher);
                    }
                } else if (voucherType.equals("PriceShopVoucher")) {
                    String codename = data[1].trim();
                    double percent = Double.parseDouble(data[2].trim());
                    String description = data[3].trim()
                            .replace("\\[newline]","\n")
                            .replace("\\[doublequote]","\"")
                            .replace("\\[comma]",",");
                    String expiryDateStr = data[4].trim();
                    String shopNameStr = data[5].trim();
                    double price = Double.parseDouble(data[6].trim());
                    if (shopNameStr.equals(shopName)) {
                        ShopPriceVoucher voucher = new ShopPriceVoucher(codename,percent,description,expiryDateStr,shopNameStr,price);
                        voucherList.addVoucher(voucher);
                    }
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
        String filename = "assets" + File.separator + "vouchers.csv";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("voucherType,codename,percent,description,expiryDateStr,c1,c2");

            printWriter.print(voucherList.toCsv());

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

    public void setVoucherList(VoucherList voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public void ifPathNotExist() {
        String directoryName = "assets";
        String filename = "vouchers.csv";
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

    public VoucherList getVoucherList() {
        return voucherList;
    }
}
