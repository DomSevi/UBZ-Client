package com.client.controllers;

import com.client.data.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SubEmpController {

    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.subEmpController = this;
        // Inicjalizacja kolumn
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameColumn.setMinWidth(50);
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameColumn.setMinWidth(50);
        // Stworzenie listy filtrowanej
        FilteredList<Person> filteredData = new FilteredList<>(masterData, p -> true);

        // Ustawienie sluchacza(?) na pole tekstowe oraz ustalenie jak maja byc filtrowane dane
        searchFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(person -> {
            // Jezeli pole tekstowe jest puste to wyswietl wszystko
            if(newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Porownanie imienia i nazwiska z polem tekstowym
            String lowerCaseFilter = newValue.toLowerCase();
            if(person.getFirstName().toLowerCase().contains(lowerCaseFilter))
                return true;
            else if(person.getLastName().toLowerCase().contains(lowerCaseFilter))
                return true;

            return false;
        }));

        // Utworznie listy sortowanej
        SortedList<Person> sortedList = new SortedList<>(filteredData);
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
    private void setSelected(Person p) {
        StringBuilder sb = new StringBuilder("Pan");
        if(p.getGender() == Person.Gender.female)
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
    TableView<Person> table;
    @FXML
    TableColumn<Person, String> firstNameColumn;
    @FXML
    TableColumn<Person, String> lastNameColumn;
    private ObservableList<Person> masterData = FXCollections.observableArrayList();

    public SubEmpController() {
        masterData.add(new Person("Dominik", "Sobieraj",Person.Gender.male));
        masterData.add(new Person("Piotr", "Nowak", Person.Gender.male));
        masterData.add(new Person("Patryk", "Wow", Person.Gender.male));
        masterData.add(new Person("Anna", "Strzelec", Person.Gender.female));
    }



}
