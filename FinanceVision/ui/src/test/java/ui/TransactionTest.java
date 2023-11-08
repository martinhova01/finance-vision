package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.Account;
import core.Expense;
import core.FinanceVisionModel;
import core.Income;
import core.Transaction;
import core.User;
import filesaving.FileHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;


public class TransactionTest extends ApplicationTest {
    
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
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);

        when(mockFileHandler.readModel()).thenReturn(model);
        
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
    public void testFilterTransactions() {
        ListView<Transaction> incomeView = lookup("#incomeView").query();
        //VirtualFlow<ListCell<Transaction>> virtualFlow = (VirtualFlow<ListCell<Transaction>>) incomeView.lookup(".virtual-flow");
        clickOn("#transactionFilterList");
        clickOn("Today");
        boolean found = false;
        for (Transaction transaction : incomeView.getItems()) {
            if (transaction.getDescription().equals("Money from granny")) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Filtrerte ikke bort transaksjon fra i går");
        clickOn("#transactionFilterList");
        clickOn("This week");
        for (Transaction transaction : incomeView.getItems()) {
            if (transaction.getDescription().equals("Money from dad")) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Filtrerte ikke bort transaksjon fra forrige uke");
        clickOn("#transactionFilterList");
        clickOn("This month");
        for (Transaction transaction : incomeView.getItems()) {
            if (transaction.getDescription().equals("Salary")) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Filtrerte ikke bort transaksjon fra forrige måned");
        clickOn("#transactionFilterList");
        clickOn("This year");
        for (Transaction transaction : incomeView.getItems()) {
            if (transaction.getDescription().equals("Christmas present")) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Filtrerte ikke bort transaksjon fra forrige år");
    }

    @Test
    public void testAddIncome() {
        clickOn("#addTransactionButton");
        clickOn("#incomeRadioButton");
        clickOn("#amountField");
        write("100");
        clickOn("#descriptionField");
        write("food");        
        clickOn("#categoryList");
        clickOn("Salary");
        clickOn("#addTransactionButton");
        Node editTransaction = lookup("#editTransactionButton").query();
        Assertions.assertTrue(editTransaction.isVisible());
    }

    @Test
    public void testAddExpense() {
        clickOn("#addTransactionButton");
        clickOn("#expenseRadioButton");
        clickOn("#amountField");
        write("100");
        clickOn("#descriptionField");
        write("Food");        
        clickOn("#categoryList");
        clickOn("Food");
        clickOn("#addTransactionButton");

    }

    @Test
    public void testDeleteTransaction() {
        ListView<Transaction> incomeView = lookup("#incomeView").query();
        VirtualFlow<ListCell<Transaction>> virtualFlow = (VirtualFlow<ListCell<Transaction>>) incomeView.lookup(".virtual-flow");
        ListCell<Transaction> firstCell = virtualFlow.getFirstVisibleCell();
        clickOn("#deleteTransactionButton");
        click("OK");
        clickOn(firstCell, MouseButton.PRIMARY);
        clickOn("#deleteTransactionButton");
        boolean found = false;
        for (Transaction transaction : incomeView.getItems()) {
            if (transaction.getDescription().equals("Vipps")) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Transaksjonen ble ikke slettet");
    }

    @Test
    public void testEditIncome() {
        ListView<Transaction> incomeView = lookup("#incomeView").query();
        VirtualFlow<ListCell<Transaction>> virtualFlow = (VirtualFlow<ListCell<Transaction>>) incomeView.lookup(".virtual-flow");
        ListCell<Transaction> firstCell = virtualFlow.getFirstVisibleCell();
        clickOn("#editTransactionButton");
        click("OK");
        clickOn(firstCell, MouseButton.PRIMARY);
        clickOn("#editTransactionButton");
        clickOn("#amountField");
        write("H");
        clickOn("#confirmButton");
        click("OK");
        clickOn("#amountField");
        push(KeyCode.BACK_SPACE);
        clickOn("#descriptionField");
        write("1");
        clickOn("#confirmButton");
        boolean found = false;
        ListView<Transaction> incomeView2 = lookup("#incomeView").query();
        VirtualFlow<ListCell<Transaction>> virtualFlow2 = (VirtualFlow<ListCell<Transaction>>) incomeView2.lookup(".virtual-flow");
        ListCell<Transaction> firstCell2 = virtualFlow2.getFirstVisibleCell();
        clickOn(firstCell2, MouseButton.PRIMARY);
        clickOn("#editTransactionButton");
        TextField descriptionField = lookup("#descriptionField").query();
        if (descriptionField.getText().equals("Vipps1")) {
            found = true;
        }
        clickOn("#backButton");
        Assertions.assertTrue(found, "Transaksjonen ble ikke endret");
    }

    @Test
    public void testEditExpense() {
        ListView<Transaction> expenseView = lookup("#expenseView").query();
        VirtualFlow<ListCell<Transaction>> virtualFlow = (VirtualFlow<ListCell<Transaction>>) expenseView.lookup(".virtual-flow");
        ListCell<Transaction> firstCell = virtualFlow.getFirstVisibleCell();
        clickOn(firstCell, MouseButton.PRIMARY);
        clickOn("#editTransactionButton");
        clickOn("#descriptionField");
        write("1");
        clickOn("#confirmButton");
        boolean found = false;
        ListView<Transaction> expenseView2 = lookup("#expenseView").query();
        VirtualFlow<ListCell<Transaction>> virtualFlow2 = (VirtualFlow<ListCell<Transaction>>) expenseView2.lookup(".virtual-flow");
        ListCell<Transaction> firstCell2 = virtualFlow2.getFirstVisibleCell();
        clickOn(firstCell2, MouseButton.PRIMARY);
        clickOn("#editTransactionButton");
        TextField descriptionField = lookup("#descriptionField").query();
        if (descriptionField.getText().equals("Food1")) {
            found = true;
        }
        clickOn("#backButton");
        Assertions.assertTrue(found, "Transaksjonen ble ikke endret");
    }

    @Test
    public void testGetUser() {
        assertEquals(user, abstractController.getUser());
    }

    private void click(String... labels) {
        for (var label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }
}
