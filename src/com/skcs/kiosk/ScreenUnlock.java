package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenUnlock implements Initializable, ControlledScreen {

    ScreensController myController;

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
    private JFXButton btnUnlock;

    @FXML
    private JFXButton btnLock;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnOut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String urlImgLogo = getClass().getResource("/resources/img/logo2.png").toExternalForm();
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

    public static class FXMLTableView extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("FXML TableView Example");
            Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("ScreenManagement.fxml"));
            Scene myScene = new Scene(myPane);
            primaryStage.setScene(myScene);
            primaryStage.show();
        }

        private static void main(String[] args) {
            launch(args);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        SVGGlyph iconBtnUnlock = Resource.getGlyph("ion-ios-unlocked-outline");
        iconBtnUnlock.setFill(MyUI.whiteColor);
        iconBtnUnlock.setSize(40, 40);
        iconBtnUnlock.setRotate(-180);
        btnUnlock.setAlignment(Pos.BASELINE_CENTER);
        btnUnlock.setContentDisplay(ContentDisplay.TOP);
        btnUnlock.setGraphicTextGap(8);
        btnUnlock.setGraphic(iconBtnUnlock);

        SVGGlyph iconBtnLock = Resource.getGlyph("ion-ios-locked-outline");
        iconBtnLock.setFill(MyUI.whiteColor);
        iconBtnLock.setSize(40, 40);
        iconBtnLock.setRotate(180);
        btnLock.setAlignment(Pos.BASELINE_CENTER);
        btnLock.setContentDisplay(ContentDisplay.TOP);
        btnLock.setGraphicTextGap(8);
        btnLock.setGraphic(iconBtnLock);

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