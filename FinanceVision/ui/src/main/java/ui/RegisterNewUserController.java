package ui;

import core.Account;
import core.User;
import java.io.IOException;
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

    @Override
    public void init() {
    }
  

    @FXML
    void handleCreateUser() throws Exception {
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
        if (modelAccess.containsUser(username)) {
            notify("Username is taken", AlertType.WARNING);
            return;
        }

        try {
            modelAccess.putUser(
                new User(username, password, fullName, email, new Account(balance)));
        } catch (Exception e) {
            notify(e.getLocalizedMessage(), AlertType.WARNING);
            return;
        }

        switchScene("login.fxml");
    }

    @FXML
    void handleBack() throws IOException {
        switchScene("login.fxml");
    }


}