package ui;

import core.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for user settings.
 */
public class UserSettingsController extends AbstractController {

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
    private Button editUserButton;
    @FXML
    private Button confirmButton;

    /**
     * Loads in the user to update.
     */
    @Override
    public void init() {
        fullNameField.setText(user.getFullName());
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());

        fullNameField.setDisable(true);
        usernameField.setDisable(true);
        emailField.setDisable(true);
        passwordField.setDisable(true);

        editUserButton.setVisible(true);
        confirmButton.setVisible(false);
    }

    /**
     * Method that allows the user to edit user information.
     */
    @FXML
    public void handleEditUser() {
        fullNameField.setDisable(false);
        usernameField.setDisable(false);
        emailField.setDisable(false);
        passwordField.setDisable(false);

        editUserButton.setVisible(false);
        confirmButton.setVisible(true);
    }

    /**
     * Method for confirming changes to the user.
     *
     * @throws Exception if something went wrong
     */
    @FXML
    public void handleConfirm() throws Exception {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        
        String oldFullName = user.getFullName();
        String oldUserName = user.getUsername();
        String oldEmail = user.getEmail();
        String oldPassword = user.getPassword();

        for (User u : modelAccess.getUsers()) {
            if (u.getUsername().equals(username) && !user.getUsername().equals(username)) {
                notify("Username is taken", AlertType.WARNING);
                return;
            }
        }

        try {
            user.setFullName(fullName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
        } catch (Exception e) {
            user.setFullName(oldFullName);
            user.setUsername(oldUserName);
            user.setEmail(oldEmail);
            user.setPassword(oldPassword);

            notify("One or more fields are empty or conatins invalid data", AlertType.WARNING);
            return;
        }

        try {
            modelAccess.putUser(user);
        } catch (IOException e) {
            notify(e.getLocalizedMessage(), AlertType.WARNING);
            return;
        }

        init();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User updated");
        alert.showAndWait();
    }

    @FXML
    public void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }
    
}
