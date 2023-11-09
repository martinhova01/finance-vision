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

/**
 * Testclass for RegisterUserController.java using JUnit and TestFX.
 */
public class RegisterUserTest extends ApplicationTest {

    private AbstractController abstractController;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FileHandler mock = Mockito.mock(FileHandler.class);
        User user = new User("testuserTaken",
            "takenTestPassword", "taken test", "taken.test@hotmail.com", new Account(0));
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        when(mock.readModel()).thenReturn(model);

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("registerNewUser.fxml"));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        abstractController.setModelAccess(new DirectFinanceVisionModelAccess(mock));
        abstractController.init();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }
       
    private void setUp() {
        clickOn("#fullNameField");
        write("Test User");
        clickOn("#usernameField");
        write("testuser");
        clickOn("#emailField");
        write("valid@gmail.com");
        clickOn("#passwordField");
        write("password");
        clickOn("#balanceField");
        write("1000");
    }
    


    @Test
    public void testRegisterUser() {
        setUp();
        click("Register user");
        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible());
    }

    @Test
    public void testInvalidUsername() {
        setUp();
        clickOn("#usernameField");
        write(" invalid");
        click("Register user");
        Node loginButton = lookup("#backButton").query();
        Assertions.assertTrue(loginButton.isVisible(),
            "Brukernavn kan ikke inneholde mellomrom");        
    }

    @Test
    public void testTakenUsername() {
        setUp();
        clickOn("#usernameField");
        write("Taken");
        click("Register user");
        Node loginButton = lookup("#backButton").query();
        Assertions.assertTrue(loginButton.isVisible(), "Brukernavn må være unikt");  
    }

    @Test
    public void testInvalidBalance() {
        setUp();
        clickOn("#balanceField");
        write("....");
        click("Register user");
        Node loginButton = lookup("#backButton").query();
        Assertions.assertTrue(loginButton.isVisible(), "Balance må være et tall");
    }

    @Test
    public void testBack() {
        clickOn("#backButton");
        Node loginButton = lookup("#loginButton").query();
        Assertions.assertTrue(loginButton.isVisible(),
            "Feilet å returnere til login-side ved trykk");
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



