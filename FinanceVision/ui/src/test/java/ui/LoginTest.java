package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;


public class LoginTest extends ApplicationTest {

    private AbstractController abstractController;
    private Parent root;
    private static Stage stage;




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();

    }



    private void setStage(Stage stage) {
        LoginTest.stage = stage;
    }
    
    
    public static Stage getPrimaryStage() {
        return stage;
    }

    @Test
    public void testInvalidLogin() {
        click("Log in");
        Assertions.assertTrue(getRootNode().lookup("#loginButton") != null);

    }


    public Parent getRootNode() {
        return root;
    }

    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

}
