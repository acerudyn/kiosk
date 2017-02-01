package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.skcs.kiosk.database.database;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.net.URL;
import java.util.ResourceBundle;

import static com.skcs.kiosk.database.database.*;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenLogin implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

    @FXML
    ScrollPane sasd;

    @FXML
    TextField txtUser;

    @FXML
    TextField txtPassword;

    @FXML
    HBox logo;

    @FXML
    HBox vboxLangTitle;

    @FXML
    ImageView imgViewLogo;


    @FXML
    VBox vboxContainer;

    @FXML
    VBox setting;

    @FXML
    Label lblScreenTitle;

    @FXML
    Label labelDate;

    @FXML
    Label lblScreenTitleTop;

    @FXML
    VBox vboxcontainer;

    @FXML
    HBox hboxlabel;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnBack;

    private Timeline tmlDisplayTime;
    SimpleDateFormat sdfDate = new SimpleDateFormat("E, dd MMM yyyy | hh:mm:ss a");

    public database loginModel = new database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo = getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Go To Screen Initialize");

                if (!"".equals(myController.getScreen(Kiosk.scrIdle))) {
                    myController.loadScreen(Kiosk.scrIdle, Kiosk.scrIdleFile);
                }
                myController.setScreen(Kiosk.scrIdle);
            }
        });

        btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (txtUser.getText().trim().isEmpty() && txtPassword.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Belum Diisi Semua !");
                        fail.showAndWait();
                    } else if (txtUser.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Username belum diisi !");
                        fail.showAndWait();
                    } else if (txtPassword.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Password belum diisi !");
                        fail.showAndWait();
                    } else if (loginModel.isLogin(txtUser.getText(), txtPassword.getText())) {
                        System.out.println("Anda Berhasil Login");
                        myController.getScreen(Kiosk.scrSet);
                        myController.loadScreen(Kiosk.scrSet, Kiosk.scrSetFile);
                        myController.setScreen(Kiosk.scrSet);
                    } else {
                        Alert fail = new Alert(Alert.AlertType.ERROR);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Username dan Password tidak cocok !");
                        fail.showAndWait();
                        txtPassword.clear();
                        txtUser.clear();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tmlDisplayTime = new Timeline(
                new KeyFrame(Duration.seconds(1), actionEvent -> {
                    Date now = new Date();
                    String dspDate = sdfDate.format(now);
                    labelDate.setText(dspDate);
                    //System.out.println(dspDate);
                }
                ));
        tmlDisplayTime.setCycleCount(Timeline.INDEFINITE);
        tmlDisplayTime.play();
    }
}