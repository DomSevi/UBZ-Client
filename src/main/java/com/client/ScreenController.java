package com.client;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    public ScreenController() {
    }

    public ScreenController(Scene main) {
        ScreenController.main = main;
    }

    protected void add(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    protected void remove(String name) {
        screenMap.remove(name);
    }

    protected static void applyStyle(String style) {
        main.getStylesheets().add(ScreenController.class.getResource("css/" + style).toExternalForm());
    }

    protected static void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
