package ku.cs.shop.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class LoginSliderShowController {
    @FXML private StackPane stackPane;

    @FXML private AnchorPane backPane;

    @FXML private AnchorPane frontPane;

    private final ObservableList<Image> backImages= FXCollections.observableArrayList();
    private final ObservableList<Image> frontImages=FXCollections.observableArrayList();
    private static FadeTransition transition;
    private int backIndex=0;
    private int frontIndex=0;


    public void initialize(){
        Image i1=new Image(Objects.requireNonNull(getClass().getResource("/userimages/1.jpg")).toExternalForm());
        Image i2=new Image(Objects.requireNonNull(getClass().getResource("/userimages/2.jpg")).toExternalForm());
        Image i3=new Image(Objects.requireNonNull(getClass().getResource("/userimages/3.jpg")).toExternalForm());
        Image i4=new Image(Objects.requireNonNull(getClass().getResource("/userimages/4.jpg")).toExternalForm());
        Image i5=new Image(Objects.requireNonNull(getClass().getResource("/userimages/5.jpg")).toExternalForm());
        Image i6=new Image(Objects.requireNonNull(getClass().getResource("/userimages/6.jpg")).toExternalForm());


        setImages(i1,i6,i3,i2,i5,i4);
        initSliderShow(2,2);
        frontPane.toFront();
        frontPane.setOpacity(0);
        backPane.toBack();
        setAnchor(stackPane,0.0,0.0,0.0,0.0);

    }

    public static void setAnchor(Node node, Double left, Double top, Double right, Double bottom){
        AnchorPane.setLeftAnchor(node,left);
        AnchorPane.setTopAnchor(node,top);
        AnchorPane.setRightAnchor(node,right);
        AnchorPane.setBottomAnchor(node, bottom);
    }

    public void setImages(Image... images){
        if (images.length<3){
            try {
                throw new Exception("The image quantity must be 3 or more!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            for (int i=0;i<images.length;i++){
                if (i%2==0){
                    frontImages.add(images[i]);
                }else {
                    backImages.add(images[i]);
                }
            }
        }
        setBackgroundImage(backPane,backImages.get(0));
        setBackgroundImage(frontPane,frontImages.get(0));

    }

    public void initSliderShow(int animationDelay, int visibilityDelay){
        Runnable rn=()->{
            Platform.runLater(()->{
                frontPane.opacityProperty().addListener((observable, oldValue, newValue) -> {
                    PauseTransition pt;
                    if (newValue.doubleValue()==0){
                        frontIndex++;
                        if (frontIndex==frontImages.size()){
                            frontIndex=0;
                        }
                        setBackgroundImage(frontPane,frontImages.get(frontIndex));
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }else if (newValue.doubleValue()==1){
                        backIndex++;
                        if (backIndex==backImages.size()){
                            backIndex=0;
                        }
                        setBackgroundImage(backPane,backImages.get(backIndex));
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }
                });
            });

            transition=new FadeTransition(Duration.seconds(animationDelay),frontPane);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.setAutoReverse(true);
            transition.setCycleCount(-1);
            transition.play();

        };
        Thread t=new Thread(rn);
        t.start();
    }

    public synchronized void stop(){
        if (transition!=null){
            transition.stop();
        }
    }

    private void setBackgroundImage(AnchorPane target, Image image){
        BackgroundSize backgroundSize=new BackgroundSize(100, 100,true, true,true, true);
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,backgroundSize);
        Background background=new Background(backgroundImage);
        target.setBackground(background);
    }

}
