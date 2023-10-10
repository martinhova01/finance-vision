package ui;

import core.Account;
import core.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller-class for registering a new user.
 */
public class RegisterNewUserController extends AbstractController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField balanceField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton;
    @FXML
    private Button registerUserButton;

    private List<User> users;

    @Override
    public void init() {
        try {
            users = fileHandler.deserializeUsers(new File(System.getProperty(
                "user.home") + "/data.json"));
        } catch (IOException e) {
            notify("File not found", AlertType.ERROR);
        }
    }
  

    @FXML
    void handleCreateUser() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        double balance = 0.0;
        try {
            balance = Double.parseDouble(balanceField.getText());
        } catch (Exception e) {
            notify("balance field is empty or invalid", AlertType.WARNING);
            return;
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                notify("Username is taken", AlertType.WARNING);
                return;
            }
        }

        try {
            users.add(new User(username, password, fullName, email, new Account(balance)));
        } catch (Exception e) {
            notify(e.getLocalizedMessage(), AlertType.WARNING);
            return;
        }

        fileHandler.serializeUsers(users, new File(System.getProperty(
            "user.home") + "/data.json"));

        switchScene("login.fxml");
    }

    @FXML
    void handleBack() throws IOException {
        switchScene("login.fxml");
    }


}