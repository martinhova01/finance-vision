package ui;

import core.Account;
import core.Expense;
import core.Income;
import core.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AppController {

    @FXML
    private TextField balanceField, transactionDescriptionField, transactionAmountField;
    @FXML
    private ListView<String> incomeView, expenseView;
    @FXML
    private RadioButton incomeRadioButton, expenseRadioButton;
    @FXML
    private Button addTransactionButton;

    private Account account = new Account(0);

    private User user = new User("Markus", "passord", "Markus Klund", "markus.klund@hotmail.com", account);
    //private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public Account getAccount() {
        return this.user.getAccount();
    }

    @FXML
    private void updateBalanceView() {
        balanceField.setText(String.valueOf(this.user.getAccount().getBalance()));
    }

    @FXML
    private void clearAmountAndDescriptionFields() {
        this.transactionAmountField.clear();
        this.transactionDescriptionField.clear();
    }

    @FXML
    void enableButton() {
        this.addTransactionButton.setDisable(false);
    }

    @FXML
    void handleAddTransactionButton() {
        String description = this.transactionDescriptionField.getText();
        double amount = Double.parseDouble(this.transactionAmountField.getText());

        if (incomeRadioButton.isSelected()) {
            handleIncome(description, amount);
        }
        else if (expenseRadioButton.isSelected()) {
            handleExpense(description, amount);
        }
        updateBalanceView();
        clearAmountAndDescriptionFields();
    }

    @FXML
    void handleIncome(String description, double amount) {
        this.getAccount().addTransaction(new Income(description, amount));
        this.incomeView.getItems().add(0, "+ " + amount + " (" + description + ")");
    }

    @FXML
    void handleExpense(String description, double amount) {
        this.getAccount().addTransaction(new Expense(description, amount));
        this.expenseView.getItems().add(0, "- " + amount + " (" + description + ")");
    }

}
