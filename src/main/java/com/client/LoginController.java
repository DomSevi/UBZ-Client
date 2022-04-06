package com.client;

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
                AppController.mainSceneController.currentUser();
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
    ImageView showPasswordImg;
    @FXML
    ToggleButton showPasswordButton;

    @FXML   // Do odslaniania hasla
    protected void showPassword() {
        if (showPasswordButton.isSelected()) {
            showPasswordImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/eyeB.png")).toString())));
            passwordAsText.setText(password.getText());
            password.setVisible(false);
            passwordAsText.setVisible(true);
        } else {
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
        if (autoLoginButton.isSelected())
            autoLoginImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/tickYes.png")).toString())));
        else
            autoLoginImg.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("img/tickNo.png")).toString())));
    }

}
