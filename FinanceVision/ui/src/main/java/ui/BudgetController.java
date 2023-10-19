package ui;

import core.Transaction;
import java.io.IOException;
import java.time.YearMonth;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Controller for creating a budget.
 */
public class BudgetController extends AbstractController {

    @FXML
    private Button backButton;
    @FXML
    private Button editBudgetButton;
    @FXML 
    private GridPane grid;
    @FXML
    private ScrollPane scrollPane;



    @FXML
    void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }

    /**
     * Adds a new row to the table of budget-categories.
     *
     * @param r the index of the row to add
     * @param category the category of the row to add
     * @param amount the amount the user has used in this category
     * @param limit the budget-limit the user has set for this category. null if not yet set
     */
    private void addRow(int r, String category, Double amount, Double limit) {
        RowConstraints row = new RowConstraints(35);
        grid.getRowConstraints().add(row);

        Label categoryField = new Label(category);
        categoryField.setId("category" + r);
        grid.add(categoryField, 0, r);

        Label amountTextField = new Label(amount.toString());
        grid.add(amountTextField, 1, r);

        String limitStr;
        ProgressBar progressBar = new ProgressBar();
        progressBar.setId("bar" + r);

        if (limit == null) {
            limitStr = "limit not set";
            progressBar.setProgress(0.0);
        } else {
            limitStr = limit.toString();
            String color = "green";
            if (amount / limit > 1) {
                color = "red";
            }
            progressBar.setProgress(amount / limit);
            progressBar.setStyle("-fx-accent: " + color + ";");
        }

        Label limTextField = new Label(limitStr);
        limTextField.setPrefWidth(100);
        limTextField.setId("limit" + r);

        grid.add(limTextField, 2, r);

        grid.add(progressBar, 3, r);
    }

    /**
     * Display the budget stored in the user to the screen.
     * This method is called after the user is set
     */
    @Override
    public void init() {

        scrollPane.setContent(grid);
        for (int i = 0; i < user.getBudget().getCategories().size(); i++) {
            String category = user.getBudget().getCategories().get(i);
            
            Double limit = user.getBudget().getLimit(category);
            double categorySum = getCategorySum(category);

            addRow(i, category, categorySum, limit);
        }
    }

    /**
     * Gets the sum of the expenses of a given category.
     * Only include the expenses from the current month.
     *
     * @param category the category to sum up
     * @return the sum of the categories
     */
    private double getCategorySum(String category) {
        double categorySum = 0;
        for (Transaction transaction : user.getAccount().getTransactions(
            t -> t.getCategory().equals(category)
            && YearMonth.from(t.getTime()).equals(YearMonth.now()))) {
            categorySum += transaction.getAmount();
        }

        return categorySum;
    }

    @FXML
    private void handleEditBudget() throws IOException {
        switchScene("editBudget.fxml", user);
    }
}
