package com.client.controllers;

import javafx.fxml.FXML;

public class SubHomeController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.subHomeController = this;
    }

    public void clear() {

    }
}
