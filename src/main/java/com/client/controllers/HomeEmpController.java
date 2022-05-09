package com.client.controllers;

import com.client.data.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
        FilteredList<Employee> filteredData = new FilteredList<>(masterData, p -> true);

        // Ustawienie sluchacza(?) na pole tekstowe oraz ustalenie jak maja byc filtrowane dane
        searchFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(employee -> {
            // Jezeli pole tekstowe jest puste to wyswietl wszystko
            if(newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Porownanie imienia i nazwiska z polem tekstowym
            String lowerCaseFilter = newValue.toLowerCase();
            if(employee.getFirstName().toLowerCase().contains(lowerCaseFilter))
                return true;
            else if(employee.getLastName().toLowerCase().contains(lowerCaseFilter))
                return true;

            return false;
        }));

        // Utworznie listy sortowanej
        SortedList<Employee> sortedList = new SortedList<>(filteredData);
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
    private void setSelected(Employee p) {
        StringBuilder sb = new StringBuilder("Pan");
        if(p.getGender() == Employee.Gender.female)
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
    TableView<Employee> table;
    @FXML
    TableColumn<Employee, String> firstNameColumn;
    @FXML
    TableColumn<Employee, String> lastNameColumn;
    private ObservableList<Employee> masterData = FXCollections.observableArrayList();

    public HomeEmpController() {
        masterData.add(new Employee("Dominik", "Sobieraj", Employee.Gender.male));
        masterData.add(new Employee("Piotr", "Nowak", Employee.Gender.male));
        masterData.add(new Employee("Patryk", "Wow", Employee.Gender.male));
        masterData.add(new Employee("Anna", "Strzelec", Employee.Gender.female));
    }



}
