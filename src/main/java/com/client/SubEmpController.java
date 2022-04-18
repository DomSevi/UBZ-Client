package com.client;

import javafx.fxml.FXML;

public class SubEmpController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeContoller.subEmpController = this;
    }
    protected void clear() {

    }
}
