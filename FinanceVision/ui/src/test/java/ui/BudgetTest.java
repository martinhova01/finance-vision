package ui;

import static org.mockito.Mockito.when;

import core.Account;
import core.Budget;
import core.Expense;
import core.FinanceVisionModel;
import core.Transaction;
import core.User;
import filesaving.FileHandler;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Testclass for BudgetController.java using JUnit and TestFX.
 */
public class BudgetTest extends ApplicationTest {

    private AppController parentController;
    private Parent root;
    private User user;
    
    @Mock
    private FileHandler mockFileHandler;

    @Override
    public void start(Stage stage) throws IOException {
        Account account = new Account(3000);
        account.addTransaction(new Expense("mat", 2000.0, "Food"));
        Transaction tooOld = new Expense("old", 500.0, "Clothes", LocalDateTime.MIN);
        account.addTransaction(tooOld);
        user = new User("testuser", "password", "Test User", "test@user.com", account);
        Budget budget = new Budget();
        budget.addCategory("Food", 500);
        budget.addCategory("Clothes", 1000);
        user.setBudget(budget);
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);

        mockFileHandler = Mockito.mock(FileHandler.class);
        when(mockFileHandler.readModel()).thenReturn(model);
        
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        root = fxmlLoader.load();
        parentController = fxmlLoader.getController();
        parentController.setUser(user);
        parentController.setModelAccess(new DirectFinanceVisionModelAccess(mockFileHandler));
        parentController.init();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        parentController.setStage(stage);
        parentController.setScene(scene);
        stage.show();
        parentController.switchBorderPane("budget.fxml");

    }

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Test
    public void testEditButton() {
        clickOn("#editBudgetButton");
        Node confirmButton = lookup("#confirmButton").query();
        Assertions.assertTrue(confirmButton.isVisible(),
            "Klarte ikke å laste inn edit budget-siden ved klikk"); 
        clickOn("#backButton");
        Node editBudgetButton = lookup("#editBudgetButton").query();
        Assertions.assertTrue(editBudgetButton.isVisible(),
            "Klarte ikke å returnere til hovedsiden");
    }

    @Test
    public void testEditCategories() {
        clickOn("#editBudgetButton");
        clickOn("#limit1");
        write("xy");
        clickOn("#confirmButton");
        Node confirmButton = lookup("#confirmButton").query();
        Assertions.assertTrue(confirmButton.isVisible(), "Fikk ikke varsel om ugyldig limit");

        click("OK");
        clickOn("#limit1");
        type(KeyCode.BACK_SPACE, 10);
        write("1200.0");

        clickOn("#category0");
        type(KeyCode.BACK_SPACE, 10);
        clickOn("#confirmButton");
        confirmButton = lookup("#confirmButton").query();
        Assertions.assertTrue(confirmButton.isVisible(), "Fikk ikke varsel om ugyldig category");

        click("OK");
        clickOn("#category0");
        write("valid");
        clickOn("#confirmButton");
        Node editBudgetButton = lookup("#editBudgetButton").query();
        Assertions.assertTrue(editBudgetButton.isVisible()); 
    }

    @Test
    public void testRemoveAndAddCategories() {
        clickOn("#editBudgetButton");
        WaitForAsyncUtils.waitForFxEvents();
        VBox limitBox = (VBox) lookup("#limitBox").query();
        click("+");
        Assertions.assertTrue(limitBox.getChildren().size() == 3, "Feilet å legge til et budsjett");

        clickOn("#remove2");
        Assertions.assertTrue(limitBox.getChildren().size() == 2, "Feilet å slette et budsjett");

        clickOn("#confirmButton");
        Node editBudgetButton = lookup("#editBudgetButton").query();
        Assertions.assertTrue(editBudgetButton.isVisible(),
            "Klarte ikke å returnere til hovedside");

    }


    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

}
