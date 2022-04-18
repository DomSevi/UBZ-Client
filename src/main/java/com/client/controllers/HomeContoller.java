package com.client.controllers;

import com.client.ClientMain;
import com.client.CurrentSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class HomeContoller {

    public void setLoggedInUser() {
        loggedInAs.setText("Zalogowano jako:\n" + CurrentSession.getUser());
        loggedInAs.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML   // Ustawienie potrzebnych rzeczy na starcie
    protected void initialize() {
        // Ustawienie strony
        setPage('h');
    }

    @FXML
    private GridPane subHome;
    @FXML
    private GridPane subRooms;
    @FXML
    private GridPane subEmp;
    public static SubRoomsController subRoomsController;
    public static SubHomeController subHomeController;
    public static SubEmpController subEmpController;

    private void setPage(char c ) {
        if(c == 'h') {
            subHome.setVisible(true);
            subRooms.setVisible(false);
            subEmp.setVisible(false);
        }
        else if(c == 'r'){
            subHome.setVisible(false);
            subRooms.setVisible(true);
            subEmp.setVisible(false);
        }
        else if(c == 'e') {
            subHome.setVisible(false);
            subRooms.setVisible(false);
            subEmp.setVisible(true);
        }
    }

    @FXML
    protected void fullscreen() {
        if(!ClientMain.mainStage.isFullScreen()) {
            ClientMain.mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            ClientMain.mainStage.setFullScreen(true);
        }
        else
            ClientMain.mainStage.setFullScreen(false);
    }

    @FXML   // Obsluga przyskow gornych zmieniajacych podstrone strony home
    BorderPane mainPane;
    @FXML
    Button homeButton;
    @FXML
    protected void home() {
        if(!subHome.isVisible()){
            subHomeController.clear();
            setPage('h');
        }
    }
    @FXML
    Button roomsButton;
    @FXML
    protected void rooms() {
        if(!subRooms.isVisible()){
            subRoomsController.clear();
            setPage('r');
        }
    }
    @FXML
    Button empButton;
    @FXML
    protected void emp() {
        if(!subEmp.isVisible()){
            subEmpController.clear();
            setPage('e');
        }
    }

    @FXML
    Label loggedInAs;
    @FXML
    Button logoutButton;
    @FXML   // Wylogowanie
    protected void logout(){
        // ### wyczyscic wszystko
        subHomeController.clear();
        subRoomsController.clear();
        subEmpController.clear();
        AppController.activateScene("login");
    }

}
