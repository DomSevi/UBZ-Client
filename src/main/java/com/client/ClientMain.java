package com.client;

import com.client.conn.employee.Employee;
import com.client.conn.employee.EmployeeConv;
import com.client.conn.reservation.Reservation;
import com.client.conn.reservation.ReservationConv;
import com.client.controllers.AppController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void start(Stage stage) {
        EmployeeConv employeeConv = new EmployeeConv();

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

        // some tests and debugging

        List<Employee> e;

        try {
            e = employeeConv.getAllEmployees();
            e.forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}