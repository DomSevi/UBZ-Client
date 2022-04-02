package com.client;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Objects;

public class ScreenController {
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    public ScreenController(Scene main) {
        ScreenController.main = main;
    }

    protected static void setScene(Scene scene){
        ScreenController.main = scene;
    }

    protected static void add(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    protected static void remove(String name) {
        screenMap.remove(name);
    }

    protected static boolean contains(String name) {
        return screenMap.containsKey(name);
    }

    protected static void activate(String name) {
        main.setRoot(screenMap.get(name));
    }

    protected static void applyStyle(String style) {
        main.getStylesheets().add(Objects.requireNonNull(ScreenController.class.getResource("css/" + style)).toExternalForm());
    }
}
