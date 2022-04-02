package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ClientMain extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        try {
            // Ustawianie najważniejszych opcji okna
            mainStage.setTitle("XYZ");
            mainStage.setMinWidth(800);
            mainStage.setMinHeight(650);
            mainStage.initStyle(StageStyle.DECORATED);
            mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/appicon.png")).toString()));
            Font.loadFont(getClass().getResourceAsStream("fonts/Lato-Regular.ttf"), 14);


            // Zaimportowanie i pokazanie sceny logowania
            mainStage.setScene(initializeMainScene());
            mainStage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private @NotNull Scene initializeMainScene() {
        Scene scene = new Scene(new AnchorPane(),1280,720,false, SceneAntialiasing.BALANCED);
        ScreenController.setScene(scene);
        try {
            System.setProperty("prism.lcdtext", "false");
            ScreenController.add("login", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/login.fxml"))));
            ScreenController.activate("login");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return scene;
    }

}