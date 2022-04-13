package com.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;

public class AppController {
    protected static Scene mainScene;
    private static HashMap<String, Parent> sceneMap = new HashMap<>();

    protected static LoginController loginController;
    protected static HomeContoller homeContoller;

    static {
        // Stworzenie sceny logowania
        AppController.loginController = prepareScene("login", "fxml/login.fxml");
        Scene scene = new Scene(AppController.sceneMap.get("login"));
        AppController.initScene(scene);

        // Importowanie dodatkowych scen
        AppController.homeContoller = prepareScene("home", "fxml/home.fxml");
    }

    // Przetwarza scene z pliku fxml oraz zwraca controller danej sceny
    private static <T> @Nullable T prepareScene(String name, String dir) {
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource(dir));
        try {
            Parent root = loader.load();
            AppController.addScene(name, root);
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Ustala glowna scene, uzywana tylko przy starcie programu
    protected static void initScene(Scene scene) {
        AppController.mainScene = scene;
    }

    // Dodanie sceny do mapy scen
    protected static void addScene(String name, Parent p) {
        sceneMap.put(name, p);
    }

    // Zmienia dotychczas uzywana scene
    protected static void activateScene(String name) {
        mainScene.setRoot(sceneMap.get(name));
    }
}
