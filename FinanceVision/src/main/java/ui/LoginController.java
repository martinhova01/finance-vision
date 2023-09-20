package ui;

import java.io.IOException;
import java.util.List;

import core.User;
import fileSaving.FileSaving;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class LoginController extends AbstractController{

    @FXML
    private Button loginButton, registerUserButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private List<User> users;

    @FXML
    private void initialize() throws IOException {
        users = FileSaving.readFromFile("data.txt");
        loginButton.setFocusTraversable(false);
        registerUserButton.setFocusTraversable(false);
    }

    @FXML
    void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                System.out.println("Login successful");
                login(user);
                return;
            }
        }
        notify("Invalid username or password");

    }

    private void login(User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        root = loader.load();
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
            //gets the controller for the main scene and sets the user that is logged in
        AppController controller = loader.getController();
        controller.setUser(user);

    }

    @FXML
    void handleRegisterUser() throws IOException{
        switchScene("registerNewUser.fxml");
    }

   
}
