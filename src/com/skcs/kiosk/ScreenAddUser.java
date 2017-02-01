package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.skcs.kiosk.database.database;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Logger;

import static java.sql.Connection.*;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenAddUser implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

    @FXML
    ScrollPane sasd;

    @FXML
    TextField textUsername;

    @FXML
    TextField textPassword;

    @FXML
    ComboBox comboRole;

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
                String ID       = UUID.randomUUID().toString().substring(0,8);
                String username = textUsername.getText();
                String password = textPassword.getText();
                String akses    = (String) comboRole.getValue();

                try {
                    if (textPassword.getText().trim().isEmpty() && textUsername.getText().trim().isEmpty() &&
                            comboRole.getSelectionModel().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Belum Diisi Semua !");
                        fail.showAndWait();
                    } else if (textUsername.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Username Belum Diisi !");
                        fail.showAndWait();
                    } else if (textPassword.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Password Belum Diisi !");
                        fail.showAndWait();
                    } else if (comboRole.getSelectionModel().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Role Acsess Belum Dipilih !");
                        fail.showAndWait();
                    } else {
                            try {
                                Statement st = connection.createStatement();
                                st.execute("INSERT INTO user (id,username,password,akses) VALUES('"+ID+"','"+username+ "','"+password+"','"+akses+"')");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        Alert fail = new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Sukses !");
                        fail.setContentText("Data Berhasil Disimpan !");
                        fail.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Data Sudah Tersimpan");
                     }
                        myController.getScreen(Kiosk.scrAdd);
                            myController.loadScreen(Kiosk.scrAdd, Kiosk.scrAddFile);
                        myController.setScreen(Kiosk.scrAdd);
                    }
        });

        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textUsername.clear();
                textPassword.clear();

                System.out.println("Go To Menu Setting");

                if (!"".equals(myController.getScreen(Kiosk.scrManagement))) {
                    myController.loadScreen(Kiosk.scrManagement, Kiosk.scrManagementFile);
                }
                myController.setScreen(Kiosk.scrManagement);
            }
        });
    }
}