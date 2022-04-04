package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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

            // Utworzenie domyślnej sceny
            Scene scene = new Scene(new VBox());
            AppController.initScene(scene);

            // Zaimportowanie sceny logowania
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
            Parent root = loader.load();
            AppController.addScene("login",root);
            AppController.loginController = loader.getController();

            // Pokazanie sceny logowania
            AppController.activateScene("login");
            mainStage.setScene(scene);
            mainStage.show();

            // Zainicjalizowanie innych scen oraz ich kontrolerow
            AppController.mainSceneController = prepareScene(MainSceneController.class,"mainScene", "fxml/mainScene.fxml");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Inicjalizuje sceny oraz zwraca kontroler danej sceny
    @SuppressWarnings("unchecked")
    private <T> T prepareScene(Class<T> returnType, String name, String dir) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(dir));
        try {
            Parent root = loader.load();
            AppController.addScene(name,root);
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (T) returnType;
    }

}