package ui;

import java.io.IOException;
import java.util.List;

import core.Account;
import core.User;
import fileSaving.JsonFileSaving;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegisterNewUserController extends AbstractController{

    @FXML
    private TextField usernameField, fullNameField, emailField, balanceField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton, registerUserButton;

    private List<User> users;


    @FXML
    private void initialize() throws IOException {
        //users = FileSaving.readFromFile("data.txt");
        users = JsonFileSaving.deserializeUsers("data.json");

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
            if (user.getUsername().equals(username)){
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

        //FileSaving.writeToFile(users, "data.txt");
        JsonFileSaving.serializeUsers(users, "data.json");

        switchScene("login.fxml");
    }

    @FXML
    void handleBack() throws IOException{
        switchScene("login.fxml");
    }


}
