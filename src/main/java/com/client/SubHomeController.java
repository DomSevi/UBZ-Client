package com.client;

import javafx.fxml.FXML;

public class SubHomeController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeContoller.subHomeController = this;
    }
}
