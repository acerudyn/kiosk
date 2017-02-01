package com.skcs.kiosk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.res.Resource;
import com.skcs.kiosk.pinpad.Pinpad;
import com.skcs.kiosk.pinpad.PinpadResult;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenProcTran implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    VBox vboxContainer;
    @FXML
    Label lblScreenTitle;
    @FXML
    ImageView imgViewLogo;

    @FXML
    Label lblProcTranHint;
    @FXML
    ImageView imgViewProcTran;
    @FXML
    private ProgressIndicator serviceProcIndicator;

    @FXML
    HBox procTranNavbar;
    @FXML
    private ProgressIndicator serviceRunningIndicator;
    @FXML
    Label lblProcTranStatus;

    @FXML
    JFXDialog dialogOk;
    @FXML
    Label dialogTitleOk;
    @FXML
    Label dialogMessageOk;
    @FXML
    JFXButton dialogButtonOk;

    // Printer printer = Printer.getInstance();
    Pinpad pinpad = Pinpad.getInstance();
    PinpadResult pinpadResult;
    int timeout;
    private Timeline tmlScreenTimeout;
    private DoProcTranService doInputPin;
    private StringProperty procTranStatusProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeout = Resource.getConfigInt("screen_pin_timeout");
        serviceProcIndicator.setVisible(true);
        serviceRunningIndicator.setVisible(false);
        //lblSwipeCard.setText(Resource.getLabel("swipe_card"));

        String urlImgLogo= getClass().getResource("/resources/img/logo2.png").toExternalForm();
        Image imgLogo = new Image(urlImgLogo);
        imgViewLogo.setImage(imgLogo);

        String urlImgProcTran = getClass().getResource("/resources/img/kiosk3.png").toExternalForm();
        Image imgProcTran = new Image(urlImgProcTran);
        imgViewProcTran.setImage(imgProcTran);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        //lblSwipeCardHint.setText("Please swipe\nyour card\nto the machine below");
        lblProcTranHint.setText(Resource.getLabel("transaction_process"));
        /*
        AnchorPane.setBottomAnchor(
            lblProcTranHint,
            imgViewProcTran.getLayoutBounds().getWidth() +
            4 + //right anchor
            5 //space
        );
        */

        //procTranNavbar.setVisible(false);

        doInputPin = new DoProcTranService();
        doInputPin.messageProperty().addListener(
            (ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
                procTranStatusProperty().set(newValue);
            }
        );
        lblProcTranStatus.textProperty().bind(procTranStatusProperty());
        //procTranStatusProperty().set(Resource.getLabel("swipe_card"));
        doInputPin.restart();

        //lblSwipeCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //@Override
            //public void handle(MouseEvent event) {
                //dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
                //dialog.show(root);
                //doInputPin.cancel();
            //}
        //});

        //(new DoSwipeCard()).execute();
        //serviceRunningIndicator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //@Override
            //public void handle(MouseEvent event) {
                //doInputPin.succeeded();
                //doInputPin.failed();
            //}
        //});

        /*
        tmlScreenTimeout = new Timeline(
            new KeyFrame(Duration.seconds(Resource.getConfigInt("screen_timeout")), actionEvent -> {
                if (!(doInputPin.getState() == Worker.State.SUCCEEDED)) {
                    showErrorDialog(Resource.getLabel("timeout"));
                }
            }
        ));
        tmlScreenTimeout.play();
        */
    }

    private StringProperty procTranStatusProperty() {
        if (procTranStatusProperty == null) {
            procTranStatusProperty = new SimpleStringProperty();
        }
        return procTranStatusProperty;
    }

    private void showErrorDialog(String message) {
        root.getChildren().remove(dialogOk);
        dialogTitleOk.setText(Resource.getLabel("message"));
        dialogMessageOk.setText(message);
        dialogButtonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Clear transaction data
                doInputPin.failed();
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
                doInputPin.failed();
                myController.setScreen(Kiosk.scrIdle);
            }
        });
        */
        dialogButtonOk.setOnAction(actionEvent);
        dialogOk.show(root);
    }

    private class DoProcTranService extends Service<Void> {
        @Override
        protected void succeeded() {
            //procTranStatusProperty().set("Processing...");

            //serviceRunningIndicator.setVisible(false);

            /*
            String str = "Your card is\n" + pinpadResult.getString();
            showDialog(str, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!"".equals(myController.getScreen(Kiosk.scrMainMenu))) {
                        myController.loadScreen(Kiosk.scrMainMenu, Kiosk.scrMainMenuFile);
                    }
                    myController.setScreen(Kiosk.scrMainMenu);
                }
            });
            */

            /*
            if (!"".equals(myController.getScreen(Kiosk.scrMainMenu))) {
                myController.loadScreen(Kiosk.scrMainMenu, Kiosk.scrMainMenuFile);
            }
            myController.setScreen(Kiosk.scrMainMenu);
            */
        }

        @Override
        protected void failed() {

        }

        //@Override
        protected void cancelled() {
            procTranStatusProperty().set("");
            serviceRunningIndicator.setVisible(false);
            switch(pinpadResult.getResult()) {
                case -1:
                    showErrorDialog(Resource.getLabel("error"));
                    break;
                case -2:
                    showErrorDialog(Resource.getLabel("transaction_cancelled"));
                    break;
                case -3:
                case -99:
                    showErrorDialog(Resource.getLabel("timeout"));
                    break;
                default:
                    showErrorDialog(Resource.getLabel("error"));
                    break;
            }
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //updateMessage("Processing");


                   /* PrinterObject prnObj = new PrinterObject();
                    prnObj.header1 = "MERCHANT SKCS";
                    prnObj.header2 = "ROXY SQUARE";
                    prnObj.TID = "10101010";
                    prnObj.MID = "100100100100100";
                    prnObj.cardNo = "4321 5555 6666 0007";
                    prnObj.trxDate = "28-11-2016";
                    prnObj.trxTime = "18:08:32";
                    prnObj.invNo = "123456";
                    prnObj.apprCode = "APPRVD";
                    prnObj.traceNo = "000111";
                    prnObj.refNo = "ABCD00001111";
                    prnObj.footer1 = Resource.getLabel("footer1");
                    prnObj.footer2 = Resource.getLabel("footer2");
                    prnObj.footer3 = Resource.getLabel("footer3");
                    prnObj.footer4 = Resource.getLabel("footer4");
                    prnObj.footer5 = Resource.getLabel("footer5");
                    prnObj.footer6 = Resource.getLabel("footer6");
                    prnObj.footer7 = Resource.getLabel("footer7");
                    prnObj.footer8 = Resource.getLabel("footer8");
                    String receiptLayout = Resource.getLabel("receipt_layout");
                    printer.printReceipt("/resources/prt/" + receiptLayout, prnObj);
*/
                    return null;
                }
            };
        }
    }

    /*
    private class DoSwipeCard extends AsyncTask {

        @Override
        public void onPreExecute() {
            try {
                Thread.sleep(2000);
            } catch (Exception e){}
            serviceRunningIndicator.setVisible(true);
        }

        @Override
        public void doInBackground() {

        }

        @Override
        public void onPostExecute() {

        }

        @Override
        public void progressCallback(Object... params) {

        }

    }
    */
}
