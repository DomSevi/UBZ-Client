package com.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeWelcomeController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeWelcomeController = this;
    }

    @FXML
    Label schedule;
    @FXML
    Label welcomeLabel;
    public void clear() {

    }
}
