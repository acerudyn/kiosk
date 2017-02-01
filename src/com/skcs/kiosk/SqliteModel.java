package com.skcs.kiosk;

import com.skcs.kiosk.database.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteModel {
    Connection conection;

    public SqliteModel () {
        conection = SqliteConect.Connector();
        if (conection == null) {

            System.out.println("Connection Not Successful !"); // keterangan jika tidak konek
            System.exit(1);}
    }

    public boolean isDbConnected() {
        try {
            return !conection.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin (String user, String pass) throws SQLException {
        ResultSet resultSet = null;

        String query="select * from table_parameter where Name = ? and Value = ? "; // untuk memnaggil dari database
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conection.prepareStatement(query);
            preparedStatement.setString(1, user); //index didalam table DB
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
            // TODO: handle exception
        }
    }
}
