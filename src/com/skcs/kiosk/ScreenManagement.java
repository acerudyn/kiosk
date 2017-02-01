package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import com.skcs.kiosk.database.database;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import javax.management.StringValueExp;
import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenManagement implements Initializable, ControlledScreen {

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

    @FXML
    ScrollPane sasd;

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
    Label lblScreenTitleTop;

    @FXML
    VBox vboxcontainer;

    @FXML
    HBox hboxlabel;

    @FXML
    HBox hboxCapture;

    @FXML
    TableView tableView;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnOut;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private TableView table;

    @FXML
    private TableColumn id;

    @FXML
    private TableColumn<DataTable, String> IdCol;

    @FXML
    private TableColumn username;

    @FXML
    private TableColumn pwd;

    @FXML
    private TableColumn akses;

    @FXML
    TextField textId;

    @FXML
    TextField textUsername;

    @FXML
    PasswordField textPassword;

    @FXML
    ComboBox comboAkses;

    private static Statement stat;

    private ObservableList <DataTable> tableData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo = getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);
        hboxCapture.setVisible(false);

        table.refresh();
        table.setItems(tableData);
        tableData = FXCollections.observableArrayList();    // build data table from database

        // Set Data For Build Table
        IdCol.setCellValueFactory(
                new PropertyValueFactory<DataTable, String>("ID")
        );
        /* id.setCellValueFactory(
                new PropertyValueFactory<DataTable, String>("ID")
        ); */
        username.setCellValueFactory(
                new PropertyValueFactory<DataTable, String>("UserName")
        );
        pwd.setCellValueFactory(
                new PropertyValueFactory<DataTable, String>("Password")
        );
        akses.setCellValueFactory(
                new PropertyValueFactory<DataTable, String>("Akses")
        );

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:skcs.sqlite");
            stat = con.createStatement();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user");
            while (rs.next()) {
                DataTable nt = new DataTable();
               // nt.textID.set(rs.getString("id"));
                nt.ID.set(rs.getString("id"));
                nt.UserName.set(rs.getString("username"));
                nt.Password.set(rs.getString("password"));
                nt.Akses.set(rs.getString("akses"));
                tableData.add(nt);
            }
            table.setItems(tableData);

        } catch (SQLException ex) {
            Logger.getLogger(ScreenManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void setScreenParent(ScreensController screenParent) {
        ScreensController myController = screenParent;
        table.refresh();
        table.setItems(tableData);

        SVGGlyph iconBtnAdd = Resource.getGlyph("ion-ios-plus-outline");
        iconBtnAdd.setFill(MyUI.whiteColor);
        iconBtnAdd.setSize(40, 40);
        btnAdd.setAlignment(Pos.BASELINE_CENTER);
        btnAdd.setContentDisplay(ContentDisplay.TOP);
        btnAdd.setGraphicTextGap(8);
        btnAdd.setGraphic(iconBtnAdd);

        SVGGlyph iconBtnEdit = Resource.getGlyph("ion-ios-compose-outline");
        iconBtnEdit.setFill(MyUI.whiteColor);
        iconBtnEdit.setSize(40, 40);
        iconBtnEdit.setRotate(270);
        btnEdit.setAlignment(Pos.BASELINE_CENTER);
        btnEdit.setContentDisplay(ContentDisplay.TOP);
        btnEdit.setGraphicTextGap(8);
        btnEdit.setGraphic(iconBtnEdit);

        SVGGlyph iconBtnDelete = Resource.getGlyph("ion-ios-close-outline");
        iconBtnDelete.setFill(MyUI.whiteColor);
        iconBtnDelete.setSize(40, 40);
        btnDelete.setAlignment(Pos.BASELINE_CENTER);
        btnDelete.setContentDisplay(ContentDisplay.TOP);
        btnDelete.setGraphicTextGap(8);
        btnDelete.setRotate(180);
        btnDelete.setGraphic(iconBtnDelete);

        SVGGlyph iconBtnView = Resource.getGlyph("ion-ios-search");
        iconBtnView.setFill(MyUI.whiteColor);
        iconBtnView.setSize(40, 40);
        iconBtnView.setRotate(90);
        btnView.setAlignment(Pos.BASELINE_CENTER);
        btnView.setContentDisplay(ContentDisplay.TOP);
        btnView.setGraphicTextGap(8);
        btnView.setGraphic(iconBtnView);

        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Go To Menu Setting");

                if (!"".equals(myController.getScreen(Kiosk.scrSet))) {
                    myController.loadScreen(Kiosk.scrSet, Kiosk.scrSetFile);
                }
                myController.setScreen(Kiosk.scrSet);
            }
        });

        btnOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("You Have Logged Out");

                if (!"".equals(myController.getScreen(Kiosk.scrLogin))) {
                    myController.loadScreen(Kiosk.scrLogin, Kiosk.scrLoginFile);
                }
                myController.setScreen(Kiosk.scrLogin);
            }
        });

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Go To Screen Add User");

                if (!"".equals(myController.getScreen(Kiosk.scrAdd))) {
                    myController.loadScreen(Kiosk.scrAdd, Kiosk.scrAddFile);
                }
                myController.setScreen(Kiosk.scrAdd);
            }
        });

        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int selectedIndex = table.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    try {
                        Connection connection = database.Connector();
                        TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
                        int row = pos.getRow();
                        Object item = table.getItems().get(row);    // Item here is the table view type
                        TableColumn col = pos.getTableColumn();
                        String data = (String) col.getCellObservableValue(item).getValue(); // this gives the value in the selected cell:
                        String ID   = data;
                        Statement st = connection.createStatement();
                        st.execute("DELETE FROM user WHERE id = '"+ID+"'");

                        Alert fail = new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Sukses !");
                        fail.setContentText("Data Berhasil Dihapus !");
                        table.refresh();
                        fail.showAndWait();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Data Sudah Terhapus");
                    }
                } else {
                    // Row Not selected.
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Selection");
                    alert.setHeaderText("No Row Selected");
                    alert.setContentText("Please select a Row in the table.");
                    alert.showAndWait();
                }
            }
        });

        btnView.setOnMouseClicked(new EventHandler<MouseEvent>() {      // Find data from table
            @Override
            public void handle(MouseEvent event) {

                // Search By Access
                Alert fail = new Alert(Alert.AlertType.CONFIRMATION);
                List<String> access = new ArrayList<String>();
                access.add("Admin");
                access.add("User");
                access.add("Owner");
                access.add("Technical");

                ChoiceDialog<String> dialog = new ChoiceDialog<String>("Admin", access);
                dialog.setTitle("Choice Dialog");
                dialog.setHeaderText("Search Data by Accsess");
                dialog.setContentText("Choose User Access : ");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String aksesdata   = result.get();

                    tableData = FXCollections.observableArrayList();

                    IdCol.setCellValueFactory(
                            new PropertyValueFactory<DataTable, String>("ID")
                    );
                    username.setCellValueFactory(
                            new PropertyValueFactory<DataTable, String>("UserName")
                    );
                    pwd.setCellValueFactory(
                            new PropertyValueFactory<DataTable, String>("Password")
                    );
                    akses.setCellValueFactory(
                            new PropertyValueFactory<DataTable, String>("Akses")
                    );

                    try {
                        Connection con = DriverManager.getConnection("jdbc:sqlite:skcs.sqlite");
                        stat = con.createStatement();
                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user WHERE akses = '"+aksesdata+"' ");
                        while (rs.next()) {
                            DataTable nt = new DataTable();
                            nt.ID.set(rs.getString("id"));
                            nt.UserName.set(rs.getString("username"));
                            nt.Password.set(rs.getString("password"));
                            nt.Akses.set(rs.getString("akses"));
                            tableData.add(nt);
                        }
                        table.refresh();
                        table.setItems(tableData);

                    } catch (SQLException ex) {
                        Logger.getLogger(ScreenManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                result.ifPresent(letter -> System.out.println("User Accsess : " + letter));
            }
        });

        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                int selectedIndex = table.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    hboxCapture.setVisible(true);
                    textId.setEditable(false);
                    final Label selected = new Label();
                    table.getSelectionModel().selectedItemProperty().addListener(
                            new ChangeListener<DataTable>() {
                                @Override
                                public void changed(
                                        ObservableValue<? extends DataTable> observable,
                                        DataTable oldValue,
                                        DataTable newValue
                                ) {
                                    if (newValue == null) {
                                        selected.setText("");
                                        return;
                                    }
                                    String data = newValue.getID(); // Get Data Table Value userID
                                    System.out.println("Data Terpilih : " + data);
                                    selected.setText("Selected Number: " + newValue.getID());
                                    
                                    // Get Data From Table Database
                                    try {
                                        Connection con = DriverManager.getConnection("jdbc:sqlite:skcs.sqlite");
                                        stat = con.createStatement();
                                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user WHERE id = '"+data+"' ");
                                        while (rs.next()) {
                                            textId.setText(rs.getString("id"));
                                            textUsername.setText(rs.getString("username"));
                                            textPassword.setText(rs.getString("password"));
                                        }
                                        table.refresh();
                                        table.setItems(tableData);

                                    } catch (SQLException ex) {
                                        Logger.getLogger(ScreenManagement.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                    );
                } else {
                    // Row Not selected.
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Selection");
                    alert.setHeaderText("No Row Selected");
                    alert.setContentText("Please select a Row in the table.");
                    alert.showAndWait();
                }
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hboxCapture.setVisible(false);
            }
        });

        btnUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Connection connection = database.Connector();
                String ID       = textId.getText();
                String username = textUsername.getText();
                String password = textPassword.getText();
                String akses    = (String) comboAkses.getValue();

                try {
                    if (textPassword.getText().trim().isEmpty() && textUsername.getText().trim().isEmpty() &&
                            comboAkses.getSelectionModel().isEmpty()) {
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
                    } else if (comboAkses.getSelectionModel().isEmpty()) {
                        Alert fail = new Alert(Alert.AlertType.WARNING);
                        fail.setHeaderText("Gagal !");
                        fail.setContentText("Role Acsess Belum Dipilih !");
                        fail.showAndWait();
                    } else {
                        try {
                            Statement st = connection.createStatement();
                            st.execute("UPDATE user SET username = '"+username+"', password = '"+password+"', akses = '"+akses+"' WHERE id = '"+ID+"' ");

                            table.refresh();
                            table.setItems(tableData);
                            hboxCapture.setVisible(false);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        Alert fail = new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Sukses !");
                        fail.setContentText("Data Berhasil Diupdate !");
                        fail.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Data Sudah Terupdate");
                }
            }

        });

       /* table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    int row = table.getSelectionModel().getSelectedIndex();
                    String tableid = table.getVisibleLeafColumns().toString();
                    System.out.println("User ID : " + tableid );
                } catch (Exception e) {

                }
            }
        }); */

        /* IdCol.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<DataTable, String>>() {
                    @Override public void handle(TableColumn.CellEditEvent<DataTable, String> t) {
                        ((DataTable)t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setID(t.getNewValue());
                        System.out.println("User Id Selected : " + t);
                    }
                });

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                // Item here is the table view type:
                Object item = table.getItems().get(row);

                TableColumn col = pos.getTableColumn();

                // this gives the value in the selected cell:
                String data = (String) col.getCellObservableValue(item).getValue();

                System.out.println("User Id Selected : " + data);
            }
        }); */

    }
}