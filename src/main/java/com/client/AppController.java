package com.client;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;

public class AppController {
    private static Scene mainScene;
    private static HashMap<String, Parent> sceneMap = new HashMap<>();

    public static LoginController loginController;
    public static MainSceneController mainSceneController;

    protected static void initScene(Scene scene) {
        AppController.mainScene = scene;
    }

    protected static void addScene(String name, Parent p) {
        sceneMap.put(name,p);
    }

    protected static void activateScene(String name) {
        mainScene.setRoot(sceneMap.get(name));
    }
}
