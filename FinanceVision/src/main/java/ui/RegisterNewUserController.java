package ui;

import java.io.IOException;
import java.util.List;

import core.Account;
import core.User;
import fileSaving.FileSaving;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        users = FileSaving.readFromFile("data.txt");
    }
  

    @FXML
    void handleCreateUser() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        double balance = Double.parseDouble(balanceField.getText());
        for (User user : users) {
            if (user.getUsername().equals(username)){
                //TODO: handle username is taken
                throw new IllegalArgumentException("Username is taken");
            }
        }

        try {
        users.add(new User(username, password, fullName, email, new Account(balance)));
            
        } catch (Exception e) {
            // TODO: handle exception. invalid input or empty textFields. Check validation in User.java
        }

        FileSaving.writeToFile(users, "data.txt");

        switchScene("login.fxml");
    }

    @FXML
    void handleBack() throws IOException{
        switchScene("login.fxml");
    }

}
