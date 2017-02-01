package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.svg.SVGGlyph;
import com.res.Resource;
import com.skcs.kiosk.trx.TransactionData;
//import com.skcs.kiosk.trx.TransactionType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenMainMenu implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    VBox vboxContainer;
    @FXML
    private HBox vboxMenuTitle;
    @FXML
    private Label lblMenuTitle;
    @FXML
    ImageView imgViewLogo;

    @FXML
    private Button btnBalanceInquiry; // JFX perbedaan Private sama tidak
    @FXML
    private JFXButton btnTransfer;
    @FXML
    private JFXButton btnChangePIN;
    @FXML
    private JFXButton btnPurchase;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private GridPane gridpaneMenu;

    @FXML
    private HBox mainMenuNavbar;

    @FXML
    JFXDialog dialogOk;
    @FXML
    Label dialogTitleOk;
    @FXML
    Label dialogMessageOk;
    @FXML
    JFXButton dialogButtonOk;

    private Timeline tmlScreenTimeout;
    private TransactionData transactionData = TransactionData.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TransactionData.getInstance().initData();
        String urlImgLogo= getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        //lblSwipeCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //@Override
            //public void handle(MouseEvent event) {
                //dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
                //dialog.show(root);
                //doSwipeCardService.cancel();
            //}
        //});

        //(new DoSwipeCard()).execute();
        //serviceRunningIndicator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //@Override
            //public void handle(MouseEvent event) {
                //doSwipeCardService.succeeded();
                //doSwipeCardService.failed();
            //}
        //});

        vboxMenuTitle.setAlignment(Pos.CENTER_LEFT);
        lblMenuTitle.setText(Resource.getLabel("main_menu_title"));

        SVGGlyph iconBtnBalanceInquiry = Resource.getGlyph("ion-ios-folder-outline");
        iconBtnBalanceInquiry.setFill(MyUI.whiteColor);
        iconBtnBalanceInquiry.setSize(32, 32);
        iconBtnBalanceInquiry.setRotate(180);
        //btnBalanceInquiry.setAlignment(Pos.CENTER);
        btnBalanceInquiry.setContentDisplay(ContentDisplay.TOP);
        btnBalanceInquiry.setGraphicTextGap(8);
        btnBalanceInquiry.setGraphic(iconBtnBalanceInquiry);
        btnBalanceInquiry.setText(Resource.getLabel("menu_balance_inquiry"));
        btnBalanceInquiry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tmlScreenTimeout.stop();
               // transactionData.setTransactionType(TransactionType.BALANCE_INQUIRY);
                transactionData.setTranTitle(btnBalanceInquiry.getText());
                if (!"".equals(myController.getScreen(Kiosk.scrInputPin))) {
                    myController.loadScreen(Kiosk.scrInputPin, Kiosk.scrInputPinFile);
                }
                myController.setScreen(Kiosk.scrInputPin);
            }
        });

        SVGGlyph iconBtnTransfer = Resource.getGlyph("ion-ios-browsers-outline");
        iconBtnTransfer.setFill(MyUI.whiteColor);
        iconBtnTransfer.setSize(32, 32);
        //btnTransfer.setAlignment(Pos.CENTER);
        btnTransfer.setContentDisplay(ContentDisplay.TOP);
        btnTransfer.setGraphicTextGap(8);
        btnTransfer.setGraphic(iconBtnTransfer);
        btnTransfer.setText(Resource.getLabel("menu_transfer"));
        btnTransfer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tmlScreenTimeout.stop();
                transactionData.setTranTitle(btnTransfer.getText());
            }
        });

        SVGGlyph iconBtnChangePIN = Resource.getGlyph("ion-ios-keypad-outline");
        iconBtnChangePIN.setFill(MyUI.whiteColor);
        iconBtnChangePIN.setSize(32, 32);
        //btnChangePIN.setAlignment(Pos.CENTER);
        btnChangePIN.setContentDisplay(ContentDisplay.TOP);
        btnChangePIN.setGraphicTextGap(8);
        btnChangePIN.setGraphic(iconBtnChangePIN);
        btnChangePIN.setText(Resource.getLabel("menu_change_pin"));
        btnChangePIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tmlScreenTimeout.stop();
                transactionData.setTranTitle(btnChangePIN.getText());
            }
        });

        SVGGlyph iconBtnPurchase = Resource.getGlyph("ion-ios-pricetag-outline");
        iconBtnPurchase.setFill(MyUI.whiteColor);
        iconBtnPurchase.setSize(32, 32);
        iconBtnPurchase.setRotate(180);
        //iconBtnPurchase.setScaleX(-1);
        //btnPurchase.setAlignment(Pos.CENTER);
        btnPurchase.setContentDisplay(ContentDisplay.TOP);
        btnPurchase.setGraphicTextGap(8);
        btnPurchase.setGraphic(iconBtnPurchase);
        btnPurchase.setText(Resource.getLabel("menu_purchase"));
        btnPurchase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tmlScreenTimeout.stop();
                TransactionData.getInstance().initData();
                TransactionData.getInstance().setTranTitle(btnPurchase.getText());

            }
        });

        //Cancel
        SVGGlyph iconBtnCancel = Resource.getGlyph("ion-close-circled");
        iconBtnCancel.setFill(MyUI.whiteColor);
        iconBtnCancel.setSize(24, 24);
        btnCancel.setAlignment(Pos.CENTER_RIGHT);
        btnCancel.setContentDisplay(ContentDisplay.RIGHT);
        btnCancel.setGraphicTextGap(8);
        btnCancel.setGraphic(iconBtnCancel);
        btnCancel.setText(Resource.getLabel("cancel"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tmlScreenTimeout.stop();
                myController.setScreen(Kiosk.scrIdle);
            }
        });

        tmlScreenTimeout = new Timeline(
            new KeyFrame(Duration.seconds(Resource.getConfigInt("screen_timeout")), actionEvent -> {
                tmlScreenTimeout.stop();
                myController.setScreen(Kiosk.scrIdle);
            }
        ));
        tmlScreenTimeout.play();
    }

    private void showErrorDialog(String message) {
        root.getChildren().remove(dialogOk);
        dialogTitleOk.setText(Resource.getLabel("message"));
        dialogMessageOk.setText(message);
        dialogButtonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Clear transaction data
                tmlScreenTimeout.stop();
                myController.setScreen(Kiosk.scrIdle);
            }
        });
        dialogOk.show(root);
    }

    private void showDialog(String message, EventHandler<ActionEvent>actionEvent) {
        root.getChildren().remove(dialogOk);
        dialogTitleOk.setText(Resource.getLabel("message"));
        dialogMessageOk.setText(message);
        /*
        dialogButtonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Clear transaction data
                doSwipeCardService.failed();
                myController.setScreen(Kiosk.scrIdle);
            }
        });
        */
        dialogButtonOk.setOnAction(actionEvent);
        dialogOk.show(root);
    }
}
