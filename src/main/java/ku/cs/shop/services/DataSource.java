package ku.cs.shop.services;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public interface DataSource <T>{

    void readData();
    void saveData();
    void ifPathNotExist();
}
