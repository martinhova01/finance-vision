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
public class UserSettingsController extends AbstractSubController {

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
    private Button editUserButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button deleteUserButton;

    private User user;
    private FinanceVisionModelAccess modelAccess;

    /**
     * Loads in the user to update.
     */
    @Override
    public void init() {

        user = parentController.getUser();
        modelAccess = parentController.getModelAccess();

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

        if (modelAccess.containsUser(username) && !username.equals(oldUserName)) {
            parentController.notify("Username is taken", AlertType.WARNING);
            return;
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

            parentController.notify("One or more fields are empty or contains invalid data",
                AlertType.WARNING);
            return;
        }

        try {
            modelAccess.putUser(user);
        } catch (IOException e) {
            parentController.notify(e.getLocalizedMessage(), AlertType.WARNING);
            return;
        }

        init();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User updated");
        alert.showAndWait();
    }

    
    /**Deletes user.
     *
     * @throws IOException if user could not be deleted.
     */
    @FXML
    public void handleDeleteUser() throws IOException {
        try {
            modelAccess.removeUser(user);
        } catch (Exception e) {
            parentController.notify("Could not delete user.", AlertType.ERROR);
            return;
        }

        parentController.switchScene("login.fxml");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User deleted");
        alert.showAndWait();
    }

    @FXML
    public void handleLogOut() throws IOException {
        parentController.switchScene("login.fxml");
    }
    
}
