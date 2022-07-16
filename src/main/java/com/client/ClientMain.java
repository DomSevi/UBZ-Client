package com.client;

import com.client.conn.credentials.Credentials;
import com.client.conn.credentials.CredentialsConv;
import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.reservation.ReservationConv;
import com.client.conn.room.Room;
import com.client.conn.room.RoomConv;
import com.client.controllers.AppController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

/*
 * ClientMain ustala tylko najwazniejsze opcje glownego okna,
 * oraz importuje pare zasobow takich jak czcionki czy ikony.
 *
 * AppController wczytuje wszyskie sceny, zawiera controllery oraz pola potrzebne do pracy programu,
 * pozwala on rowniez na zmiane scen oraz wywolywanie potrzebnych metod z konkretnych controllerow
 */

public class ClientMain extends Application {

    public static Stage mainStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        // Zapewnienie dostepu do glownego okna innym klasom
        mainStage = stage;
        // Ustawianie najważniejszych opcji okna
        mainStage.setTitle("XYZ");
        mainStage.setMinWidth(1280);
        mainStage.setMinHeight(740);
        mainStage.initStyle(StageStyle.DECORATED);

        // Importowanie czesci zasobow
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/appicon.png")).toString()));
        Font.loadFont(getClass().getResourceAsStream("fonts/Lato-Regular.ttf"), 14);

        // Ustalenie glownej sceny i jej pokazanie
        mainStage.setScene(AppController.mainScene);
        mainStage.show();

        // testy i debugowanie

        // problem z data (dodawanie rezerwacji - nierozwiazany)

        EmployeeConv employeeConv = new EmployeeConv();
        CredentialsConv credentialsConv = new CredentialsConv();
        ReservationConv reservationConv = new ReservationConv();
        RoomConv roomConv = new RoomConv();

        List<Employee> employees;
        List<Credentials> credentials;
        List<Reservation> reservations;
        List<Room> rooms;

        Employee e;
        Credentials c;
        Reservation re;
        Room r;
        Boolean correct;

        try {
            /* jesli uzywamy get***By*** (metode ktora zwraca pojedynczy obiekt, trzeba sprawdzic
            czy nie jest on nullem bo moze go nie byc w bazie / NullPointerEx
            e = employeeConv.getEmployeeByLogin("ghost");
            if(e == null){
                // wypisz blad
            }
            else {
                // jest ok
            }
            */
            r = roomConv.getRoomByNr(1L);
            System.out.println(r);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}