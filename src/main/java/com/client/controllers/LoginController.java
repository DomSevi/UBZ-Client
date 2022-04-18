package com.client.controllers;

import com.client.CurrentSession;
import com.client.DataGetter;
import com.client.controllers.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    protected void clearScene() {
        errorLabel.setVisible(false);
        showPasswordButton.setSelected(false);
        AppController.loginController.showPassword();
        autoLoginButton.setSelected(false);
        AppController.loginController.autoLogin();
        login.clear();
        password.clear();
        passwordAsText.clear();
    }

    @FXML
    protected void initialize() {

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

    @FXML   // Metoda dla logowania sie do systemu
    protected void login() {
        // Jezeli jest wcisniete odslonięcie hasla, uzyj danych z odslonietego pola
        if (showPasswordButton.isSelected())
            password.setText(passwordAsText.getText());

        // Jezeli pola sa puste, ustaw error msg i zmien ich border
        if (login.getText().isBlank() || password.getText().isBlank()) {
            errorLabel.setText("Wypełnij wszystkie pola!");
            errorLabel.setVisible(true);
            setCredentialsBorders();
        }

        // Pola sa wypelnione
        else {
            // Poprawne logowanie
            if (DataGetter.checkCredentials(login.getText(), password.getText())) {

                // ### Zrobienie automatycznego logowania
                CurrentSession.setUser(login.getText());
                AppController.homeContoller.setLoggedInUser();
                AppController.activateScene("home");
                AppController.loginController.clearScene();
            }

            // Bledne logowanie
            else {
                errorLabel.setText("Błędny login lub hasło!");
                errorLabel.setVisible(true);
                password.clear();
                passwordAsText.clear();
            }

            // Czyszczenie ramek bledu
            login.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
            password.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
        }

    }

    // Koloruje ramki danych logowania kiedy sa one puste
    private void setCredentialsBorders() {
        if (login.getText().isBlank())
            login.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        else
            login.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));

        if (password.getText().isBlank())
            if (showPasswordButton.isSelected())
                passwordAsText.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
            else
                password.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        else if (showPasswordButton.isSelected())
            passwordAsText.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
        else
            password.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
    }

    @FXML
    Button exitButton;

    @FXML   // Wyjscie z programu
    protected void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    ImageView showPasswordImgC;
    @FXML
    ImageView showPasswordImgO;
    @FXML
    ToggleButton showPasswordButton;

    @FXML   // Do odslaniania hasla
    protected void showPassword() {
        if (showPasswordButton.isSelected()) {
            showPasswordImgC.setVisible(false);
            showPasswordImgO.setVisible(true);
            passwordAsText.setText(password.getText());
            password.setVisible(false);
            passwordAsText.setVisible(true);
        } else {
            showPasswordImgC.setVisible(true);
            showPasswordImgO.setVisible(false);
            password.setText(passwordAsText.getText());
            password.setVisible(true);
            passwordAsText.setVisible(false);
        }
    }


    @FXML   // Do pamietania automatycznego logowania
    ImageView autoLoginImgN;
    @FXML
    ImageView autoLoginImgY;
    @FXML
    ToggleButton autoLoginButton;

    @FXML
    protected void autoLogin() {
        if (autoLoginButton.isSelected()) {
            autoLoginImgN.setVisible(false);
            autoLoginImgY.setVisible(true);
        }
        else {
            autoLoginImgN.setVisible(true);
            autoLoginImgY.setVisible(false);
        }

    }

}
