package com.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeAddRoomController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeAddRoomController = this;
    }

    public void clear() {
    }
    @FXML
    Label errorLabel;
    @FXML
    TextField capacity;
    @FXML
    TextField level;
    @FXML
    TextField type;

    @FXML
    Button acceptButton;
    @FXML
    protected void addRoom() {

    }
}
