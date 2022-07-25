package com.client.controllers;

import javafx.fxml.FXML;

public class HomeScheduleController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeScheduleController = this;
    }

    public void clear() {
    }
}
