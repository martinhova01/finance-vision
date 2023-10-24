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
import javafx.scene.layout.HBox;
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
    private Label usedTotal;
    @FXML
    private Label limitTotal;
    @FXML
    private ProgressBar progressBarTotal;



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
        HBox cellCategory = new HBox(10);
        cellCategory.getStyleClass().add("budgetCell");
        cellCategory.getChildren().addAll(categoryField);
        grid.add(cellCategory, 0, r);

        Label amountTextField = new Label(amount.toString());
        HBox cellAmount = new HBox(10);
        cellAmount.getStyleClass().add("budgetCell");
        cellAmount.getChildren().addAll(amountTextField);
        grid.add(cellAmount, 1, r);

        String limitStr;
        ProgressBar progressBar = new ProgressBar();
        progressBar.setId("bar" + r);

        limitStr = limit.toString();
        String color = "green";
        if (amount / limit > 1) {
            color = "red";
        }
        progressBar.setProgress(amount / limit);
        progressBar.setStyle("-fx-accent: " + color + ";");
        HBox cellBar = new HBox(10);
        cellBar.getStyleClass().add("budgetCell");
        cellBar.getChildren().addAll(progressBar);
        grid.add(cellBar, 3, r);
        

        Label limTextField = new Label(limitStr);
        limTextField.setPrefWidth(100);
        limTextField.setId("limit" + r);

        grid.add(limTextField, 2, r);

    }

    /**
     * Display the budget stored in the user to the screen.
     * This method is called after the user is set
     */
    @Override
    public void init() {
        double totalSum = 0.0;
        double totalLimit = 0.0;

        scrollPane.setContent(grid);
        for (int i = 0; i < user.getBudget().getCategories().size(); i++) {
            String category = user.getBudget().getCategories().get(i);
            
            Double limit = user.getBudget().getLimit(category);
            double categorySum = getCategorySum(category);
            totalSum += categorySum;
            totalLimit += limit;

            addRow(i, category, categorySum, limit);
        }
        usedTotal.setText("" + totalSum);
        limitTotal.setText("" + totalLimit);
        
        String color = "green";
        if (totalSum / totalLimit > 1) {
            color = "red";
        }
        progressBarTotal.setProgress(totalSum / totalLimit);
        progressBarTotal.setStyle("-fx-accent: " + color + ";");
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
