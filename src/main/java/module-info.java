module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okio;
    requires java.sql;


    opens com.client.conn.employee;
    exports com.client.conn.employee;
    opens com.client.conn.reservation;
    exports com.client.conn.reservation;
    opens com.client.conn.credentials;
    exports com.client.conn.credentials;
    opens com.client.conn.room;
    exports com.client.conn.room;


    opens com.client to javafx.fxml;
    exports com.client;
    exports com.client.controllers;
    opens com.client.controllers to javafx.fxml;
}