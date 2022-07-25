package com.client.controllers;

import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.data.EmployeeClient;
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

public class HomeEmpController {

    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeEmpController = this;

        // Inicjalizacja kolumn tabeli
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setMinWidth(50);
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setMinWidth(50);
        jobColumn.setCellValueFactory(cellData -> cellData.getValue().jobProperty());
        jobColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jobColumn.setMinWidth(50);

        // Stworzenie listy filtrowanej
        FilteredList<EmployeeClient> filteredData = new FilteredList<>(masterData, p -> true);

        // Ustawienie sluchacza(?) na pole tekstowe oraz ustalenie jak maja byc filtrowane dane
        searchFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(employeeClient -> {
            // Jezeli pole tekstowe jest puste to wyswietl wszystko
            if(newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Porownanie imienia i nazwiska z polem tekstowym
            String lowerCaseFilter = newValue.toLowerCase();
            if(employeeClient.getFirstName().toLowerCase().contains(lowerCaseFilter))
                return true;
            else if(employeeClient.getLastName().toLowerCase().contains(lowerCaseFilter))
                return true;

            return false;
        }));

        // Utworznie listy sortowanej
        SortedList<EmployeeClient> sortedList = new SortedList<>(filteredData);
        // Zbindowanie tableview razem z sortowana lista
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

    }

    @FXML
    Label heading;
    private void setSelected(EmployeeClient p) {
        StringBuilder sb = new StringBuilder("Pan");
        if(p.getGender() == EmployeeClient.Gender.female)
            sb.append("i");
        sb.append(" ");
        sb.append(p.getFirstName());
        sb.append(" ");
        sb.append(p.getLastName());
        heading.setText(sb.toString());
        job.setText(p.getJob());
        login.setText(p.getLogin());
        if(p.getGender().equals(EmployeeClient.Gender.male))
            gender.setText("Mężczyzna");
        else
            gender.setText("Kobieta");
        serialNumber.setText(p.getSerialNumber());
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
    Button delEmpButton;

    @FXML
    protected void delEmployee() {
        if(table.getSelectionModel().getSelectedItem() != null) {
            EmployeeClient e = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Usunąć " + e.getFirstName() +
                    " " + e.getLastName() + " ?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Potwierdzenie");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                masterData.remove(table.getSelectionModel().getSelectedItem());
                heading.setText("Wybierz osobę z listy");
                heading.setTextAlignment(TextAlignment.CENTER);
            }
        }
    }

    public static void refreshTable() {
        masterData.removeAll(masterData);
        EmployeeConv employeeConv = new EmployeeConv();
        try {
            List<Employee> employees = employeeConv.getAllEmployees();
            for (Employee e: employees) {
                EmployeeClient.Gender g;
                g = ((e.getMale()) ? EmployeeClient.Gender.male : EmployeeClient.Gender.female);
                masterData.add(new EmployeeClient(e.getId(), e.getName(), e.getSurname(), e.getJob(), g, e.getLogin(), e.getSerialNumber()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    TableView<EmployeeClient> table;
    @FXML
    TableColumn<EmployeeClient, String> firstNameColumn;
    @FXML
    TableColumn<EmployeeClient, String> lastNameColumn;
    @FXML
    TableColumn<EmployeeClient, String> jobColumn;
    private static ObservableList<EmployeeClient> masterData = FXCollections.observableArrayList();

    private void updateEmp() {
        EmployeeConv ec = new EmployeeConv();
        try {
            EmployeeClient e = table.getSelectionModel().getSelectedItem();
            ec.updateEmployeeById(e.getId(),e.getFirstName(),e.getLastName(),e.getJob());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
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
    }

    public HomeEmpController() {
        refreshTable();
    }

    @FXML
    Label job;
    @FXML
    Label gender;
    @FXML
    Label login;
    @FXML
    Label serialNumber;

    @FXML
    ImageView padlockC;
    @FXML
    ImageView padlockO;

    @FXML
    Button lockButton;
    @FXML
    protected void lock() {
        login.setVisible(!login.isVisible());
        serialNumber.setVisible(!serialNumber.isVisible());
        padlockC.setVisible(!padlockC.isVisible());
        padlockO.setVisible(!padlockO.isVisible());
    }


}

