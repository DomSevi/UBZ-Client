package com.client.controllers;

import javafx.fxml.FXML;

public class HomeAddEmpController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeAddEmpController = this;
    }

    public void clear() {
    }
}
