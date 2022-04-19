package com.client;

import com.client.controllers.AppController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/*
 * ClientMain ustala tylko najwazniejsze opcje glownego okna,
 * oraz importuje pare zasobow takich jak czcionki czy ikony.
 *
 * AppController zawiera wszyskie sceny, controllery oraz pola potrzebne do pracy programu,
 * pozwala on rowniez na zmiane scen oraz wywolywanie potrzebnych metod z konkretnych controllerow
 */

public class ClientMain extends Application {

    public static Stage mainStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Zapewnienie dostepu do glownego okna
        mainStage = stage;
        // Ustawianie najważniejszych opcji okna
        mainStage.setTitle("XYZ");


        mainStage.setMinWidth(1280);
        mainStage.setMinHeight(740);
        mainStage.initStyle(StageStyle.DECORATED);

        // Importowanie czesci zasobow
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/appicon.png")).toString()));
        Font.loadFont(getClass().getResourceAsStream("fonts/Lato-Regular.ttf"), 14);

        // Ustalenie glownej sceny i jej pokazanie
        mainStage.setScene(AppController.mainScene);
        mainStage.show();

    }
}