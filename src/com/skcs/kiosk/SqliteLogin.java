package com.skcs.kiosk;

import com.skcs.kiosk.database.database;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */

public class SqliteLogin implements Initializable, ControlledScreen {

    public SqliteModel loginModel = new SqliteModel();


    @FXML
    private Label textConect;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        if (loginModel.isDbConnected()) {
            textConect.setText("Connected to SQLite !"); // keterangan jika berhasil koneksi
        } else {

            textConect.setText("Not Connected !");
        }
    }

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    VBox vboxContainer;

    @FXML
    HBox h;

    @FXML
    Button btnLogin;

    @FXML
    Button btnView;

    /*   public SqliteLogin SqliteModel = new SqliteModel(); // MASIH ERROR KONEKSI KE SQLITE


        @FXML
        Label textConect;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            // TODO Auto-generated method stub
            if (SqliteModel.isDbConnected()) {
                textConect.setText("Berhasil Koneksi SqliteDB");
            } else {

                textConect.setText("Not Connected");
            }
        } */

/*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    } */

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (loginModel.isLogin(username.getText(), password.getText())) {
                        System.out.println("Anda Berhasil Login");
                        myController.getScreen(Kiosk.scrSetTerminal);
                        myController.loadScreen(Kiosk.scrSetTerminal, Kiosk.scrSetTerminalFile);
                        myController.setScreen(Kiosk.scrSetTerminal);
                        textConect.setText("Username and Password Correct !");
                    } else {
                        textConect.setText("Username and Password Not Correct !");

                        Alert fail = new Alert(Alert.AlertType.ERROR);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Username dan Password tidak cocok !");
                        fail.showAndWait();
                    }

                } catch (SQLException e) {
                  //  textConect.setText("Username and Password Not Correct !");
                    e.printStackTrace();
                }
                myController.setScreen(Kiosk.sqliteLogin);
            }
        });

        btnView.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                database db = database.getInstance();
                db.getSelect();
            }
        });

    }

/*    public void setBtnlogin (ActionEvent actionEvent) {
        try {
            if (loginModel.isLogin(username.getText(), password.getText())) {
                textConect.setText("Username and Password Correct !");
            } else {
                textConect.setText("Username and Password Not Correct !");
            }

        } catch (SQLException e) {
            textConect.setText("Username and Password Not Correct !");
            e.printStackTrace();
        }
    } */
}
