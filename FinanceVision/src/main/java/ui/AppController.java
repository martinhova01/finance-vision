package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import core.Account;
import core.Expense;
import core.Income;
import core.Transaction;
import core.User;
import fileSaving.FileSaving;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AppController extends AbstractController {

    @FXML
    private TextField balanceField, transactionDescriptionField, transactionAmountField;
    @FXML
    private ListView<String> incomeView, expenseView, incomeCategoryView, expenseCategoryView;
    @FXML
    private RadioButton incomeRadioButton, expenseRadioButton;
    @FXML
    private Button addTransactionButton, logOutButton;
    @FXML
    private ChoiceBox<String> categoryList;

    private User user;
    private HashMap<String, Double> categoryTransactions = new HashMap<>();
    private ArrayList<String> expenseCategoryTypes = new ArrayList<>(Arrays.asList("Food", "Clothes", "Housing", "Other")); //add more later
    private ArrayList<String> incomeCategoryTypes = new ArrayList<>(Arrays.asList("Salary", "Gift", "intrests", "Other")); //add more later

    public void setUser(User user) {
        this.user = user;
        this.balanceField.setText(String.valueOf(getAccount().getBalance()));
        retrieveIncomeAndExpenseCategorySum();
        updateIncomeAndExpenseCategoryView();
        loadTransactionsFromFile();
    }

    private void loadTransactionsFromFile() {
        List<Transaction> fileTransactions = getAccount().getTransactions();
        for (Transaction transaction : fileTransactions) {
            addTransactionToView(transaction);
        }
    }

    private void retrieveIncomeAndExpenseCategorySum() {
        //Kan kanskje isteden ha en felles for-løkke som itererer gjennom alle kategoriene samtidig?
        for (String incomeCategory : this.incomeCategoryTypes) {
            double incomeCategorySum = 0;
            for (Transaction transaction : getAccount().getTransactions(t -> t.getCategory().equals(incomeCategory))) {
                incomeCategorySum += transaction.getAmount();
             }
             this.categoryTransactions.put(incomeCategory, incomeCategorySum);
        }
        for (String expenseCategory : this.expenseCategoryTypes) {
            double expenseCategorySum = 0;
            for (Transaction transaction : getAccount().getTransactions(t -> t.getCategory().equals(expenseCategory))) {
                expenseCategorySum += transaction.getAmount();
             }
             this.categoryTransactions.put(expenseCategory, expenseCategorySum);
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
    private void updateIncomeAndExpenseCategoryView() {
        this.incomeCategoryView.getItems().clear();
        this.expenseCategoryView.getItems().clear();
        for (String incomeCategory : this.incomeCategoryTypes) {
            this.incomeCategoryView.getItems().add(incomeCategory + ": " + this.categoryTransactions.get(incomeCategory));
        }
        for (String expenseCategory : this.expenseCategoryTypes) {
            this.expenseCategoryView.getItems().add(expenseCategory + ": " + this.categoryTransactions.get(expenseCategory));
        }
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

    // @FXML
    // void enableButton() {
    //     if (! this.categoryList.getValue().equals("Select category") && (incomeRadioButton.isSelected() || expenseRadioButton.isSelected())) {
    //         this.addTransactionButton.setDisable(false);
    //     }
    //     else {
    //         this.addTransactionButton.setDisable(true);
    //     }
    // }

    @FXML
    void updateButton(){
        
        if (categoryList.getValue() != null && (incomeRadioButton.isSelected() || expenseRadioButton.isSelected()) && !transactionAmountField.getText().equals("") && !transactionDescriptionField.getText().equals("")){
            this.addTransactionButton.setDisable(false);
        }
        else{
            this.addTransactionButton.setDisable(true);
        }
    }
    @FXML
    void handleRbtnClicked(){

        if (incomeRadioButton.isSelected()){
            categoryList.getItems().clear();
            categoryList.getItems().addAll(this.incomeCategoryTypes);
        }
        else if(expenseRadioButton.isSelected()){
            categoryList.getItems().clear();
            categoryList.getItems().addAll(this.expenseCategoryTypes);
        }
        this.addTransactionButton.setDisable(true);

    }

    private boolean isNumeric(String string) {
        return string.matches("\\d*\\.?\\d*") && string.charAt(string.length() - 1) != '.';
    }

    @FXML
    void handleAddTransactionButton() {
        String description = this.transactionDescriptionField.getText();
        String amountString = this.transactionAmountField.getText();
    
        if (! isNumeric(amountString)) {
            notify("The given amount should only contain digits");
            return;
        }
        double amount = Double.parseDouble(amountString);
        String category = this.categoryList.getValue();
        if (incomeRadioButton.isSelected()) {
            handleIncome(description, amount, category);
        }
        else if (expenseRadioButton.isSelected()) {
            handleExpense(description, amount, category);
        }
        updateBalanceView();
        updateIncomeAndExpenseCategoryView();
        clearAmountAndDescriptionFields();
        this.addTransactionButton.setDisable(true);
        try {
            saveAccountChanges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccountChanges() throws IOException {
        List<User> users = FileSaving.readFromFile("data.txt");
        for (User user : users) {
            if (user.getUsername().equals(this.user.getUsername())) {
                users.remove(user);
                users.add(this.user);
            }
        }
        FileSaving.writeToFile(users, "data.txt");
    }

    @FXML
    void handleIncome(String description, double amount, String category) {
        Income income = new Income(description, amount, category);
        this.getAccount().addTransaction(income);
        addTransactionToView(income);
        retrieveIncomeAndExpenseCategorySum();
    }

    @FXML
    void handleExpense(String description, double amount, String category) {
        Expense expense = new Expense(description, amount, category);
        this.getAccount().addTransaction(expense);
        addTransactionToView(expense);
        retrieveIncomeAndExpenseCategorySum();
    }

    @FXML
    void addTransactionToView(Transaction transaction) {
        if (transaction instanceof Income) {
            this.incomeView.getItems().add(0, "+ " + transaction.getAmount() + "    " + transaction.getDescription());
        }
        else {
            this.expenseView.getItems().add(0, "- " + transaction.getAmount() + "    " + transaction.getDescription());
        }
    }

    @FXML
    void handleLogOutButton() throws IOException {
        switchScene("login.fxml");
    }

}
