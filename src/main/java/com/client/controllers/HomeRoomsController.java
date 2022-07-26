package com.client.controllers;

import com.client.conn.credentials.CredentialsConv;
import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import com.client.data.EmployeeClient;
import com.client.data.RoomClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.List;

public class HomeRoomsController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeRoomsController = this;

        // Inicjalizacja kolumn tabeli
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        roomIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomIdColumn.setMinWidth(50);
        roomLevelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        roomLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomLevelColumn.setMinWidth(75);
        roomCapColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty());
        roomCapColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomCapColumn.setMinWidth(75);
        roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        roomTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomTypeColumn.setMinWidth(270);

        // Stworzenie listy filtrowanej
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

        // event dla przycisku myszy nad tabelą
        table.setOnMousePressed(e ->{
            if(e.getClickCount() == 1 && e.isPrimaryButtonDown()) {
                if(table.getSelectionModel().getSelectedItem() != null)
                    setSelected(table.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void clear() {
        roomId.setText("-");
        level.setText("-");
        cap.setText("-");
        monday.setText("");
        tuesday.setText("");
        wednesday.setText("");
        thursday.setText("");
        friday.setText("");
    }

    @FXML
    Label heading;

    private void setSelected(RoomClient p) {
        StringBuilder sb = new StringBuilder(p.getType());
        heading.setText(sb.toString());
        roomId.setText(p.getId());
        cap.setText(p.getCapacity());
        level.setText(p.getLevel());

        RoomConv ec = new RoomConv();
        try {
            Room e = ec.getRoomByNr(Long.parseLong(p.getId()));
            List<Reservation> list = e.getReservations();
            StringBuilder mon = new StringBuilder();
            StringBuilder tue = new StringBuilder();
            StringBuilder wed = new StringBuilder();
            StringBuilder thu = new StringBuilder();
            StringBuilder fri = new StringBuilder();
            for (Reservation res: list) {
                switch(res.getDay().intValue()) {
                    case 0:
                        mon.append(res.getHour() + ":00 - " + (res.getHour()+2) + ":00\n");
                        break;
                    case 1:
                        tue.append(res.getHour() + ":00 - " + (res.getHour()+2) + ":00\n");
                        break;
                    case 2:
                        wed.append(res.getHour() + ":00 - " + (res.getHour()+2) + ":00\n");
                        break;
                    case 3:
                        thu.append(res.getHour() + ":00 - " + (res.getHour()+2) + ":00\n");
                        break;
                    case 4:
                        fri.append(res.getHour() + ":00 - " + (res.getHour()+2) + ":00\n");
                        break;
                }
            }
            monday.setText(mon.toString());
            tuesday.setText(tue.toString());
            wednesday.setText(wed.toString());
            thursday.setText(thu.toString());
            friday.setText(fri.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    TextField searchFilter;
    @FXML
    protected void resetFilter() {
        if(!searchFilter.getText().isEmpty())
            searchFilter.setText("");
    }

    @FXML
    Button refreshButton;
    @FXML
    protected void refresh() {
        refreshTable();
    }

    @FXML
    Button delRoomButton;

    @FXML
    protected void delEmployee() {
        if(table.getSelectionModel().getSelectedItem() != null) {
            RoomClient e = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Usunąć " + e.getType() +
                    " o numerze " + e.getId() + " ?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Potwierdzenie");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                heading.setText("Wybierz pokój z listy");
                heading.setTextAlignment(TextAlignment.CENTER);
                try {
                    RoomConv ec = new RoomConv();
                    ec.removeRoomByNr(Long.parseLong(e.getId()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                masterData.remove(table.getSelectionModel().getSelectedItem());
                clear();
            }
        }
    }

    public static void refreshTable() {
        masterData.removeAll(masterData);
        RoomConv rc = new RoomConv();
        try {
            List<Room> rooms = rc.getAllRooms();
            for (Room r : rooms) {
                List<Reservation> l = r.getReservations();
                masterData.add(new RoomClient(r.getRoomNr().toString(), r.getLevel().toString(), r.getCapacity().toString(), r.getType()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /*private void updateEmp() {
        EmployeeConv ec = new EmployeeConv();
        try {
            EmployeeClient e = table.getSelectionModel().getSelectedItem();
            ec.updateEmployeeById(e.getId(),e.getFirstName(),e.getLastName(),e.getJob());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/

    /*@FXML
    public void editFirstName(TableColumn.CellEditEvent<?,?> cEE) {
        int index = table.getSelectionModel().getSelectedIndex();
        masterData.get(index).setFirstName((String) cEE.getNewValue());
        updateEmp();
    }

    @FXML
    public void editLastName(TableColumn.CellEditEvent<?,?> cEE) {
        int index = table.getSelectionModel().getSelectedIndex();
        masterData.get(index).setLastName((String) cEE.getNewValue());
        updateEmp();
    }

    @FXML
    public void editJob(TableColumn.CellEditEvent<?,?> cEE) {
        int index = table.getSelectionModel().getSelectedIndex();
        masterData.get(index).setJob((String) cEE.getNewValue());
        updateEmp();
    }*/

    public HomeRoomsController() {
        refreshTable();
    }

    @FXML
    Label roomId;
    @FXML
    Label level;
    @FXML
    Label cap;

    @FXML
    Label monday;
    @FXML
    Label tuesday;
    @FXML
    Label wednesday;
    @FXML
    Label thursday;
    @FXML
    Label friday;

    @FXML
    Button scheduleButton;
    @FXML
    protected void schedule() {
        if(table.getSelectionModel().getSelectedItem() != null) {
            Long scheduleId = Long.parseLong(table.getSelectionModel().getSelectedItem().getId());
            String scheduleType = table.getSelectionModel().getSelectedItem().getType();
            Long scheduleLevel = Long.parseLong(table.getSelectionModel().getSelectedItem().getLevel());

            AppController.scheduleController.clear();
            AppController.scheduleController.setSchedule(scheduleId,scheduleType,scheduleLevel);
            AppController.activateScene("schedule");
        }

    }


}
