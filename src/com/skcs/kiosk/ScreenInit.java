package com.skcs.kiosk;

import com.res.Resource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adeirwansiah on 11/11/16.
 */
public class ScreenInit implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    StackPane root;

    @FXML
    VBox vboxContainer;

    @FXML
    private ProgressIndicator serviceRunningIndicator;
    @FXML
    Label lblSwipeCard;

    private ConnectService connectService;
    private StringProperty statusMessagesProperty;

    private void loadFont() {
        Font.loadFont(Kiosk.class.getResource("/resources/font/ubuntu/Ubuntu-B.ttf").toExternalForm(), 10);
        Font.loadFont(Kiosk.class.getResource("/resources/font/opensans/OpenSans-CondLight.ttf").toExternalForm(), 0);
        Font.loadFont(Kiosk.class.getResource("/resources/font/ubuntu/Ubuntu-R.ttf").toExternalForm(), 10);
        Font.loadFont(Kiosk.class.getResource("/resources/font/ubuntu/UbuntuMono-R.ttf").toExternalForm(), 10);
        Font.loadFont(Kiosk.class.getResource("/resources/font/roboto/Roboto-Bold.ttf").toExternalForm(), 10);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //HBox.setHgrow(root, Priority.ALWAYS);
        //VBox.setVgrow(vboxContainer, Priority.ALWAYS);
        //vboxContainer.prefHeightProperty().bind(root.widthProperty());

        lblSwipeCard.setText(Resource.getLabel("init"));
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        //root.prefWidthProperty().bind();
        connectService = new ConnectService();
        connectService.messageProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            statusMessagesProperty().set(newValue);
        });
        lblSwipeCard.textProperty().bind(statusMessagesProperty());
        serviceRunningIndicator.setVisible(true);
        connectService.restart();
    }

    private StringProperty statusMessagesProperty() {
        if (statusMessagesProperty == null) {
            statusMessagesProperty = new SimpleStringProperty();
        }
        return statusMessagesProperty;
    }

    private class ConnectService extends Service<Void> {
        @Override
        protected void succeeded() {
            statusMessagesProperty().set("Done.");
            if (!"".equals(myController.getScreen(Kiosk.scrIdle))) {
                myController.loadScreen(Kiosk.scrIdle, Kiosk.scrIdleFile);
            }
            myController.setScreen(Kiosk.scrIdle);
        }

        @Override
        protected void failed() {
            statusMessagesProperty().set("Load error.");
        }

        //@Override
        //protected void cancelled() {
        //    statusMessagesProperty().set("Connecting cancelled.");
        //}

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Load font...");
                    loadFont();
                    Thread.sleep(1000);
                    
                    //DEMO: uncomment to provoke "Not on FX application thread"-Exception:
                    //connectButton.setVisible(false);
                    updateMessage("Finishing...");
                    Thread.sleep(1000);
                    return null;
                }
            };
        }
    }
}
