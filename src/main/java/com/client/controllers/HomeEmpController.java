package com.client.controllers;

import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.data.EmployeeClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class HomeEmpController {

    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeEmpController = this;
        // Inicjalizacja kolumn
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameColumn.setMinWidth(50);
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameColumn.setMinWidth(50);
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
    Label testLabel;
    private void setSelected(EmployeeClient p) {
        StringBuilder sb = new StringBuilder("Pan");
        if(p.getGender() == EmployeeClient.Gender.female)
            sb.append("i");
        sb.append(" ");
        sb.append(p.getFirstName());
        sb.append(" ");
        sb.append(p.getLastName());
        testLabel.setText(sb.toString());
    }
    @FXML
    TextField searchFilter;
    @FXML
    protected void resetFilter() {
        if(!searchFilter.getText().isEmpty())
            searchFilter.setText("");
    }
    @FXML
    TableView<EmployeeClient> table;
    @FXML
    TableColumn<EmployeeClient, String> firstNameColumn;
    @FXML
    TableColumn<EmployeeClient, String> lastNameColumn;
    private ObservableList<EmployeeClient> masterData = FXCollections.observableArrayList();

    public HomeEmpController() {
        EmployeeConv employeeConv = new EmployeeConv();
        try {
            List<Employee> employees = employeeConv.getAllEmployees();
            for (Employee e: employees
            ) {
                masterData.add(new EmployeeClient(e.getName(),e.getSurname(),EmployeeClient.Gender.female));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
