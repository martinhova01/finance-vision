package ui;

import core.Expense;
import core.Income;
import core.Transaction;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class EditTransactionController extends AbstractController {

    @FXML
    private TextField amountField, descriptionField;
    @FXML
    private RadioButton incomeRadioButton, expenseRadioButton;
    @FXML
    private Button confirmButton, backButton;
    @FXML
    private ChoiceBox<String> categoryList;
    @FXML
    private DatePicker datePicker;

    @Override
    public void setTransaction(Transaction t) {
        super.setTransaction(t);
        init();

    }
    public void init() {
        if (transaction instanceof Income) {
            incomeRadioButton.setSelected(true);
            categoryList.getItems().addAll(core.User.defaultIncomeCategories);
        }
        else {
            expenseRadioButton.setSelected(true);
            categoryList.getItems().addAll(core.User.defaultExpenseCategories);
        }
        amountField.setText(transaction.getAmount().toString());
        descriptionField.setText(transaction.getDescription());
        categoryList.setValue(transaction.getCategory());
        datePicker.setValue(transaction.getTime().toLocalDate());
    }

    @FXML
    public void handleRbtnClicked() {
        if (incomeRadioButton.isSelected()){
            categoryList.getItems().clear();
            categoryList.getItems().addAll(core.User.defaultIncomeCategories);
        }
        else if (expenseRadioButton.isSelected()) {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(core.User.defaultExpenseCategories);

            //add the additional categories for this user
        }
    }

    @FXML
    public void handleConfirm() throws IOException {
        try {
            user.getAccount().removeTransaction(transaction);

            String description = descriptionField.getText();
            String amountString = amountField.getText();
            double amount = Double.parseDouble(amountString);
            String category = this.categoryList.getValue();
            LocalDateTime time = datePicker.getValue().atStartOfDay();

            Transaction t;
            if (incomeRadioButton.isSelected()) {
                t = new Income(description, amount, category, time);
            }
            else {
                t = new Expense(description, amount, category, time);
            }
            user.getAccount().addTransaction(t);
            saveToFile();
        }
        catch (Exception e) {
            notify("One or more fields are empty or contains invalid data", AlertType.WARNING);
            return;
        }
        
        switchScene("App.fxml", user);
    }

    @FXML
    public void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }
    
}
