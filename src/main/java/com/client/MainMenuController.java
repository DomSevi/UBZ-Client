package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class MainMenuController {
    @FXML   // Ustawienie potrzebnych rzeczy na startcie
    protected void initialize() {
        loggedInAs.setText("Zalogowano jako:\n" + CurrentSession.getUser());
        loggedInAs.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML
    Button homeButton;
    @FXML
    protected void home() {

    }

    @FXML
    Button roomsButton;
    @FXML
    protected void rooms() {

    }

    @FXML
    Button usersButton;
    @FXML
    protected void users() {

    }

    @FXML
    Label loggedInAs;
    @FXML
    Button logoutButton;
    @FXML   // Wylogowanie
    protected void logout(){
        ScreenController.activate("login");
    }

}
