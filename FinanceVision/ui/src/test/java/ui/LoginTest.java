package ui;

import static org.mockito.Mockito.when;

import core.Account;
import core.FinanceVisionModel;
import core.User;
import filesaving.FileHandler;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;


/**
 * Testclass for LoginController.java using JUnit and TestFX.
 */
public class LoginTest extends ApplicationTest {

    private AbstractController abstractController;
    private Parent root;
    private User user;



    @Override
    public void start(Stage stage) throws IOException {
        FileHandler mockFileHandler = Mockito.mock(FileHandler.class);
        user = new User("testuser", "password", "Test User", "test@valid.com", new Account(1000));
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        when(mockFileHandler.readModel()).thenReturn(model);
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        abstractController.setModelAccess(new DirectFinanceVisionModelAccess(mockFileHandler));
        abstractController.init();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Test
    public void testInvalidLogin() {
        click("Log in");
        WaitForAsyncUtils.waitForFxEvents();
        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible(),
            "Skal ikke kunne logge inn uten gyldig brukernavn og passord");
    }

    @Test
    public void testValidLogin() {
        clickOn("#usernameField");
        write("testuser");
        clickOn("#passwordField");
        write("password");
        click("Log in");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Node balanceField = lookup("#balanceField").query();
        Assertions.assertTrue(balanceField.isVisible(),
            "Mislykket login med gyldig brukernavn og passord");
    }

    @Test
    public void testRegisterUser() {
        clickOn("#registerUserButton");
        Node backButton = lookup("#backButton").query();
        Assertions.assertTrue(backButton.isVisible(),
            "Feilet å laste inn register user-side ved trykk");
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
