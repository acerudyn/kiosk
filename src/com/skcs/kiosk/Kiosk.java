package com.skcs.kiosk;

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import com.res.Resource;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Angie
 */
public class Kiosk extends Application {

    public static String scrInit = "scrIdle";
    public static String scrInitFile = "ScreenInit.fxml";

    public static String scrSet = "scrSet";
    public static String scrSetFile = "ScreenSetting.fxml";

    public static String scrIdle = "scrIdle";
    public static String scrIdleFile = "ScreenIdle.fxml";

    public static String scrLogin = "ScreenLogin";
    public static String scrLoginFile = "ScreenLogin.fxml";

    public static String scrSetTerminal = "ScreenSetTerminal";
    public static String scrSetTerminalFile  = "ScreenSetTerminal.fxml";

    public static String scrLang = "scrLang";
    public static String scrLangFile = "ScreenLanguage.fxml";

    public static String scrMainMenu = "scrMainMenu";
    public static String scrMainMenuFile = "ScreenMainMenu.fxml";

    public static String scrSwipe = "scrSwipe";
    public static String scrSwipeFile = "ScreenSwipeCard.fxml";

    public static String scrInputPin = "scrInputPin";
    public static String scrInputPinFile = "ScreenInputPin.fxml";

    public static String scrProcTran = "scrProcTran";
    public static String scrProcTranFile = "ScreenProcTran.fxml";

    public static String sqliteLogin = "SqliteLogin";
    public static String sqliteLoginFile = "SqliteLogin.fxml";

    public static String scrChangePwd = "ScreenChangePwd";
    public static String scrChangePwdFile = "ScreenChangePwd.fxml";

    public static String scrTestDriver = "ScreenTestDriver";
    public static String scrTestDriverFile = "ScreenTestDriver.fxml";

    public static String scrManagement = "ScreenManagement";
    public static String scrManagementFile = "ScreenManagement.fxml";

    public static String scrUpload = "ScreenUpload";
    public static String scrUploadFile = "ScreenUpload.fxml";

    public static String scrUnlock = "ScreenUnlock";
    public static String scrUnlockFile = "ScreenUnlock.fxml";

    public static String scrAdd = "ScreenAddUser";
    public static String scrAddFile = "ScreenAddUser.fxml";

    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Kiosk.scrInit, Kiosk.scrInitFile);

        mainContainer.setScreen(Kiosk.scrInit);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreen(true);
        //primaryStage.setMaximized(true);
        primaryStage.setMaxWidth(1024);
        primaryStage.setMaxHeight(768);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Resource.init();

        launch(args);
    }
}
