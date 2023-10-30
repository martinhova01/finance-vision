package ui;

import core.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

    private List<User> users;

    @Override
    public void setUser(User user) {
        super.setUser(user);
        init();
    }

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

        try {
            users = fileHandler.deserializeUsers(new File(System.getProperty(
                "user.home") + "/data.json"));
        } catch (IOException e) {
            notify("File not found", AlertType.ERROR);
        }
    }

    @FXML
    public void handleEditUser() {
        fullNameField.setDisable(false);
        usernameField.setDisable(false);
        emailField.setDisable(false);
        passwordField.setDisable(false);

        editUserButton.setVisible(false);
        confirmButton.setVisible(true);
        
    }

    @FXML
    public void handleConfirm() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();

        for (User u : users) {
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

            init();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User updated");
            alert.showAndWait();
        } catch (Exception e) {
            notify("One or more fields are empty or conatins invalid data", AlertType.WARNING);
            return;
        }

        saveToFile();
        //funker ikke å lagre til fil?
    }

    @FXML
    public void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }
    
}
