package ui;

import java.util.ArrayList;
import java.util.List;

import core.Expense;
import core.Income;
import core.User;
import fileSaving.FileSaving;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class LoginController {

    //Har ikke lagd ui da jeg ikke vet hvordan jeg skal knytte det til eksisterende fxml-fil

    @FXML
    private TextField usernameField, passwordField, fullNameField, emailField;
    @FXML
    private RadioButton loginRadioButton, createUserRadioButton;
    @FXML
    private Button loginButton, createUserButton;

    private List<User> users;

    @FXML
    private void initialize() {
        //Lese fra fil må fikses
        users = FileSaving.readFromFile("data.txt");
    }
    
    @FXML
    void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                System.out.println("Login successful");
                //håndter login
                return;
            }
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    @FXML
    void handleCreateUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        for (User user : users) {
            if (user.getUsername().equals(username)){
                throw new IllegalArgumentException("Username is taken");
            }
        }
        //Mer validering her eller gjøres det i User-klassen?
        users.add(new User(username, password, fullName, email, null));
        //Skrive til fil må fikses
        FileSaving.writeToFile(users, "data.txt");
    }
}
