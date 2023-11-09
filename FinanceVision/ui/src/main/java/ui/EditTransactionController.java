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

/**
 * Controller for editing a transaction.
 */
public class EditTransactionController extends AbstractSubController {

    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    @FXML
    private RadioButton incomeRadioButton;
    @FXML
    private RadioButton expenseRadioButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> categoryList;
    @FXML
    private DatePicker datePicker;


    /**
     * Loads in the transaction to edit.
     */
    @Override
    public void init() {
        if (getTransaction() instanceof Income) {
            incomeRadioButton.setSelected(true);
            handleRbtnClicked();
        } else {
            expenseRadioButton.setSelected(true);
            handleRbtnClicked();
        }
        amountField.setText(getTransaction().getAmount().toString());
        descriptionField.setText(getTransaction().getDescription());
        categoryList.setValue(getTransaction().getCategory());
        datePicker.setValue(getTransaction().getTime().toLocalDate());
    }

    /**
     * Changes the category list when clicking on a radiobutton.
     */
    @FXML
    public void handleRbtnClicked() {
        if (incomeRadioButton.isSelected()) {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(parentController.getUser().getBudget().getCategories());
        } else if (expenseRadioButton.isSelected()) {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(parentController.getUser().getBudget().getCategories());

            //add the additional categories for this user
        }
    }

    public Transaction getTransaction() {
        return parentController.getTransaction();
    }

    /**
     * Saves the changes and directs the user back to the main page.
     *
     * @throws IOException if one or more fields are empty or contains invalid data
     */
    @FXML
    public void handleConfirm() throws IOException {
        Transaction t;
        try {
            String description = descriptionField.getText();
            String amountString = amountField.getText();
            double amount = Double.parseDouble(amountString);
            String category = this.categoryList.getValue();
            LocalDateTime time = datePicker.getValue().atStartOfDay();

            if (incomeRadioButton.isSelected()) {
                t = new Income(description, amount, category, time);
            } else {
                t = new Expense(description, amount, category, time);
            }

        } catch (Exception e) {
            parentController.notify(
                "One or more fields are empty or contains invalid data", AlertType.WARNING);
            return;
        }

        parentController.getUser().getAccount().removeTransaction(getTransaction());
        parentController.getUser().getAccount().addTransaction(t);
        parentController.saveToFile();
        
        parentController.switchBorderPane("transactions.fxml");
    }

    @FXML
    public void handleBack() throws IOException {
        parentController.switchBorderPane("transactions.fxml");
    }
    
}
