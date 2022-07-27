package com.client.controllers;

import com.client.conn.credentials.Credentials;
import com.client.conn.credentials.CredentialsConv;
import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class HomeAddEmpController {
    @FXML
    protected void initialize() {
        // Ustawia swoj controller w statycznym polu rodzica
        HomeController.homeAddEmpController = this;
        jobChoiceBox.getItems().removeAll(jobChoiceBox.getItems());
        jobChoiceBox.getItems().addAll("-", "Magister", "Doktor", "Profesor");
        genderChoiceBox.getItems().removeAll(genderChoiceBox.getItems());
        genderChoiceBox.getItems().addAll("Mężczyzna", "Kobieta");
        adminChoiceBox.getItems().removeAll(adminChoiceBox.getItems());
        adminChoiceBox.getItems().addAll("Normalny użytkownik", "Admin");
    }

    @FXML
    ChoiceBox<String> jobChoiceBox;
    @FXML
    ChoiceBox<String> genderChoiceBox;
    @FXML
    ChoiceBox<String> adminChoiceBox;
    @FXML
    Label errorLabel;
    @FXML
    Button acceptButton;
    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField serialNumber;
    @FXML
    TextField login;
    @FXML
    PasswordField password;

    protected void clear() {
        name.setText("");
        surname.setText("");
        serialNumber.setText("");
        login.setText("");
        password.setText("");
        jobChoiceBox.getSelectionModel().select(null);
        genderChoiceBox.getSelectionModel().select(null);
        adminChoiceBox.getSelectionModel().select(null);
        errorLabel.setVisible(false);
    }
    @FXML
    protected void addUser() {
        // jezeli pola sa puste
        if (name.getText().isEmpty() || surname.getText().isEmpty() || serialNumber.getText().isEmpty() || login.getText().isEmpty() || password.getText().isEmpty()
                || adminChoiceBox.getSelectionModel().isEmpty() || genderChoiceBox.getSelectionModel().isEmpty() || jobChoiceBox.getSelectionModel().isEmpty()) {
            errorLabel.setText("Wypełnij wszystkie pola!");
            errorLabel.setVisible(true);
        }
        // jezeli pesel ma zla dlugosc
        else if (serialNumber.getText().length() != 11) {
            errorLabel.setText("Długość numeru PESEL się nie zgadza!");
            errorLabel.setVisible(true);
        } else {
            // sprawdzenie czy podany login juz istnieje
            EmployeeConv ec = new EmployeeConv();
            CredentialsConv cc = new CredentialsConv();
            Employee emp;
            try {
                emp = ec.getEmployeeByLogin(login.getText());
                if (emp != null) {
                    errorLabel.setText("Podany login już istnieje!");
                    errorLabel.setVisible(true);
                    return;
                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            errorLabel.setVisible(false);
            boolean gender = genderChoiceBox.getSelectionModel().getSelectedItem().equals("Mężczyzna");
            Employee e = new Employee(login.getText(), name.getText(), surname.getText(), jobChoiceBox.getSelectionModel().getSelectedItem(),
                    serialNumber.getText(), gender);
            try {
                ec.createNewEmployee(e);
                Employee newEmp = ec.getEmployeeByLogin(login.getText());
                boolean admin = adminChoiceBox.getSelectionModel().getSelectedItem().equals("Admin");
                Credentials newCre = new Credentials(newEmp.getId(), newEmp.getLogin(), password.getText(),admin);
                cc.createNewCredentials(newCre);
                clear();
                errorLabel.setText("Dodano pomyślnie!");
                errorLabel.setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}

