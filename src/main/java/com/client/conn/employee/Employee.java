package com.client.conn.employee;

import com.client.conn.reservation.Reservation;

import java.util.List;

public class Employee {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String job;
    private String serialNumber;

    private List<Reservation> reservations;

    public Employee() {
    }

    public Employee(Long id, String login, String name, String surname, String job, String serialNumber, List<Reservation> reservations) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.job = job;
        this.serialNumber = serialNumber;
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "EmployeeClient{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", job='" + job + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
