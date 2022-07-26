package com.client.controllers;

import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HomeAddRoomController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeAddRoomController = this;

    }

    public void clear() {
        capacity.setText("");
        level.setText("");
        type.setText("");
        errorLabel.setVisible(false);
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
        System.out.println("here");
        //jezeli pola sa puste
        if(capacity.getText().isEmpty() ||
                level.getText().isEmpty() || type.getText().isEmpty()) {
            errorLabel.setText("Wypełnij wszystkie pola!");
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
            Room r = new Room(Long.parseLong(level.getText()), Long.parseLong(capacity.getText()),
                    type.getText());
            RoomConv rc = new RoomConv();
            try {
                rc.createNewRoom(r);
                clear();
                errorLabel.setText("Dodano pomyślnie!");
                errorLabel.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}
