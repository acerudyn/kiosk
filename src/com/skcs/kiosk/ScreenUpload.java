package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
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
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenUpload implements Initializable, ControlledScreen {

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
    Label labelDate;

    @FXML
    Label lblScreenTitleTop;

    @FXML
    VBox vboxcontainer;

    @FXML
    HBox hboxlabel;

    @FXML
    private JFXButton btnOut;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnPinpad;

    @FXML
    private JFXButton btnPrinter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo = getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        SVGGlyph iconBtnPinpad = Resource.getGlyph("ion-ios-calculator-outline");
        iconBtnPinpad.setFill(MyUI.whiteColor);
        iconBtnPinpad.setSize(30, 35);
        //iconBtnNext.setRotate(180);
        btnPinpad.setAlignment(Pos.CENTER);
        btnPinpad.setContentDisplay(ContentDisplay.TOP);
        btnPinpad.setGraphicTextGap(8);
        btnPinpad.setGraphic(iconBtnPinpad);
        //btnNext.setText(Resource.getLabel("menu_balance_inquiry")); */

        SVGGlyph iconBtnPrinter = Resource.getGlyph("ion-ios-printer-outline");
        iconBtnPrinter.setFill(MyUI.whiteColor);
        iconBtnPrinter.setSize(30, 35);
        iconBtnPrinter.setRotate(180);
        btnPrinter.setAlignment(Pos.CENTER);
        btnPrinter.setContentDisplay(ContentDisplay.TOP);
        btnPrinter.setGraphicTextGap(8);
        btnPrinter.setGraphic(iconBtnPrinter);

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
    }
}