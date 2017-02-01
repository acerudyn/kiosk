package com.skcs.kiosk;

import com.res.Resource;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenIdle implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    AnchorPane root;

    @FXML
    VBox vboxContainer;

    @FXML
    HBox h;

    @FXML
    private ImageView imgViewWelcome;

    @FXML
    private Label lblDispTime;

    @FXML
    private Label lblDispDate;

    private Timeline tmlDisplayTime;
    SimpleDateFormat sdfTim = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdfDat = new SimpleDateFormat("dd MMMM yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgWelcome = getClass().getResource("/resources/img/welcome.jpg").toExternalForm();
        Image imgWelome = new Image(urlImgWelcome);
        imgViewWelcome.setImage(imgWelome);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        imgViewWelcome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                if (!"".equals(myController.getScreen(Kiosk.scrLogin))) {
                    myController.loadScreen(Kiosk.scrLogin, Kiosk.scrLoginFile);
                }
                myController.setScreen(Kiosk.scrLogin);
            }
        });

        tmlDisplayTime = new Timeline(
            new KeyFrame(Duration.seconds(1), actionEvent -> {
                Date now = new Date();
                String dspTime = sdfTim.format(now);
                String dspDate = sdfDat.format(now);
                lblDispTime.setText(dspTime);
                lblDispDate.setText(dspDate);
                //System.out.println(dspTime);
            }
        ));
        tmlDisplayTime.setCycleCount(Timeline.INDEFINITE);
        tmlDisplayTime.play();
    }
}
