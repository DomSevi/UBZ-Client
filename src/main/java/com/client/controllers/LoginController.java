package com.client.controllers;

import com.client.CurrentSession;
import com.client.DataGetter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.*;


public class LoginController {
    private final static String AUTOLOGIN_PATH = "autoLogin.txt";


    @FXML   // Na starcie programu
    protected void initialize() {
        File file = new File(AUTOLOGIN_PATH);
        if (file.exists() && file.isFile()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                if (br.ready()) {
                    if (br.readLine().equals("1")) {
                        login.setText(br.readLine());
                        rememberLoginButton.setSelected(true);
                        rememberLoginImgN.setVisible(false);
                        rememberLoginImgY.setVisible(true);
                        password.requestFocus();
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Czyszczenie strony
    protected void clearScene() {
        errorLabel.setVisible(false);

        showPasswordButton.setSelected(false);
        AppController.loginController.showPassword();

        if (!rememberLoginButton.isSelected())
            login.clear();
        password.clear();
        passwordAsText.clear();
    }

    // Koloruje ramki danych logowania kiedy sa one puste
    private void setCredentialsBorders() {
        if (login.getText().isBlank())
            login.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        else
            login.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));

        if (showPasswordButton.isSelected())
            if (passwordAsText.getText().isBlank())
                passwordAsText.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
            else
                passwordAsText.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
        else if (password.getText().isBlank())
            password.setBorder(new Border(new BorderStroke(Color.valueOf("#ED8A77"), BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        else
            password.setBorder(new Border((new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT))));
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

                // Automatyczne logowanie
                File file = new File(AUTOLOGIN_PATH);
                // Jezeli plik istnieje
                if (file.exists() && file.isFile()) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                        if (rememberLoginButton.isSelected()) {
                            bw.write("1");
                            bw.newLine();
                            bw.write(login.getText());
                        } else
                            bw.write("0");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Jezeli plik nie istnieje oraz jest wcisniety przycisk automatycznego logowania
                else {
                    try {
                        if (rememberLoginButton.isSelected() && file.createNewFile()) {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                                bw.write("1");
                                bw.newLine();
                                bw.write(login.getText());
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Czyszczenie
                if (CurrentSession.getUserName() != null) {
                    HomeController.homeWelcomeController.clear();
                    HomeController.homeRoomsController.clear();
                    HomeController.homeEmpController.clear();
                }
                CurrentSession.setUserName(login.getText());

                AppController.homeController.setLoggedInUser();
                AppController.activateScene("home");
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

    @FXML   // Do odslaniania hasla
    ImageView showPasswordImgC;
    @FXML
    ImageView showPasswordImgO;
    @FXML
    ToggleButton showPasswordButton;

    @FXML
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
    ImageView rememberLoginImgN;
    @FXML
    ImageView rememberLoginImgY;
    @FXML
    ToggleButton rememberLoginButton;

    @FXML
    protected void rememberLogin() {
        if (rememberLoginButton.isSelected()) {
            rememberLoginImgN.setVisible(false);
            rememberLoginImgY.setVisible(true);
        } else {
            rememberLoginImgN.setVisible(true);
            rememberLoginImgY.setVisible(false);
        }

    }

    @FXML   // Wyjscie z programu
    Button exitButton;

    @FXML
    protected void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
