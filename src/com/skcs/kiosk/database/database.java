package com.skcs.kiosk.database;

import com.skcs.kiosk.Parameter;
import com.skcs.kiosk.ScreenSetTerminal;
import com.sun.org.apache.xpath.internal.operations.String;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Application.launch;
import static javafx.scene.control.TextField.*;

/**
 * Created by akhirudin on 12/7/16.
 */
public class database {


    private static database instance = null;

    public static database getInstance() {
        if (instance == null) {
            instance = new database();
        }
        return instance;
    }

    public static Connection Connector() {  // --> Koneksi ke DB
        try {

             Class.forName("org.sqlite.JDBC");

          //  String url = "jdbc:sqlite:skcs.sqlite"; // mengunakan penambahan library

           // Connection conn = DriverManager.getConnection(url);

            Connection conn = DriverManager.getConnection("jdbc:sqlite:skcs.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            // TODO: handle exception
        }
    }

    public boolean isLogin (java.lang.String username, java.lang.String password) throws SQLException {     // untuk verivikasi login
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        java.lang.String query="select * from user where username = ? and password = ? "; // untuk memnaggil dari database
        try {
            preparedStatement = Connector().prepareStatement(query);
            preparedStatement.setString(1, username); //index didalam table DB
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
            // TODO: handle exception
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }


    public List<Parameter> getSelect() {  // --> Menampilkan record
        List<Parameter> result = new ArrayList<Parameter>();
        try {
            Statement st = Connector().createStatement();
            ResultSet r = st.executeQuery("SELECT * FROM table_parameter");
            while (r.next()) {
                Parameter p = new Parameter(
                        r.getString("Name"),
                        r.getString("Value")
                );
                result.add(p);
                System.out.println("Name = "+ p.getName() + ", Value = "+ p.getValue());
            }
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null,
                    ex);
            result = new ArrayList<Parameter>();
        }
        return result;
    }

   public void getInsert(Parameter s) {

           try {
               Statement st = Connector().createStatement();

               ResultSet rs;
               System.out.println("Opened database successfully");

               rs = st.executeQuery("SELECT * FROM table_parameter ORDER BY ID");

               while (rs.next()) {
                   java.lang.String Name = rs.getString("Name");
                   java.lang.String Value = rs.getString("Value");

                   if (Value == s.getValue() ) {
                       System.out.println("Data sudah dipakai !");
                   } else {
                       st.execute(" INSERT INTO table_parameter (Name, Value) VALUES('"+s.getName() +"','"+s.getValue()+"')");
                   }

               }
           } catch (SQLException ex) {
               Logger.getLogger(database.class.getName()).log(Level.SEVERE, null,
                       ex);
           }
   }

    public void getUpdate(Parameter  s) {    // --> Melakukan Update
        try {
            Statement st = Connector().createStatement();

          /*  ResultSet r = st.executeQuery("SELECT * FROM table_parameter");
            while (r.next()) {
                Parameter p = new Parameter(
                        r.getString("ID"),
                        r.getString("Name"),
                        r.getString("Value")
                );
                System.out.println("Name = "+ p.getName() + ", Value = "+ p.getValue());
           String sql = " UPDATE table_parameter SET Value = '"+s.getValue()+"'"
                   +" WHERE Name = '"+s.getName()+"'";  */


            st.executeUpdate(" UPDATE table_parameter SET Value = '"+s.getValue()+"'"
                    + " WHERE Name ='"+s.getName()+"'");
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName() ).log(Level.SEVERE, null,
                    ex);
           // System.out.println("Data Berhasil diUpdate !");
        }
    }

    public void getFind(String name) {
        Parameter setting = null;
        try {
            Statement st = Connector().createStatement();
            ResultSet rs = st
                    .executeQuery("SELECT * FROM table_parameter WHERE name = " + name);
            while (rs.next()) {
                setting = new Parameter(rs.getString("name"),
                        rs.getString("value"));
            }
        } catch (Exception ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null,
                    ex);
            setting = null;
        }
    }
}
