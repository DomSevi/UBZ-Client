package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SubRoomsController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeContoller.subRoomsController = this;
    }

    @FXML
    TextField testText;

    protected void clear() {
        testText.clear();
    }

    @FXML
    Button testButton;

    @FXML
    private void testAction() {
        if (testButton.getText().equals("dziala")) {
            testButton.setText("takze dziala");
        } else {
            testButton.setText("dziala");
        }
    }

}
