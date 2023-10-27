package ui;

import core.Expense;
import core.Income;
import core.Transaction;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controller for adding a transaction.
 */
public class AddTransactionController extends AbstractController {
    
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField amountField;
    @FXML
    private RadioButton incomeRadioButton;
    @FXML
    private RadioButton expenseRadioButton;
    @FXML
    private Button addTransactionButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> categoryList;
    @FXML
    private DatePicker datePicker;

    
    @FXML
    void handleRbtnClicked() {

        if (incomeRadioButton.isSelected()) {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(core.User.defaultIncomeCategories);
        } else {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(user.getBudget().getCategories());

            //add the additional categories for this user
        }

    }

    @FXML
    void handleAddTransactionButton() throws IOException {
        try {
            String description = descriptionField.getText();
            String amountString = amountField.getText();
            double amount = Double.parseDouble(amountString);
            String category = this.categoryList.getValue();
            LocalDateTime time = datePicker.getValue().atStartOfDay();

            Transaction t;
            if (incomeRadioButton.isSelected()) {
                t = new Income(description, amount, category, time);
            } else {
                t = new Expense(description, amount, category, time);
            }
            user.getAccount().addTransaction(t);
            saveToFile();

        } catch (Exception e) {
            //TODO: tell the user which field is invalid or empty
            notify("One or more fields are empty or contains invalid data", AlertType.WARNING);
            return;
        }

        switchScene("App.fxml", user);
    }

    @FXML
    void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }

    @Override
    public void init() {
        datePicker.setValue(LocalDate.now());
    }
}
