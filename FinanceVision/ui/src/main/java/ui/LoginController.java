package ui;

import java.io.IOException;
import java.util.List;

import core.User;
import fileSaving.JsonFileSaving;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController extends AbstractController{

    @FXML
    private Button loginButton, registerUserButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private List<User> users;

    @FXML
    private void initialize() throws IOException {
        //users = FileSaving.readFromFile("data.txt");
        users = JsonFileSaving.deserializeUsers("data.json");
        loginButton.setFocusTraversable(false);
        registerUserButton.setFocusTraversable(false);
    }

    @FXML
    void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                System.out.println("Login successful");
                switchScene("App.fxml", user);
                return;
            }
        }
        notify("Invalid username or password", AlertType.WARNING);

    }

    

    @FXML
    void handleRegisterUser() throws IOException{
        switchScene("registerNewUser.fxml");
    }

   
}
