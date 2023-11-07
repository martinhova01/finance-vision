package ui;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Controller for animation when loading the app.
 */
public class LoadLoginScreenController extends AbstractController {

    @FXML
    private ImageView piggyCoin;
    @FXML
    private ImageView piggyBankBackground;
    @FXML
    private AnchorPane loadLoginAnchor;


    @Override
    public void init() {
        loadLoginAnchor.setVisible(true);
        
        piggyCoinAnimation();
        loginScreenAnimation();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.890));
        pause.play();
        pause.setOnFinished((ActionEvent event) -> {
            try {
                switchScene("login.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }

    private void piggyCoinAnimation() {
        TranslateTransition piggyCoinTransition = new TranslateTransition();
        piggyCoinTransition.setNode(piggyCoin);
        piggyCoinTransition.setDuration(Duration.seconds(0.3));
        piggyCoinTransition.setByY(-40);
        piggyCoinTransition.setAutoReverse(true);
        piggyCoinTransition.setCycleCount(2);
        piggyCoinTransition.play();
    }

    private void loginScreenAnimation() {
        FadeTransition loginScreen = new FadeTransition();
        loginScreen.setNode(loadLoginAnchor);
        loginScreen.setDuration(javafx.util.Duration.seconds(0.3));
        loginScreen.setFromValue(1);
        loginScreen.setToValue(0);
        loginScreen.setDelay(Duration.seconds(0.6));
        loginScreen.play();
        
    }

    
}
