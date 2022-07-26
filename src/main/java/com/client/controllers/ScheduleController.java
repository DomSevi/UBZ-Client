package com.client.controllers;

import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.reservation.ReservationConv;
import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import com.client.data.RoomClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleController {
    @FXML   // Ustawienie potrzebnych rzeczy na starcie
    protected void initialize() {
        cmMon8.setVisible(false);
        cmTue8.setVisible(false);
        cmWed8.setVisible(false);
        col6.setPercentWidth(0);
        addLabel.setVisible(false);
        searchFilter.setVisible(false);
        table.setVisible(false);
        dayChoicebox.setVisible(false);
        hourChoicebox.setVisible(false);
        acceptButton.setVisible(false);
        resetButton.setVisible(false);
        resetImg.setVisible(false);
        errorLabel.setVisible(false);
        //refreshTable();

        // Inicjalizacja choiceboxow
        dayChoicebox.getItems().removeAll(dayChoicebox.getItems());
        dayChoicebox.getItems().addAll("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek");
        hourChoicebox.getItems().removeAll(hourChoicebox.getItems());
        hourChoicebox.getItems().addAll("08:00", "10:00", "12:00", "14:00", "16:00");

        //inicjalizacja tabeli
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        roomIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomIdColumn.setMinWidth(30);
        roomLevelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        roomLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomLevelColumn.setMinWidth(30);
        roomCapColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty());
        roomCapColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomCapColumn.setMinWidth(30);
        roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        roomTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomTypeColumn.setMinWidth(60);

        FilteredList<RoomClient> filteredData = new FilteredList<>(masterData, p -> true);
        searchFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(roomClient -> {
            // Jezeli pole tekstowe jest puste to wyswietl wszystko
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Porownanie imienia i nazwiska z polem tekstowym
            String lowerCaseFilter = newValue.toLowerCase();
            if (roomClient.getType().toLowerCase().contains(lowerCaseFilter))
                return true;
            else return false;
        }));

        SortedList<RoomClient> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void clear() {

    }

    public static void refreshTable() {
        masterData.removeAll(masterData);
        RoomConv rc = new RoomConv();
        try {
            List<Room> rooms = rc.getAllRooms();
            for (Room r : rooms) {
                List<Reservation> l = r.getReservations();
                for (Reservation res : l) {

                }
                masterData.add(new RoomClient(r.getRoomNr().toString(), r.getLevel().toString(), r.getCapacity().toString(), r.getType()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String login;
    private List<Integer> listaRez = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    // Funkcja wywoływana gdy wchodzimy na strone rezerwacji
    public void setSchedule(String login, String name, String surname) {
        this.login = login;
        topLabel.setText("Szczegółowy plan dla " + name + " " + surname);
        try {
            EmployeeConv ec = new EmployeeConv();
            Employee emp = ec.getEmployeeByLogin(login);
            List<Reservation> lista = emp.getReservations();
            fillSchedule(lista, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setSchedule(Long id, String type, Long level) {
        topLabel.setText("Szczegółowy plan dla " + type + " na piętrze " + level);
    }

    // DAY - 2 - MONDAY.. 3 4 5 6 - friday
    // Dla kazdej rezerwacji wywoluje funkcji
    private void fillSchedule(List<Reservation> lista, boolean isPerson) {
        for (Reservation r : lista) {
            Date d = r.getFrom();
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int day = c.get(Calendar.DAY_OF_WEEK);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            Long id;
            if (isPerson)
                id = r.getRoomId();
            else
                id = r.getEmpId();
            fillSingle(day, hour, id, isPerson, r.getReservationId().intValue());
        }
    }

    // jezeli isPerson = true, id - id pokoju, isPerson = false, id - id osoby
    private void fillSingle(int day, int hour, Long id, boolean isPerson, int resId) {
        if (day == 2) {
            if (hour == 8) {
                listaRez.set(0, resId);
                styleSelected(mon8, id, isPerson,cmMon8);
            }
        } else if (day == 3) {
            if (hour == 8) {
                listaRez.set(5, resId);
                styleSelected(tue8, id, isPerson,cmTue8);
            }
        } else if (day == 4) {

        } else if (day == 5) {

        } else if (day == 6) {

        }
    }

    // orange -fx-background-color:  linear-gradient(to bottom, #ebd834, #eddf13);  3
    // blue -fx-background-color:  linear-gradient(to bottom, #489ff0, #4872f0);    1
    // cyean -fx-background-color:  linear-gradient(to bottom, #48cfd9, #28e9f7);    0
    // yellolw -fx-background-color:  linear-gradient(to bottom, #80d945, #7cf52c);    2
    private void styleSelected(Label l, Long id, boolean isPerson, MenuItem mi) {
        mi.setVisible(true);
        if (isPerson) {
            try {
                RoomConv rc = new RoomConv();
                Room r = rc.getRoomByNr(id);
                l.setText(r.getType());
                Long level = r.getLevel();
                if (level == 0)
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #48cfd9, #28e9f7);");
                else if (level == 1)
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #489ff0, #4872f0);");
                else if (level == 2)
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #80d945, #7cf52c);");
                else if (level == 3)
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #ebd834, #eddf13);");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    Label topLabel;
    @FXML
    Button addReservationButton;
    @FXML
    Button exitButton;
    @FXML
    Label errorLabel;

    @FXML
    protected void exit() {
        AppController.activateScene("home");
    }

    @FXML
    Button resetButton;
    @FXML
    ImageView resetImg;

    @FXML
    TextField searchFilter;
    @FXML
    protected void resetFilter() {
        if(!searchFilter.getText().isEmpty())
            searchFilter.setText("");
    }

    @FXML
    TableView<RoomClient> table;
    @FXML
    TableColumn<RoomClient, String> roomIdColumn;
    @FXML
    TableColumn<RoomClient, String> roomLevelColumn;
    @FXML
    TableColumn<RoomClient, String> roomCapColumn;
    @FXML
    TableColumn<RoomClient, String> roomTypeColumn;

    private static ObservableList<RoomClient> masterData = FXCollections.observableArrayList();

    @FXML
    Label addLabel;
    @FXML
    ChoiceBox<String> dayChoicebox;
    @FXML
    ChoiceBox<String> hourChoicebox;
    @FXML
    Button acceptButton;



    @FXML
    ColumnConstraints col6;



    @FXML
    protected void addReservation() {
        if(!addLabel.isVisible()) {

            col6.setPercentWidth(40);
            refreshTable();
            addLabel.setVisible(true);
            searchFilter.setVisible(true);
            table.setVisible(true);
            dayChoicebox.setVisible(true);
            hourChoicebox.setVisible(true);
            acceptButton.setVisible(true);
            resetButton.setVisible(true);
            resetImg.setVisible(true);
        }
        else {
            col6.setPercentWidth(0);
            addLabel.setVisible(false);
            searchFilter.setVisible(false);
            table.setVisible(false);
            dayChoicebox.setVisible(false);
            hourChoicebox.setVisible(false);
            acceptButton.setVisible(false);
            resetButton.setVisible(false);
            resetImg.setVisible(false);
        }
    }

    @FXML
    protected void submitRes() {
        if(dayChoicebox.getSelectionModel().isEmpty() || hourChoicebox.getSelectionModel().isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Wybierz wszystkie pola");
        }
        else if( table.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Wybierz pokój z tabeli");
        }
        else {
            try {
                RoomConv rc = new RoomConv();
                Room r = rc.getRoomByNr(Long.parseLong(table.getSelectionModel().getSelectedItem().getId()));
                List<Reservation> roomList = r.getReservations();
                //roomList.forEach(res -> System.out.println(res.getFrom()));
                String myDate = getDate();
                //System.out.println(myDate);
                for (Reservation res: roomList) {
                    if(res.getFrom().toString().equals(myDate)) {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Wybrana godzina jest już zajęta!");
                        return;
                    }
                }
                ReservationConv resC = new ReservationConv();
                //resC.createNewReservation(new Reservation());
                col6.setPercentWidth(0);
                addLabel.setVisible(false);
                errorLabel.setVisible(false);
                searchFilter.setVisible(false);
                table.setVisible(false);
                dayChoicebox.setVisible(false);
                hourChoicebox.setVisible(false);
                acceptButton.setVisible(false);
                resetButton.setVisible(false);
                resetImg.setVisible(false);
                hourChoicebox.setSelectionModel(null);
                dayChoicebox.setSelectionModel(null);
                searchFilter.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private String getDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDay());
        sb.append(" Jan ");
        sb.append(getDayInt());
        sb.append(" ");
        sb.append(getHour());
        sb.append(":00:00 CET 2022");
        return sb.toString();
    }

    private String getDay() {
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 0)
            return "Mon";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 1)
            return "Tue";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 2)
            return "Wed";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 3)
            return "Thu";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 4)
            return "Fri";
        return "Lol";
    }
    private String getDayInt() {
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 0)
            return "03";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 1)
            return "04";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 2)
            return "05";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 3)
            return "06";
        if(dayChoicebox.getSelectionModel().getSelectedIndex() == 4)
            return "07";
        return "10";
    }

    private String getHour() {
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 0)
            return "08";
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 1)
            return "10";
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 2)
            return "12";
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 3)
            return "14";
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 4)
            return "16";
        return "20";
    }


    private void deleteSchedule(int resId, Label l, MenuItem m,int listId) {
        try {
            ReservationConv rc = new ReservationConv();
            rc.removeReservationByid((long) resId);
            l.setStyle("-fx-background-color: transparent;");
            l.setText("-");
            m.setVisible(false);
            listaRez.set(listId, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    Label mon8;
    @FXML
    MenuItem cmMon8;

    @FXML
    protected void cmMon8a() {
        deleteSchedule(listaRez.get(0), mon8, cmMon8,0);
    }

    @FXML
    Label tue8;
    @FXML
    MenuItem cmTue8;

    @FXML
    protected void cmTue8a() {
        deleteSchedule(listaRez.get(5), tue8, cmTue8,5);
    }

    @FXML
    Label wed8;
    @FXML
    MenuItem cmWed8;

    @FXML
    protected void cmWed8a() {

    }


}
