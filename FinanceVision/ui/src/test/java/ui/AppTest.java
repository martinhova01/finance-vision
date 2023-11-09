package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import core.Account;
import core.Expense;
import core.Income;
import core.User;
import filesaving.FileHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppTest extends ApplicationTest {
    private AbstractController abstractController;
    private Parent root;
    private User user;

    @Mock
    private FileHandler mockFileHandler;

    @Override
    public void start(Stage stage) throws IOException {
        mockFileHandler = Mockito.mock(FileHandler.class);
        Account account = new Account(1000);
        Income vippsIncome = new Income("Vipps", 500, "Salary", LocalDateTime.now());
        Income income1 = new Income("Money from granny", 1000.0, "Other", LocalDateTime.now().minusDays(1));
        Income income2 = new Income("Money from dad", 200.0, "Other", LocalDateTime.now().minusWeeks(1));
        Income income3 = new Income("Salary", 2500.0, "Other", LocalDateTime.now().minusMonths(1));
        Income income4 = new Income("Christmas present", 100.0, "Other", LocalDateTime.now().minusYears(1));
        Expense foodExpense = new Expense("Food", 100, "Food", LocalDateTime.now());
        account.addTransaction(vippsIncome);
        account.addTransaction(income1);
        account.addTransaction(income2);
        account.addTransaction(income3);
        account.addTransaction(income4);
        account.addTransaction(foodExpense);
        user = new User("testuser", "password", "Test User", "test@valid.com", account);
        when(mockFileHandler.deserializeUsers(any(File.class))).thenReturn(new ArrayList<>(List.of(user)));
        
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("app.fxml"));
        root = fxmlLoader.load();
        abstractController = fxmlLoader.getController();
        abstractController.setStage(stage);
        abstractController.setModelAccess(new DirectFinanceVisionModelAccess(mockFileHandler));
        abstractController.setUser(user);
        abstractController.init();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Test
    public void testClickLabels() {
        Node addTransactionButton = lookup("#addTransactionButton").query();
        assertTrue(addTransactionButton.isVisible(), "Klarte ikke å laste inn transaction-siden ved start");
        clickOn("#budgetLabel");
        Node editBudgetButton = lookup("#editBudgetButton").query();
        assertTrue(editBudgetButton.isVisible(), "Klarte ikke å laste inn edit budget-siden ved klikk");
        clickOn("#userSettingsLabel");
        Node deleteUserButton = lookup("#deleteUserButton").query();
        assertTrue(deleteUserButton.isVisible(), "Klarte ikke å laste inn user settings-siden ved klikk");
        clickOn("#transactionLabel");
        assertTrue(addTransactionButton.isVisible(), "Klarte ikke å laste inn transaction-siden ved klikk");
    }
}
