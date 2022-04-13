package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class HomeContoller {

    protected void currentUser() {
        loggedInAs.setText("Zalogowano jako:\n" + CurrentSession.getUser());
        loggedInAs.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML   // Ustawienie potrzebnych rzeczy na startcie
    protected void initialize() {
        // Ukrycie niepotrzebnych 'stron' programu
        subRooms.setVisible(false);
    }

    @FXML
    private GridPane subHome;

    protected static SubHomeController subHomeController;
    @FXML
    private GridPane subRooms;

    protected static  SubRoomsController subRoomsController;


    @FXML
    BorderPane mainPane;
    @FXML
    Button homeButton;
    @FXML
    protected void home() {
        if(!subHome.isVisible()){
            subHome.setVisible(true);
            subRooms.setVisible(false);
        }
    }

    @FXML
    Button roomsButton;
    @FXML
    protected void rooms() {
        if(!subRooms.isVisible()){
            subRoomsController.clear();
            subRooms.setVisible(true);
            subHome.setVisible(false);
        }
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
        // ### wyczyscic wszystko
        AppController.activateScene("login");
    }

}
