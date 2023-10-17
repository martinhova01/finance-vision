package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import core.Account;
import core.User;
import filesaving.FileHandler;


public class LoginTest extends ApplicationTest {

    private AbstractController abstractController;
    private Parent root;
    private User user;



    @Override
    public void start(Stage stage) throws IOException {
        FileHandler mockFileHandler = Mockito.mock(FileHandler.class);
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        user = new User("testuser", "password", "Test User", "test@valid.com", new Account(1000));
        when(mockFileHandler.deserializeUsers(any(File.class))).thenReturn(List.of(user));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        abstractController.setFileHandler(mockFileHandler);
        abstractController.init();
        abstractController.setFileHandler(mockFileHandler);
        abstractController.init();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @Test
    public void testInvalidLogin() {
        click("Log in");
        WaitForAsyncUtils.waitForFxEvents();
        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible());
    }

    @Test
    public void testValidLogin() {
        clickOn("#usernameField");
        write("testuser");
        clickOn("#passwordField");
        write("password");
        click("Log in");
        Node logOutButton = lookup("#logOutButton").query();
        Assertions.assertTrue(logOutButton.isVisible());
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
