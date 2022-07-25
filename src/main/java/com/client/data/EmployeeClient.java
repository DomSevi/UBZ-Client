package com.client.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeClient {
    public enum Gender {female, male}
    private final long id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty job;
    private final Gender gender;
    private final String login;
    private final String serialNumber;



    public EmployeeClient(long id, String firstName, String lastName, String job, Gender gender, String login,  String serialNumber) {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.job = new SimpleStringProperty(job);
        this.gender = gender;
        this.login = login;
        this.serialNumber = serialNumber;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getJob() {
        return job.get();
    }

    public StringProperty jobProperty() {
        return job;
    }

    public void setJob(String job) {
        this.lastName.set(job);
    }

    public Gender getGender() {
        return gender;
    }
}
