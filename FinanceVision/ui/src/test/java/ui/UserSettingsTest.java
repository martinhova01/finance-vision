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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.Account;
import core.User;
import filesaving.FileHandler;


public class UserSettingsTest extends ApplicationTest {

    private AppController appController;
    private Parent root;
    private User user;

    @Mock
    private FileHandler mockFileHandler;

    @Override
    public void start(Stage stage) throws IOException {
        mockFileHandler = Mockito.mock(FileHandler.class);
        
        user = new User("testuser", "password", "Test User",
            "test@valid.com", new Account(1000));
        User user2 = new User("testusertaken", "password2", "Test User2",
            "test2@valid.com", new Account(500));
        
        when(mockFileHandler.deserializeUsers(any(File.class)))
            .thenReturn(new ArrayList<>(List.of(user, user2)));

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        root = fxmlLoader.load();
        appController = fxmlLoader.getController();
        appController.setStage(stage);
        appController.setModelAccess(new DirectFinanceVisionModelAccess(mockFileHandler));
        appController.setUser(user);
        appController.init();
        appController.switchBorderPane("userSettings.fxml");
        stage.setScene(new Scene(root));
        stage.show();

    }
    
    @Test
    public void testConfirm() {
        clickOn("#editUserButton");
        clickOn("#usernameField");
        write("edit");
        clickOn("#confirmButton");
        click("OK");
        Node editUserButton = lookup("#editUserButton").query();
        Assertions.assertTrue(editUserButton.isVisible());
    }

    @Test
    public void testTakenUsername() {
        clickOn("#editUserButton");
        clickOn("#usernameField");
        write("taken");
        clickOn("#confirmButton");
        Node confirmButton = lookup("#confirmButton").query();
        Assertions.assertTrue(confirmButton.isVisible());
    }

    @Test
    public void testInvalidUsername() {
        clickOn("#editUserButton");
        clickOn("#usernameField");
        write(" ");
        clickOn("#confirmButton");
        Node confirmButton = lookup("#confirmButton").query();
        Assertions.assertTrue(confirmButton.isVisible());
    }

    @Test
    public void testLogOut() {
        clickOn("#logOutButton");

        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible());
    }

    @Test
    public void testDeleteUser() {
        clickOn("#deleteUserButton");
        click("OK");
        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible());
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