package ui;

import core.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller-class for the logging in.
 */
public class LoginController extends AbstractController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerUserButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView piggyCoin;
    @FXML
    private Text loginSuccessful;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private ImageView piggyBankBackground;
    @FXML
    private ImageView piggyCoinWelcome;

    private List<User> users;
    
    @Override
    public void init() {
        try {
            this.users = fileHandler.deserializeUsers(new File(System.getProperty(
                "user.home") + "/data.json"));
        } catch (IOException e) {
            this.users = new ArrayList<>();
            try {
                fileHandler.serializeUsers(this.users, new File(System.getProperty(
                    "user.home") + "/data.json"));
            } catch (IOException e1) {
                notify("File not found", AlertType.ERROR);
            }
        }


        loginSuccessful.setVisible(false);
        loginButton.setFocusTraversable(false);
        registerUserButton.setFocusTraversable(false);
    }



    void loginTransition() {
        FadeTransition loginTransition = new FadeTransition();
        loginTransition.setDuration(Duration.seconds(0.3));
        loginTransition.setNode(loginPane);
        loginTransition.setFromValue(1);
        loginTransition.setToValue(0);
        loginTransition.play();
    }

    void piggyCoinJumpAnimation() {
        TranslateTransition piggyCoinTransition = new TranslateTransition();
        piggyCoinTransition.setNode(piggyCoin);
        piggyCoinTransition.setDuration(Duration.seconds(0.15));
        piggyCoinTransition.setByY(-10);
        piggyCoinTransition.setAutoReverse(true);
        piggyCoinTransition.setCycleCount(2);
        piggyCoinTransition.play();
    }
    

    @FXML
    void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful" + user);
                loginTransition();
                piggyCoinJumpAnimation();
                PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.3));
                loginSuccessful.setVisible(true);
                pause.play();
                // wait for pausetransition to finish before switching scene
                pause.setOnFinished(event -> {
                    try {
                        switchScene("App.fxml", user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
        }
        notify("Invalid username or password", AlertType.WARNING);

    }

    

    @FXML
    void handleRegisterUser() throws IOException {
        switchScene("registerNewUser.fxml");
    }
    
}
