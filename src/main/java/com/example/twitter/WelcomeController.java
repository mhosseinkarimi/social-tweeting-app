package com.example.twitter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.authentication.Authentication.*;

import java.io.IOException;

public class WelcomeController {
    public WelcomeController(){}

    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label wrongInput;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox rememberMe;

    private void userSignIn() {
        boolean loginFlag = logIn(username, password);
        if(loginFlag) {
            wrongInput.setText("Success!");
        }
        else{
            wrongInput.setText("Something went wrong!");
        }
    }

    private void userSignUp() throws IOException {
        Main m = new Main();
        m.changeScene("Sign Up.fxml");
        private TextField name;
        private TextField lastname;
        private TextField username;
        private TextField birthdate;
        private TextField password;
        private TextField repeatPassword;
        private TextField biography;
        signUp(name, lastname, username, dateOfBirth, password, biography);
        m.changeScene("welcome.fxml");
    }
    private void checkRememberMe(){

    }

}
