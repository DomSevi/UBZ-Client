package com.client.controllers;

import com.client.ClientMain;
import com.client.CurrentSession;
import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class HomeController {

    // Used when coming from login scene
    public void setLoggedInUser() {
        loggedInAs.setText("Zalogowano jako:\n" + CurrentSession.getUserName());
        loggedInAs.setTextAlignment(TextAlignment.CENTER);


        try {
            EmployeeConv ec = new EmployeeConv();
            RoomConv rc = new RoomConv();
            Room r;
            Employee e = ec.getEmployeeByLogin(CurrentSession.getUserName());
            homeWelcomeController.welcomeLabel.setText("Witaj " +e.getName() + " " + e.getSurname() );
            List<Reservation> eList = e.getReservations();
            StringBuilder sb = new StringBuilder();
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_WEEK);
            if(day > 2) day = day - 2;
            else if (day == 1) day = 6;
            else day = 5;
            for (Reservation res : eList) {
                if(res.getDay().equals((long)day)) {
                    sb.append(res.getHour());
                    sb.append(":00-");
                    sb.append(res.getHour() + 2);
                    sb.append(":00 w ");
                    r = rc.getRoomByNr(res.getRoomId());
                    sb.append(r.getType());
                    sb.append(".\n");
                }
            }
            homeWelcomeController.schedule.setText(sb.toString());
            homeWelcomeController.schedule.setStyle("-fx-text-fill:white;-fx-font-size:20px;");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(CurrentSession.isIsAdmin()) {
            homeEmpController.table.setEditable(true);
            loggedInAs.setUnderline(true);
            homeEmpController.delEmpButton.setDisable(false);
            addEmpButton.setDisable(false);
            addRoomButton.setDisable(false);
            homeEmpController.lockButton.setDisable(false);
            homeRoomsController.delRoomButton.setDisable(false);
        }
        else {
            loggedInAs.setUnderline(false);
            homeEmpController.table.setEditable(false);
            homeEmpController.delEmpButton.setDisable(true);
            addEmpButton.setDisable(true);
            addRoomButton.setDisable(true);
            homeEmpController.lockButton.setDisable(true);
            homeRoomsController.delRoomButton.setDisable(true);
        }
    }

    @FXML   // Ustawienie potrzebnych rzeczy na starcie
    protected void initialize() {
        // Ustawienie strony
        setPage('w');
    }

    @FXML
    private GridPane homeHome;
    @FXML
    private GridPane homeRooms;
    @FXML
    private GridPane homeEmp;
    @FXML
    private GridPane homeAddEmp;
    @FXML
    private GridPane homeAddRoom;

    public static HomeRoomsController homeRoomsController;
    public static HomeWelcomeController homeWelcomeController;
    public static HomeEmpController homeEmpController;
    public static HomeAddEmpController homeAddEmpController;
    public static HomeAddRoomController homeAddRoomController;

    // w - welcome, r - rooms, e - employees, a - addemp, b - addroom
    private void setPage(char c ) {
        homeHome.setVisible(false);
        homeRooms.setVisible(false);
        homeEmp.setVisible(false);
        homeAddEmp.setVisible(false);
        homeAddRoom.setVisible(false);
        try {
            if (c == 'w') {
                homeHome.setVisible(true);
            } else if (c == 'r') {
                homeRooms.setVisible(true);
            } else if (c == 'e') {
                homeEmp.setVisible(true);
            } else if (c == 'a') {
                homeAddEmp.setVisible(true);
            } else if (c == 'b') {
                homeAddRoom.setVisible(true);
            } else
                throw new IllegalArgumentException("Wrong argument: " + c);
        }
        catch(IllegalArgumentException e) {
            System.err.println(e.getMessage());
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


    @FXML // Obsluga przyskow gornych zmieniajacych podstrone strony home
    Button homeButton;
    @FXML
    protected void home() {
        if(!homeHome.isVisible()) {
            homeWelcomeController.clear();
            setPage('w');
        }
    }
    @FXML
    Button roomsButton;
    @FXML
    protected void rooms() {
        if(!homeRooms.isVisible()) {
            homeRoomsController.clear();
            setPage('r');
        }
    }
    @FXML
    Button empButton;
    @FXML
    protected void emp() {
        if(!homeEmp.isVisible()) {
            homeEmpController.clear();
            HomeEmpController.refreshTable();
            setPage('e');
        }
    }

    @FXML
    Button addRoomButton;
    @FXML
    public void addRoom() {
        if(!homeAddRoom.isVisible()) {
            setPage('b');
        }
    }

    @FXML
    Button addEmpButton;
    @FXML
    public void addEmp() {
        if(!homeAddEmp.isVisible()) {
            setPage('a');
        }
    }

    @FXML
    Label loggedInAs;
    @FXML
    Button logoutButton;
    @FXML   // Wylogowanie
    protected void logout(){
        // ### wyczyscic wszystko
        if(!homeHome.isVisible()) {
            setPage('w');
        }
        AppController.loginController.clearScene();

        AppController.activateScene("login");
    }




}
