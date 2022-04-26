package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class OrderListDataSource implements DataSource<Order>{

    OrderList orders;

    public OrderListDataSource() {
        orders = new OrderList();
        ifPathNotExist();
        readData(); //อ่านข้อมูลสินค้า
    }

    public OrderListDataSource(String shopName) {
        orders = new OrderList();
        ifPathNotExist();
        readData(shopName); //อ่านข้อมูลสินค้า
    }

    public OrderListDataSource(boolean doReadData) {
        orders = new OrderList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    public void readData(){
        String line;
        String filename = "assets" + File.separator + "orders.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String orderID = data[0].trim();
                int amountOfItems = Integer.parseInt(data[1].trim());
                String address = data[2].trim();
                String username = data[3].trim();
                String fullname = data[4].trim();
                String itemID = data[5].trim();
                String shopName = data[6].trim();
                boolean isShipped = Boolean.parseBoolean(data[7].trim());

                Order order = new Order(orderID,amountOfItems,address,username,fullname,itemID,shopName,isShipped);
                orders.addOrder(order);

            }
        }catch (FileNotFoundException e){
            System.err.println("Cannot read file " + filename);
        }catch (IOException e){
            System.err.println("Error reading from file");
        }
    }

    public void readData(String name) {
        String line;
        String filename = "assets" + File.separator + "orders.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String orderID = data[0].trim();
                int amountOfItems = Integer.parseInt(data[1].trim());
                String address = data[2].trim();
                String username = data[3].trim();
                String fullname = data[4].trim();
                String itemID = data[5].trim();
                String shopName = data[6].trim();
                boolean isShipped = Boolean.parseBoolean(data[7].trim());

                if (shopName.equals(name)) {
                    Order order = new Order(orderID,amountOfItems,address,username,fullname,itemID,shopName,isShipped);
                    orders.addOrder(order);
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
        String orderFilename = "assets" + File.separator + "orders.csv";
        ArrayList<Order> ol = orders.getAllOrders();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(orderFilename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("orderID,amountOfItems,address,username,fullname,itemID,shopName,isShipped");

            for (Order order : ol) {
                printWriter.println(order.getOrderID() + ',' +
                        order.getAmountOfItems() + ',' +
                        order.getAddress() + ',' +
                        order.getUsername() + ',' +
                        order.getFullname() + ',' +
                        order.getItemID() + ',' +
                        order.getShopName() + ',' +
                        order.isShipped());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Cannot save file " + orderFilename);
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
        String filename = "orders.csv";
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

    public OrderList getOrders() {
        return orders;
    }

    public void setOrders(OrderList orders) {
        this.orders = orders;
    }
}
