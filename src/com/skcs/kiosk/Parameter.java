package com.skcs.kiosk;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

/**
 * Created by akhirudin on 12/8/16.
 */
public class Parameter {
     String name;
     String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(String name, SingleSelectionModel selectionModel) {
        this.value = getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

