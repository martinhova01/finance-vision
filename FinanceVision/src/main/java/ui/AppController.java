package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import core.Account;
import core.Expense;
import core.Income;
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
    private ListView<String> incomeView, expenseView, categoryView;
    @FXML
    private RadioButton incomeRadioButton, expenseRadioButton;
    @FXML
    private Button addTransactionButton;
    @FXML
    private ChoiceBox<String> categoryList;

    private User user;
    private HashMap<String, Double> categoryTransactions = new HashMap<>();
    private ArrayList<String> categoryTypes = new ArrayList<>(Arrays.asList("Food", "Clothes", "Housing", "Other"));

    public void setUser(User user) {
        this.user = user;
        this.balanceField.setText(String.valueOf(getAccount().getBalance()));
        //Bør kanskje flyttes over i egen metode?:
        retrieveCategorySum();
        updateCategoryView();
    }

    //Må fikse slik at det adderer incomes og subtraherer expenses
    private void retrieveCategorySum() {
        for (String category : this.categoryTypes) {
            Double categorySum = this.getAccount()
            .getTransactions(transaction -> transaction.getCategory().equals(category))
            .stream()
            .mapToDouble(transaction -> transaction.getAmount())
            .sum();
            this.categoryTransactions.put(category, categorySum);
        }
    }

    @FXML
    private void initialize() {
        this.categoryList.getItems().add("Select category");
        this.categoryList.getItems().addAll(this.categoryTypes);
        this.categoryList.setValue("Select category");
    }

    public User getUser() {
        return this.user;
    }

    public Account getAccount() {
        return this.user.getAccount();
    }

    @FXML
    private void updateCategoryView() {
        this.categoryView.getItems().clear();
        for (String category : this.categoryTypes) {
            this.categoryView.getItems().add(category + ": " + this.categoryTransactions.get(category));
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
        System.out.println("in update!");
        if (! this.categoryList.getValue().equals("Select category") && (incomeRadioButton.isSelected() || expenseRadioButton.isSelected()) && !transactionAmountField.getText().equals("") && !transactionDescriptionField.getText().equals("")){
            this.addTransactionButton.setDisable(false);
        }
        else{
            this.addTransactionButton.setDisable(true);
        }
    }

    private boolean isNumeric(String string) {
        return string.matches("\\d*\\.?\\d*") && string.charAt(string.length() - 1) != '.';
    }

    @FXML
    void handleAddTransactionButton() {
        String description = this.transactionDescriptionField.getText();
        String amountString = this.transactionAmountField.getText();
        if (amountString.length() == 0) {
            notify("Amount field is empty");
            return;
        }
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
        updateCategoryView();
        clearAmountAndDescriptionFields();
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
        this.getAccount().addTransaction(new Income(description, amount, category));
        this.incomeView.getItems().add(0, "+ " + amount + "    " + description);
        retrieveCategorySum();
    }

    @FXML
    void handleExpense(String description, double amount, String category) {
        this.getAccount().addTransaction(new Expense(description, amount, category));
        this.expenseView.getItems().add(0, "- " + amount + "    " + description);
        retrieveCategorySum();
    }

}
