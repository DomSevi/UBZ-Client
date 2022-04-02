package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class MainMenuController {
    @FXML   // Ustawienie potrzebnych rzeczy na startcie
    protected void initialize() {
        loginInAs.setText("Zalogowano jako:\n" + CurrentSession.getUser());
        loginInAs.setTextAlignment(TextAlignment.CENTER);
    }
    @FXML
    Button logout;
    @FXML
    Label loginInAs;

    @FXML   // Wylogowanie
    protected void logout(){
        ScreenController.activate("login");
    }

}
