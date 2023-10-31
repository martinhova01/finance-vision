package ui;

import core.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller-class for the logging in.
 */
public class LoginController extends AbstractController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerUserButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    @Override
    public void init() {
        loginButton.setFocusTraversable(false);
        registerUserButton.setFocusTraversable(false);
    }

    @FXML
    void handleLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (User user : modelAccess.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful");
                switchScene("App.fxml", user);
                return;
            }
        }
        notify("Invalid username or password", AlertType.WARNING);

    }

    

    @FXML
    void handleRegisterUser() throws IOException {
        switchScene("registerNewUser.fxml");
    }
    
}
