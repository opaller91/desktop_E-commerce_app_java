package ku.cs.shop.models;

import ku.cs.shop.services.UserListDataSource;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserList{

    private ArrayList<User> userList;

    public UserList() {
        userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public ArrayList<User> getAllUsers() {
        return userList;
    }

    public User findUser(String username) {
        User user = null;
        for (User temp : userList) {
            if (username.equals(temp.getUsername())) {
                user = temp;
            }
        }
        return user;
    }

    public void setAllUserShop() {
        for (User temp : userList) {
            temp.setShop();
        }
    }

    public void setAllUserImage() {
        for (User temp : userList) {
            temp.setImage(null);
        }
    }

    public void removeUser(String username) {
        User user = findUser(username);
        userList.remove(user);
    }

    public void sortByLoginTime() {
        Collections.sort(userList);
    }

}
