package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import com.skcs.kiosk.database.database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.lang.reflect.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenSetTerminal implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

    @FXML
    ScrollPane sasd;

    @FXML
    TextField textTID;

    @FXML
    TextField textMID;

    @FXML
    TextField textName;

    @FXML
    TextField textAddress;

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
    private JFXButton btnCancel;

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

        label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Ke Menu Utama");

                if (!"".equals(myController.getScreen(Kiosk.scrMainMenu))) {
                    myController.loadScreen(Kiosk.scrMainMenu, Kiosk.scrMainMenuFile);
                }
                myController.setScreen(Kiosk.scrMainMenu);
            }
        });

     /* public class cekField () {
            try {
                Statement st = database.Connector().createStatement();
                ResultSet rs;
                System.out.println("Opened database successfully");

                rs = st.executeQuery("SELECT * FROM table_parameter ORDER BY ID");

                while ( rs.next()) {
                    String Name = rs.getString("Name");
                    String  Value = rs.getString("Value");
                }
                rs.close();
                st.close();
            } catch ( Exception e ) {
                System.out.println("Data Sudah Tersimpan");
                System.exit(0);
            }
        } */

        btnSave.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                Connection connection = database.Connector();
                String ID       = UUID.randomUUID().toString().substring(0,8);
                String TID      = textTID.getText();        // get text
                String MID      = textMID.getText();
                String name     = textName.getText();
                String address  = textAddress.getText();

                try {
                    if (textTID.getText().trim().isEmpty() && textMID.getText().trim().isEmpty() &&             // Validasi Textfield
                            textName.getText().trim().isEmpty() && textAddress.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Belum Diisi Semua !");
                        fail.showAndWait();
                    } else if (textTID.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("TID Belum Diisi !");
                        fail.showAndWait();
                    } else if (textMID.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("MID Belum Diisi !");
                        fail.showAndWait();
                    } else if (textName.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Name Belum Diisi !");
                        fail.showAndWait();
                    } else if (textAddress.getText().trim().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Address Belum Diisi !");
                        fail.showAndWait();
                    } else {
                            try {
                                Statement st = connection.createStatement();        // insert data to database
                                st.execute("INSERT INTO parameter (ID,TID,MID,name,address) " +
                                        "VALUES ('"+ID+"','"+TID+ "','"+MID+"','"+name+"','"+address+"')");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.out.println("Data Sudah Tersimpan");
                            }
                        Alert fail = new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Sukses !");
                        fail.setContentText("Data Berhasil Disimpan !");
                        fail.showAndWait();
                        textTID.clear();
                        textMID.clear();
                        textName.clear();
                        textAddress.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                System.out.println("Go To Menu Setting");
                myController.getScreen(Kiosk.scrSet);
                myController.loadScreen(Kiosk.scrSet, Kiosk.scrSetFile);
                myController.setScreen(Kiosk.scrSet);
            }
        });
    }
}