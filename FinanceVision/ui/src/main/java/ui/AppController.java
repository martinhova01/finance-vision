package ui;

import core.Account;
import core.Income;
import core.Transaction;
import core.User;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    @FXML
    private ChoiceBox<String> transactionFilterList;

    //@Override
    //public void setUser(User user) {
    //    this.user = user;
    //    init();        
    //}
    
    /**
     * Shows total bank balance and list of transactions.
     */
    @Override
    public void init() {
        updateBalanceView();

        incomeView.getItems().clear();
        expenseView.getItems().clear();
        loadTransactionsFromFile();
        
        transactionFilterList.getItems().addAll(List.of("All", "Today", "This week", "This month", "This year"));
        transactionFilterList.getSelectionModel().selectFirst();
        transactionFilterList.setOnAction(this::handleFilterTransactionsList);
    }
    
    private void loadTransactionsFromFile() {
        List<Transaction> fileTransactions = getAccount().getTransactions();
        fileTransactions.stream().sorted((t1, t2) -> t1.getTime().compareTo(t2.getTime()))
        .forEach(t -> addTransactionToView(t));
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

    /**
     * Method for filtering the transactions by current day, month or year.
     */
    @FXML
    void handleFilterTransactionsList(ActionEvent event) {
        List<Transaction> allTransactions = getAccount().getTransactions();
        String selectedTime = transactionFilterList.getValue();
        incomeView.getItems().clear();
        expenseView.getItems().clear();
        Stream<Transaction> timeFilterStream = allTransactions.stream();

        if (selectedTime.equals("Today")) {
            timeFilterStream = timeFilterStream.filter(t -> t.getTime().toLocalDate().equals(LocalDate.now()));
        }
        else if (selectedTime.equals("This week")) {
            timeFilterStream = timeFilterStream.filter(new Predicate<Transaction>() {
                @Override
                public boolean test(Transaction t) {
                    LocalDate tDate = t.getTime().toLocalDate();
                    LocalDate nowDate = LocalDate.now();
                    LocalDate tDateStartOfWeek = tDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate nowDateStartOfWeek = nowDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    return tDateStartOfWeek.equals(nowDateStartOfWeek);
                }
            });
        }
        else if (selectedTime.equals("This month")) {
            timeFilterStream = timeFilterStream.filter(t -> t.getTime().getMonth().equals(LocalDate.now().getMonth()) 
            && t.getTime().getYear() == LocalDate.now().getYear());
        }
        else if (selectedTime.equals("This year")) {
            timeFilterStream = timeFilterStream.filter(t -> t.getTime().getYear() == LocalDate.now().getYear());
        }
        timeFilterStream
        .sorted((t1, t2) -> t1.getTime().compareTo(t2.getTime()))
        .forEach(t -> addTransactionToView(t));
    }
}
