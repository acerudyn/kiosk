package com.skcs.kiosk;
import java.sql.*;

public class SqliteConect {
    public static Connection Connector() {
        try {
            // Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:skcs.sqlite"; // mengunakan penambahan library

            Connection conn = DriverManager.getConnection(url);

            // Connection conn = DriverManager.getConnection("jdbc:sqlite:skcs.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            // TODO: handle exception
        }
    }
}
