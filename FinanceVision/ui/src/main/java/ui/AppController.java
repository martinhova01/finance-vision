package ui;

import core.Account;
import core.Income;
import core.Transaction;
import core.User;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for the main page of the application.
 */
public class AppController extends AbstractController {

    @FXML
    private TextField balanceField;
    @FXML
    private ListView<Transaction> incomeView;
    @FXML
    private ListView<Transaction> expenseView;
    @FXML
    private Button addTransactionButton;
    @FXML
    private Button editTransactionButton;
    @FXML
    private Button deleteTransactionButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button budgetButton;

    @Override
    public void setUser(User user) {
        this.user = user;
        init();        
    }
    
    /**
     * Shows total bank balance and list of transactions.
     */
    @Override
    public void init() {
        updateBalanceView();

        incomeView.getItems().clear();
        expenseView.getItems().clear();
        loadTransactionsFromFile();
    }



    private void loadTransactionsFromFile() {
        List<Transaction> fileTransactions = getAccount().getTransactions();
        for (Transaction transaction : fileTransactions) {
            addTransactionToView(transaction);
        }
    }

    @FXML
    private void initialize() {
        this.balanceField.setFocusTraversable(false);
    }

    public User getUser() {
        return this.user;
    }

    public Account getAccount() {
        return this.user.getAccount();
    }

    @FXML
    private void updateBalanceView() {
        balanceField.setText(String.valueOf(Math.round(this.user.getAccount().getBalance())));
    }


    @FXML
    void addTransactionToView(Transaction transaction) {
        if (transaction instanceof Income) {
            this.incomeView.getItems().add(0, transaction);
        } else {
            this.expenseView.getItems().add(0, transaction);
        }
    }

    @FXML
    void handleLogOutButton() throws IOException {
        switchScene("login.fxml");
    }

    @FXML
    void handleAddTransactionButton() throws IOException {
        switchScene("addTransaction.fxml", user);
    }

    @FXML
    void handleBudgetButton() throws IOException {
        switchScene("budget.fxml", user);
    }

    private Transaction selectedTransaction;

    @FXML
    void handleIncomeView() {
        selectedTransaction = incomeView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void handleExpenseView() {
        selectedTransaction = expenseView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void handleEditTransaction() throws IOException {
        if (incomeView.getSelectionModel().isEmpty() && expenseView.getSelectionModel().isEmpty()) {
            notify("No transaction selected", AlertType.ERROR);
            return;
        }
        switchScene("editTransaction.fxml", user, selectedTransaction);
    }

    @FXML
    void handleDeleteTransaction() {
        if (incomeView.getSelectionModel().isEmpty() && expenseView.getSelectionModel().isEmpty()) {
            notify("No transaction selected", AlertType.ERROR);
            return;
        }
        getAccount().removeTransaction(selectedTransaction);
        saveToFile();
        init();
    }

}
