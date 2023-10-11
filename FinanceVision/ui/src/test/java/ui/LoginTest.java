package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import filesaving.FileHandler;


public class LoginTest extends ApplicationTest {

    private AbstractController abstractController;
    private Parent root;

    @Mock
    private FileHandler mockFileHandler;


    @Override
    public void start(Stage stage) throws IOException {
        mockFileHandler = Mockito.mock(FileHandler.class);

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        abstractController.setFileHandler(mockFileHandler);
        abstractController.init();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @Test
    public void testInvalidLogin() {
        click("Log in");
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(lookup("#loginButton") != null);
    }

    // @Test
    // public void testValidLogin() {
    //     clickOn("#usernameField");
    //     write("testuser");
    //     clickOn("#passwordField");
    //     write("password");
    //     click("Log in");
    //     Assertions.assertNull(lookup("#loginButton"));
    // }


    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

}
