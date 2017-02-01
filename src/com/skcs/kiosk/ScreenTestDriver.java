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
public class ScreenTestDriver implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    AnchorPane anchor;

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
    Label labelCamera;

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
    private JFXButton btnBack;

    @FXML
    private JFXButton btnOut;

    @FXML
    private JFXButton btnCamera;

    @FXML
    private JFXButton btnPinpad;

    @FXML
    private JFXButton btnPrinter;

    @FXML
    private JFXButton btnSpeaker;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo= getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);

      /*  String urlImgNext= getClass().getResource("/resources/img/next.png").toExternalForm();
        Image img_next = new Image(urlImgNext);
        imgNext.setImage(img_next);

        String urlImgNext1= getClass().getResource("/resources/img/next.png").toExternalForm();
        Image img_next1 = new Image(urlImgNext1);
        imgNext1.setImage(img_next1);

        String urlImgNext2= getClass().getResource("/resources/img/next.png").toExternalForm();
        Image img_next2 = new Image(urlImgNext2);
        imgNext2.setImage(img_next2); */

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        SVGGlyph iconBtnCamera = Resource.getGlyph("ion-ios-camera-outline");
        iconBtnCamera.setFill(MyUI.redColor);
        iconBtnCamera.setSize(50, 40);
        iconBtnCamera.setRotate(180);
        btnCamera.setAlignment(Pos.BASELINE_CENTER);
        btnCamera.setContentDisplay(ContentDisplay.CENTER);
        btnCamera.setGraphicTextGap(8);
        btnCamera.setGraphic(iconBtnCamera);

        SVGGlyph iconBtnPinpad = Resource.getGlyph("ion-ios-calculator-outline");
        iconBtnPinpad.setFill(MyUI.redColor);
        iconBtnPinpad.setSize(50, 40);
        iconBtnPinpad.setRotate(180);
        btnPinpad.setAlignment(Pos.BASELINE_CENTER);
        btnPinpad.setContentDisplay(ContentDisplay.CENTER);
        btnPinpad.setGraphicTextGap(8);
        btnPinpad.setGraphic(iconBtnPinpad);

        SVGGlyph iconBtnPrinter = Resource.getGlyph("ion-ios-printer-outline");
        iconBtnPrinter.setFill(MyUI.redColor);
        iconBtnPrinter.setSize(50, 40);
        iconBtnPrinter.setRotate(180);
        btnPrinter.setAlignment(Pos.BASELINE_CENTER);
        btnPrinter.setContentDisplay(ContentDisplay.CENTER);
        btnPrinter.setGraphicTextGap(8);
        btnPrinter.setGraphic(iconBtnPrinter);

        SVGGlyph iconBtnSpeaker = Resource.getGlyph("ion-ios-volume-high");
        iconBtnSpeaker.setFill(MyUI.redColor);
        iconBtnSpeaker.setSize(40, 40);
        iconBtnSpeaker.setRotate(0);
        btnSpeaker.setAlignment(Pos.BASELINE_CENTER);
        btnSpeaker.setContentDisplay(ContentDisplay.CENTER);
        btnSpeaker.setGraphicTextGap(8);
        btnSpeaker.setGraphic(iconBtnSpeaker);

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
