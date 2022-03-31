package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.util.Collections;
import java.util.Objects;

public class LoginController {
    @FXML   // Ustawienie podstawowych stylów dla całej sceny
    protected void initialize(){
        ScreenController.applyStyle("login.css");
    }

    @FXML
    Label topLabel;
    @FXML
    Label errorLabel;
    @FXML
    TextField login;
    @FXML
    PasswordField password;
    @FXML
    TextField passwordAsText;
    @FXML
    Button loginButton;

    @FXML   // Metoda dla logowania się do systemu
    protected void login(){
        // Jeżeli jest wciśniete odsłonięcie hasła, użyj odsłonietego pola
        if(showPasswordButton.isSelected())
            password.setText(passwordAsText.getText());

        // Jeżeli pola są puste, ustaw error msg i zmień ich border
        if (login.getText().isBlank() || password.getText().isBlank()) {
            errorLabel.setText("Wypełnij wszystkie pola!");
            errorLabel.setVisible(true);
            if (login.getText().isBlank())
                login.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
            else
                login.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
            if (password.getText().isBlank())
                if(showPasswordButton.isSelected())
                    passwordAsText.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10),  BorderWidths.DEFAULT)));
                else
                    password.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10),  BorderWidths.DEFAULT)));
            else
                if(showPasswordButton.isSelected())
                    passwordAsText.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
                else
                    password.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
        }

        // Pola są wypełnione
        else {
            // Poprawne logowanie
            if (login.getText().equals("root") && password.getText().equals("root")) {
                errorLabel.setVisible(false);
                login.clear();
                showPasswordButton.setSelected(false);
                autoLoginButton.setSelected(false);
                ScreenController.activate("mainMenu");
            }

            // Blędne logowanie
            else {
                errorLabel.setText("Błędny login lub hasło!");
                errorLabel.setVisible(true);
            }

            // Czyszczenie sceny
            login.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
            password.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
            password.clear();
            passwordAsText.clear();
        }
    }


    @FXML   // Do odsłaniania hasła
    ImageView showPasswordImg;
    @FXML
    ToggleButton showPasswordButton;
    @FXML
    protected void showPassword() {
        if(showPasswordButton.isSelected()) {
            showPasswordImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/eyeB.png")).toString())));
            passwordAsText.setText(password.getText());
            password.setVisible(false);
            passwordAsText.setVisible(true);
        }
        else {
            showPasswordImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/eyeW.png")).toString())));
            password.setText(passwordAsText.getText());
            password.setVisible(true);
            passwordAsText.setVisible(false);
        }
    }


    @FXML   // Do pamietania automatycznego logowania
    ImageView autoLoginImg;
    @FXML
    ToggleButton autoLoginButton;
    @FXML
    protected void autoLogin() {
        if(autoLoginButton.isSelected())
            autoLoginImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/tickYes.png")).toString())));
        else
            autoLoginImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/tickNo.png")).toString())));
    }

    //---------------------------------------------------
    @FXML
    Button mainMenuLogout;

    @FXML
    protected void logout(){
        ScreenController.activate("login");

    }

}
