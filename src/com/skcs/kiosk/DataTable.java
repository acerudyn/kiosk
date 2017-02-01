package com.skcs.kiosk;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by akhirudin on 1/4/17.
 */
public class DataTable {
   // public SimpleStringProperty TEXTID = new SimpleStringProperty();
    public SimpleStringProperty ID = new SimpleStringProperty();
    public  SimpleStringProperty UserName = new SimpleStringProperty();
    public  SimpleStringProperty Password = new SimpleStringProperty();
    public  SimpleStringProperty Akses = new SimpleStringProperty();

   /* public String getTextID() {
        return TEXTID.get();
    }

    public void setTEXTID(String IDStr) { TEXTID.set(IDStr); } */

    public String getID() {
        return ID.get();
    }

    public void setID(String IDStr) {
        ID.set(IDStr);
    }

    public String getUserName() {
        return UserName.get();
    }

    public void setUserName(String UsernameStr) {
        UserName.set(UsernameStr);
    }

    public String getPassword() { return Password.get(); }

    public void setPassword(String PasswordStr) { Password.set(PasswordStr); }

    public String getAkses() {
        return Akses.get();
    }

    public void setAkses(String AksesStr) { Akses.set(AksesStr); }
}
