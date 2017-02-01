package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.skcs.kiosk.database.database;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenChangePwd implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

    @FXML
    ScrollPane sasd;

    @FXML
    TextField textOld;

    @FXML
    TextField textNew;

    @FXML
    TextField textConfirm;

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
    Label label1;

    @FXML
    Label label2;

    @FXML
    Label labelShow;

    @FXML
    Label lblScreenTitleTop;

    @FXML
    VBox vboxcontainer;

    @FXML
    HBox hboxlabel;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnNext3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo = getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Connection connection = database.Connector();
                String oldPwd   = textOld.getText();
                String password = textNew.getText();
                String confirm  = textConfirm.getText();

                if (oldPwd.trim().isEmpty() && password.trim().isEmpty() && confirm.trim().isEmpty()) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Gagal !");
                    fail.setContentText("Belum Diisi Semua !");
                    fail.showAndWait();
                } else if (oldPwd.trim().isEmpty()) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Gagal !");
                    fail.setContentText("Password Lama belum diisi !");
                    fail.showAndWait();
                } else if (password.trim().isEmpty()) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Gagal !");
                    fail.setContentText("Password Baru belum diisi !");
                    fail.showAndWait();
                } else if (confirm.trim().isEmpty()) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Gagal !");
                    fail.setContentText("Konfirmasi Password belum diisi !");
                    fail.showAndWait();
                } else if (password.equals(confirm) == false) {             // validasi string not same
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Gagal !");
                    fail.setContentText("Konfirmasi Password tidak sama");
                    fail.showAndWait();
                    textConfirm.clear();
                } else {
                    try {
                        Statement st = connection.createStatement();
                        st.execute("UPDATE user SET password = '"+password+"' WHERE password = '"+oldPwd+"' ");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Go To Menu Setting");
                    }
                    Alert fail = new Alert(Alert.AlertType.INFORMATION);
                    fail.setHeaderText("Sukses !");
                    fail.setContentText("Password Berhasil Diupdate !");
                    fail.showAndWait();
                    myController.getScreen(Kiosk.scrSet);
                    myController.loadScreen(Kiosk.scrSet, Kiosk.scrSetFile);
                    myController.setScreen(Kiosk.scrSet);
                }
            }
        });

        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textOld.clear();
                textNew.clear();
                textConfirm.clear();

                System.out.println("Go To Menu Setting");

                if (!"".equals(myController.getScreen(Kiosk.scrSet))) {
                    myController.loadScreen(Kiosk.scrSet, Kiosk.scrSetFile);
                }
                myController.setScreen(Kiosk.scrSet);
            }
        });


    }
}