package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenSetting implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    ScrollPane sasd;

    @FXML
    HBox logo;

    @FXML
    HBox vboxLangTitle;

    @FXML
    ImageView imgViewLogo;

    @FXML
    ImageView imgNext;

    @FXML
    ImageView imgNext1;

    @FXML
    ImageView imgNext2;

    @FXML
    VBox vboxContainer;

    @FXML
    VBox setting;

    @FXML
    Label lblScreenTitle;

    @FXML
    Label labelSetting;

    @FXML
    Label labelUtility;

    @FXML
    Label labelUpload;

    @FXML
    Label labelUser;

    @FXML
    Label labelUnlock;

    @FXML
    Label lblScreenTitleTop;

    @FXML
    VBox vboxcontainer;

    @FXML
    HBox hboxlabel;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXButton btnNext2;

    @FXML
    private JFXButton btnNext3;

    @FXML
    private JFXButton btnNext4;

    @FXML
    private JFXButton btnNext5;

    @FXML
    private JFXButton btnChange;

    @FXML
    private JFXButton btnOut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo= getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        SVGGlyph iconBtnNext = Resource.getGlyph("ion-ios-arrow-right");
        iconBtnNext.setFill(MyUI.grayColor);
        iconBtnNext.setSize(12, 20);
        //iconBtnNext.setRotate(180);
        btnNext.setAlignment(Pos.CENTER);
        btnNext.setContentDisplay(ContentDisplay.TOP);
        btnNext.setGraphicTextGap(8);
        btnNext.setGraphic(iconBtnNext);
        //btnNext.setText(Resource.getLabel("menu_balance_inquiry"));

        SVGGlyph iconBtnNext2 = Resource.getGlyph("ion-ios-arrow-right");
        iconBtnNext2.setFill(MyUI.grayColor);
        iconBtnNext2.setSize(12, 20);
        //iconBtnNext2.setRotate(180);
        btnNext2.setAlignment(Pos.CENTER);
        btnNext2.setContentDisplay(ContentDisplay.TOP);
        btnNext2.setGraphicTextGap(8);
        btnNext2.setGraphic(iconBtnNext2);

        SVGGlyph iconBtnNext3 = Resource.getGlyph("ion-ios-arrow-right");
        iconBtnNext3.setFill(MyUI.grayColor);
        iconBtnNext3.setSize(12, 20);
        //iconBtnNext.setRotate(180);
        btnNext3.setAlignment(Pos.CENTER);
        btnNext3.setContentDisplay(ContentDisplay.TOP);
        btnNext3.setGraphicTextGap(8);
        btnNext3.setGraphic(iconBtnNext3);

        SVGGlyph iconBtnNext4 = Resource.getGlyph("ion-ios-arrow-right");
        iconBtnNext4.setFill(MyUI.grayColor);
        iconBtnNext4.setSize(12, 20);
        //iconBtnNext.setRotate(180);
        btnNext4.setAlignment(Pos.CENTER);
        btnNext4.setContentDisplay(ContentDisplay.TOP);
        btnNext4.setGraphicTextGap(8);
        btnNext4.setGraphic(iconBtnNext4);

        SVGGlyph iconBtnNext5 = Resource.getGlyph("ion-ios-arrow-right");
        iconBtnNext5.setFill(MyUI.grayColor);
        iconBtnNext5.setSize(12, 20);
        //iconBtnNext.setRotate(180);
        btnNext5.setAlignment(Pos.CENTER);
        btnNext5.setContentDisplay(ContentDisplay.TOP);
        btnNext5.setGraphicTextGap(8);
        btnNext5.setGraphic(iconBtnNext5);

      labelSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Goto Screen Setting");

                if (!"".equals(myController.getScreen(Kiosk.scrSetTerminal))) {
                    myController.loadScreen(Kiosk.scrSetTerminal, Kiosk.scrSetTerminalFile);
                }
                myController.setScreen(Kiosk.scrSetTerminal);
            }
        });

        labelUtility.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Goto Screen Test Utility");

                if (!"".equals(myController.getScreen(Kiosk.scrTestDriver))) {
                    myController.loadScreen(Kiosk.scrTestDriver, Kiosk.scrTestDriverFile);
                }
                myController.setScreen(Kiosk.scrTestDriver);
            }
        });

        labelUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Go to Screen User Mangement");

                if (!"".equals(myController.getScreen(Kiosk.scrManagement))) {
                    myController.loadScreen(Kiosk.scrManagement, Kiosk.scrManagementFile);
                }
                myController.setScreen(Kiosk.scrManagement);
            }
        });

        labelUpload.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Goto Screen Upload");

                if (!"".equals(myController.getScreen(Kiosk.scrUpload))) {
                    myController.loadScreen(Kiosk.scrUpload, Kiosk.scrUploadFile);
                }
                myController.setScreen(Kiosk.scrUpload);
            }
        });

        labelUnlock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Goto Screen Unlock");

                if (!"".equals(myController.getScreen(Kiosk.scrUnlock))) {
                    myController.loadScreen(Kiosk.scrUnlock, Kiosk.scrUnlockFile);
                }
                myController.setScreen(Kiosk.scrUnlock);
            }
        });

        btnChange.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Go To Change Password");

                if (!"".equals(myController.getScreen(Kiosk.scrChangePwd))) {
                    myController.loadScreen(Kiosk.scrChangePwd, Kiosk.scrChangePwdFile);
                }
                myController.setScreen(Kiosk.scrChangePwd);
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
