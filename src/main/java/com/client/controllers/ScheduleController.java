package com.client.controllers;

import com.client.CurrentSession;
import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.reservation.ReservationConv;
import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import com.client.data.RoomClient;
import javafx.beans.property.SimpleStringProperty;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        errorLabel.setVisible(false);
        mon8.setVisible(false);
        mon10.setVisible(false);
        mon12.setVisible(false);
        mon14.setVisible(false);
        mon16.setVisible(false);
        tue8.setVisible(false);
        tue10.setVisible(false);
        tue12.setVisible(false);
        tue14.setVisible(false);
        tue16.setVisible(false);
        wed8.setVisible(false);
        wed10.setVisible(false);
        wed12.setVisible(false);
        wed14.setVisible(false);
        wed16.setVisible(false);
        thu8.setVisible(false);
        thu10.setVisible(false);
        thu12.setVisible(false);
        thu14.setVisible(false);
        thu16.setVisible(false);
        fri8.setVisible(false);
        fri10.setVisible(false);
        fri12.setVisible(false);
        fri14.setVisible(false);
        fri16.setVisible(false);

    }

    public static void refreshTable() {
        masterData.removeAll(masterData);
        RoomConv rc = new RoomConv();
        try {
            List<Room> rooms = rc.getAllRooms();
            for (Room r : rooms) {
                masterData.add(new RoomClient(r.getRoomNr().toString(), r.getLevel().toString(), r.getCapacity().toString(), r.getType()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String login;
    private String name;
    private String surname;
    private List<Integer> listaRez = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    // Funkcja wywoływana gdy wchodzimy na strone rezerwacji
    public void setSchedule(String login, String name, String surname) {
        // jak sie nie jest adminem to wychodzi true
        addReservationButton.setDisable(!CurrentSession.isIsAdmin() && !login.equals(CurrentSession.getUserName()));
        this.login = login;
        this.name = name;
        this.surname = surname;
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
        addReservationButton.setDisable(true);
        topLabel.setText("Szczegółowy plan dla " + type + " na piętrze " + level);
        try {
            RoomConv rc = new RoomConv();
            Room r = rc.getRoomByNr(id);
            List<Reservation> lista = r.getReservations();
            fillSchedule(lista, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DAY - 1 - monday, ...
    // HOUr 8 ...
    // Dla kazdej rezerwacji wywoluje funkcji
    private void fillSchedule(List<Reservation> lista, boolean isPerson) {
        for (Reservation r : lista) {
            Long day = r.getDay();
            Long hour = r.getHour();
            Long id;
            if (isPerson)
                id = r.getRoomId();
            else
                id = r.getEmpId();
            fillSingle(day.intValue(), hour.intValue(), id, isPerson, r.getReservationId().intValue());
        }
    }

    // jezeli isPerson = true, id - id pokoju, isPerson = false, id - id osoby
    private void fillSingle(int day, int hour, Long id, boolean isPerson, int resId) {
        if (day == 0) {
            if (hour == 8) {
                listaRez.set(0, resId);
                styleSelected(mon8, id, isPerson,cmMon8);
            }
            if (hour == 10) {
                listaRez.set(1, resId);
                styleSelected(mon10, id, isPerson,cmMon10);
            }
            if (hour == 12) {
                listaRez.set(2, resId);
                styleSelected(mon12, id, isPerson,cmMon12);
            }
            if (hour == 14) {
                listaRez.set(3, resId);
                styleSelected(mon14, id, isPerson,cmMon14);
            }
            if (hour == 16) {
                listaRez.set(4, resId);
                styleSelected(mon16, id, isPerson,cmMon16);
            }
        } else if (day == 1) {
            if (hour == 8) {
                listaRez.set(5, resId);
                styleSelected(tue8, id, isPerson,cmTue8);
            }
            if (hour == 10) {
                listaRez.set(6, resId);
                styleSelected(tue10, id, isPerson,cmTue10);
            }
            if (hour == 12) {
                listaRez.set(7, resId);
                styleSelected(tue12, id, isPerson,cmTue12);
            }
            if (hour == 14) {
                listaRez.set(8, resId);
                styleSelected(tue14, id, isPerson,cmTue14);
            }
            if (hour == 16) {
                listaRez.set(9, resId);
                styleSelected(tue16, id, isPerson,cmTue16);
            }
        } else if (day == 2) {
            if (hour == 8) {
                listaRez.set(10, resId);
                styleSelected(wed8, id, isPerson,cmWed8);
            }
            if (hour == 10) {
                listaRez.set(11, resId);
                styleSelected(wed10, id, isPerson,cmWed10);
            }
            if (hour == 12) {
                listaRez.set(12, resId);
                styleSelected(wed12, id, isPerson,cmWed12);
            }
            if (hour == 14) {
                listaRez.set(13, resId);
                styleSelected(wed14, id, isPerson,cmWed14);
            }
            if (hour == 16) {
                listaRez.set(14, resId);
                styleSelected(wed16, id, isPerson,cmWed16);
            }
        } else if (day == 3) {
            if(hour == 8) {
                listaRez.set(15, resId);
                styleSelected(thu8, id, isPerson, cmThu8);
            }
            if(hour == 10) {
                listaRez.set(16, resId);
                styleSelected(thu10, id, isPerson, cmThu10);
            }
            if(hour == 12) {
                listaRez.set(17, resId);
                styleSelected(thu12, id, isPerson, cmThu12);
            }
            if(hour == 14) {
                listaRez.set(18, resId);
                styleSelected(thu14, id, isPerson, cmThu14);
            }
            if(hour == 16) {
                listaRez.set(19, resId);
                styleSelected(thu16, id, isPerson, cmThu16);
            }
        } else if (day == 4) {
            if(hour == 8) {
                listaRez.set(20, resId);
                styleSelected(fri8, id, isPerson, cmFri8);
            }
            if(hour == 10) {
                listaRez.set(21, resId);
                styleSelected(fri10, id, isPerson, cmFri10);
            }
            if(hour == 12) {
                listaRez.set(22, resId);
                styleSelected(fri12, id, isPerson, cmFri12);
            }
            if(hour == 14) {
                listaRez.set(23, resId);
                styleSelected(fri14, id, isPerson, cmFri14);
            }
            if(hour == 16) {
                listaRez.set(24, resId);
                styleSelected(fri16, id, isPerson, cmFri16);
            }
        }
    }

    // orange -fx-background-color:  linear-gradient(to bottom, #ebd834, #eddf13);  3
    // blue -fx-background-color:  linear-gradient(to bottom, #489ff0, #4872f0);    1
    // cyean -fx-background-color:  linear-gradient(to bottom, #48cfd9, #28e9f7);    0
    // yellolw -fx-background-color:  linear-gradient(to bottom, #80d945, #7cf52c);    2
    private void styleSelected(Label l, Long id, boolean isPerson, MenuItem mi) {
        if (isPerson) {
            mi.setVisible(true);
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
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #ebd834, #eddf13);");
                else if (level == 3)
                    l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #c45c47, #e84220);");
                l.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            mi.setVisible(false);
            EmployeeConv ec = new EmployeeConv();
            List<Employee> eList = null;
            try {
                eList = ec.getAllEmployees();
                for (Employee e: eList) {
                    if(e.getId().equals(id)) {
                        int level = 0;
                        if(e.getJob().equals("Magister"))
                            level = 1;
                        else if (e.getJob().equals("Doktor"))
                            level = 2;
                        else if (e.getJob().equals("Profesor"))
                            level = 3;
                        l.setText(e.getName() + " " + e.getSurname());
                        if (level == 0)
                            l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #48cfd9, #28e9f7);");
                        else if (level == 1)
                            l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #489ff0, #4872f0);");
                        else if (level == 2)
                            l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #ebd834, #eddf13);");
                        else if (level == 3)
                            l.setStyle("-fx-font-size: 20px;-fx-background-radius: 10;-fx-background-color:  linear-gradient(to bottom, #c45c47, #e84220);");
                        l.setVisible(true);
                        return;

                    }
                }
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
        HomeController.homeEmpController.clear();
        HomeController.homeRoomsController.clear();
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
            addResExit.setVisible(true);
            addRes.setVisible(false);
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
            hourChoicebox.getSelectionModel().clearSelection();
            dayChoicebox.getSelectionModel().clearSelection();
            addResExit.setVisible(false);
            addRes.setVisible(true);

        }
    }

    @FXML
    ImageView addRes;
    @FXML
    ImageView addResExit;

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
                Long myDay = (long) dayChoicebox.getSelectionModel().getSelectedIndex();
                Long myHour = Long.parseLong(getHour());
                for (Reservation res: roomList) {
                    if(Objects.equals(res.getDay(), myDay) && Objects.equals(res.getHour(), myHour)) {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Wybrana godzina jest już zajęta!");
                        return;
                    }
                }
                ReservationConv resC = new ReservationConv();
                EmployeeConv ec = new EmployeeConv();
                Employee e = ec.getEmployeeByLogin(login);
                resC.createNewReservation(new Reservation(myDay,myHour,r.getRoomNr(),e.getId()));


                errorLabel.setVisible(true);
                errorLabel.setText("Dodano pomyślnie!");

                setSchedule(login,name,surname);
                hourChoicebox.getSelectionModel().clearSelection();
                dayChoicebox.getSelectionModel().clearSelection();
                searchFilter.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String getHour() {
        if(hourChoicebox.getSelectionModel().getSelectedIndex() == 0)
            return "8";
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
        if(resId == 0)
            return;
        try {
            ReservationConv rc = new ReservationConv();
            rc.removeReservationByid((long) resId);
            l.setVisible(false);
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
    Label mon10;
    @FXML
    MenuItem cmMon10;
    @FXML
    protected void cmMon10a() {
        deleteSchedule(listaRez.get(1), mon10, cmMon10,1);
    }

    @FXML
    Label mon12;
    @FXML
    MenuItem cmMon12;
    @FXML
    protected void cmMon12a() {
        deleteSchedule(listaRez.get(2), mon12, cmMon12,2);
    }

    @FXML
    Label mon14;
    @FXML
    MenuItem cmMon14;
    @FXML
    protected void cmMon14a() {
        deleteSchedule(listaRez.get(3), mon14, cmMon14,3);
    }

    @FXML
    Label mon16;
    @FXML
    MenuItem cmMon16;
    @FXML
    protected void cmMon16a() {
        deleteSchedule(listaRez.get(4), mon16, cmMon16,4);
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
    Label tue10;
    @FXML
    MenuItem cmTue10;
    @FXML
    protected void cmTue10a() {
        deleteSchedule(listaRez.get(6), tue10, cmTue10,6);
    }

    @FXML
    Label tue12;
    @FXML
    MenuItem cmTue12;
    @FXML
    protected void cmTue12a() {
        deleteSchedule(listaRez.get(7), tue12, cmTue12,7);
    }

    @FXML
    Label tue14;
    @FXML
    MenuItem cmTue14;
    @FXML
    protected void cmTue14a() {
        deleteSchedule(listaRez.get(8), tue14, cmTue14,8);
    }

    @FXML
    Label tue16;
    @FXML
    MenuItem cmTue16;
    @FXML
    protected void cmTue16a() {
        deleteSchedule(listaRez.get(9), tue16, cmTue16,9);
    }

    @FXML
    Label wed8;
    @FXML
    MenuItem cmWed8;
    @FXML
    protected void cmWed8a() {
        deleteSchedule(listaRez.get(10),wed8,cmWed8, 10);
    }

    @FXML
    Label wed10;
    @FXML
    MenuItem cmWed10;
    @FXML
    protected void cmWed10a() {
        deleteSchedule(listaRez.get(11),wed10,cmWed10, 11);
    }

    @FXML
    Label wed12;
    @FXML
    MenuItem cmWed12;
    @FXML
    protected void cmWed12a() {
        deleteSchedule(listaRez.get(12),wed12,cmWed12, 12);
    }

    @FXML
    Label wed14;
    @FXML
    MenuItem cmWed14;
    @FXML
    protected void cmWed14a() {
        deleteSchedule(listaRez.get(13),wed14,cmWed14, 13);
    }

    @FXML
    Label wed16;
    @FXML
    MenuItem cmWed16;
    @FXML
    protected void cmWed16a() {
        deleteSchedule(listaRez.get(14),wed16,cmWed16, 14);
    }

    @FXML
    Label thu8;
    @FXML
    MenuItem cmThu8;
    @FXML
    protected void cmThu8a() {
        deleteSchedule(listaRez.get(15),thu8,cmThu8, 15);
    }

    @FXML
    Label thu10;
    @FXML
    MenuItem cmThu10;
    @FXML
    protected void cmThu10a() {
        deleteSchedule(listaRez.get(16),thu10,cmThu10, 16);
    }

    @FXML
    Label thu12;
    @FXML
    MenuItem cmThu12;
    @FXML
    protected void cmThu12a() {
        deleteSchedule(listaRez.get(17),thu12,cmThu12, 17);
    }

    @FXML
    Label thu14;
    @FXML
    MenuItem cmThu14;
    @FXML
    protected void cmThu14a() {
        deleteSchedule(listaRez.get(18),thu14,cmThu14, 18);
    }

    @FXML
    Label thu16;
    @FXML
    MenuItem cmThu16;
    @FXML
    protected void cmThu16a() {
        deleteSchedule(listaRez.get(19),thu16,cmThu16, 19);
    }

    @FXML
    Label fri8;
    @FXML
    MenuItem cmFri8;
    @FXML
    protected void cmFri8a() {
        deleteSchedule(listaRez.get(20),fri8,cmFri8, 20);
    }

    @FXML
    Label fri10;
    @FXML
    MenuItem cmFri10;
    @FXML
    protected void cmFri10a() {
        deleteSchedule(listaRez.get(21),fri10,cmFri10, 21);
    }

    @FXML
    Label fri12;
    @FXML
    MenuItem cmFri12;
    @FXML
    protected void cmFri12a() {
        deleteSchedule(listaRez.get(22),fri12,cmFri12, 22);
    }

    @FXML
    Label fri14;
    @FXML
    MenuItem cmFri14;
    @FXML
    protected void cmFri14a() {
        deleteSchedule(listaRez.get(23),fri14,cmFri14, 23);
    }

    @FXML
    Label fri16;
    @FXML
    MenuItem cmFri16;
    @FXML
    protected void cmFri16a() {
        deleteSchedule(listaRez.get(24),fri16,cmFri16, 24);
    }


}
