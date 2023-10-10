package ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import core.Account;
import core.User;
import filesaving.FileHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BudgetTest extends ApplicationTest {

    private BudgetController controller;
    private Parent root;
    private User user;
    

    @Mock
    private FileHandler mockFileHandler;


    @Override
    public void start(Stage stage) throws IOException {
        mockFileHandler = Mockito.mock(FileHandler.class);
        Account account = new Account(3000);
        user = new User("testuser", "password", "Test User", "test@user.com", account);
        when(mockFileHandler.deserializeUsers(any(File.class))).thenReturn(new ArrayList<>(List.of(user)));
        
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("budget.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setUser(user);
        controller.setFileHandler(mockFileHandler);
        controller.init();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();

    }

    @Test
    public void testBackButton() {
        clickOn("#backButton");
    }

    @Test
    public void testSetLimit() {
        clickOn("edit");
        write("100");
        clickOn("set");
        TextField limit = lookup("#limit1").query();
        assert(limit.getText().equals("100"));
    }

}
