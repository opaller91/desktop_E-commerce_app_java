package ku.cs.shop.services;

import javafx.scene.image.Image;
import ku.cs.shop.models.User;
import ku.cs.shop.models.UserList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class UserListDataSource implements DataSource<UserList>{

    private UserList userList;

    public UserListDataSource() {
        userList = new UserList();
        ifPathNotExist();
        readData();
    }

    public UserListDataSource(boolean doReadData) {
        userList = new UserList();
        ifPathNotExist();
        if (doReadData) {
            readData();
        }
    }

    public void readData(){
        String line;
        String filename = "assets" + File.separator + "users.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);

            buffer.readLine();

            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                String fullName = data[0].trim();
                String username = data[1].trim();
                String phoneNumber = data[2].trim();
                String email = data[3].trim();
                String password = data[4].trim();
                String shopName = data[5].trim();
                boolean isAdmin = Boolean.parseBoolean(data[6].trim());
                boolean isBanned = Boolean.parseBoolean(data[7].trim());
                User tempUser = new User(fullName, username, phoneNumber, email, password, shopName, isAdmin, isBanned);
                String time = getLastLoginTime(username);
                if (time.equals("")) {
                    tempUser.setOnlineTime();
                } else {
                    tempUser.setLastOnlineTimeStr(time);
                }

                tempUser.setLastLogin();
                userList.addUser(tempUser);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
    }

    public void saveData() {
        String userFilename = "assets" + File.separator + "users.csv";
        ArrayList<User> ul = userList.getAllUsers();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(userFilename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("fullName,username,phoneNumber,email,password,shopName,isAdmin,isBanned");

            for (User user : ul) {
                user.setShop();
                printWriter.println(user.getFullName() + ',' +
                        user.getUsername() + ',' +
                        user.getPhoneNumber() + ',' +
                        user.getEmail() + ',' +
                        user.getPassword() + ',' +
                        user.getShopName() + ',' +
                        user.isAdmin() + ',' +
                        user.isBanned());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + userFilename);
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
                            "avatar" + File.separator +
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

    @Override
    public void ifPathNotExist() {
        String directoryName = "assets";
        String filename = "users.csv";
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

    public UserList getUserList() {
        return userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public void logTime(User user) {
        String userActivityFilename = "assets" + File.separator + "userActivity.csv";
        ArrayList<User> ul = userList.getAllUsers();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(userActivityFilename, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(user.getUsername() + "," + user.getLastOnlineTimeStr());
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + userActivityFilename);
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

    public String getLastLoginTime(String username) {
        String line;
        String time = "";
        String filename = "assets" + File.separator + "userActivity.csv";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);
            buffer.readLine();
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                String tempUsername = data[0].trim();
                String formattedTime = data[1].trim();
                if (username.equals(tempUsername)) {
                    time = formattedTime;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
        return time;
    }
}
