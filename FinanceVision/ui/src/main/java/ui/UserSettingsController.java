package ui;

import core.User;
import java.io.IOException;
//import java.util.List;
import javafx.fxml.FXML;
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
    private Button updateUserButton;

    //private List<User> users;

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
    }

    @FXML
    public void handleUpdateUser() {
        return;
    }

    @FXML
    public void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }
    
}
