package ui;

import core.Budget;
import core.Transaction;
import core.User;
import java.io.IOException;
import java.time.YearMonth;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class BudgetController extends AbstractController {

    @FXML
    private Button backButton;

    @FXML 
    private GridPane grid;

    // private int maxRows = 10; //used if we add more categories


    @FXML
    void handleBack() throws IOException {
        switchScene("App.fxml", user);
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
        init();
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

        TextField categoryField = new TextField(category);
        categoryField.setId("category" + r);
        categoryField.setEditable(false);
        grid.add(categoryField, 0, r);

        TextField amountTextField = new TextField(amount.toString());
        amountTextField.setEditable(false);
        grid.add(amountTextField, 1, r);

        String limitStr;
        ProgressBar progressBar = new ProgressBar();
        progressBar.setId("bar" + r);

        if (limit == null) {
            limitStr = "limit not set";
            progressBar.setProgress(0.0);
        }
        else {
            limitStr = limit.toString();
            String color = "green";
            if (amount / limit > 1) {
              color = "red";
            }
            progressBar.setProgress(amount / limit);
            progressBar.setStyle("-fx-accent: " + color + ";");
        }

        HBox limitBox = new HBox(10);
        TextField limTextField = new TextField(limitStr);
        limTextField.setEditable(false);
        limTextField.setPrefWidth(100);
        limTextField.setId("limit" + r);

        Button editButton = new Button("edit");
        editButton.setId("" + r);
        editButton.setOnMouseClicked((e) -> {
            editLimit(e.getSource());
        });

        limitBox.getChildren().addAll(limTextField, editButton);
        grid.add(limitBox, 2, r);

        grid.add(progressBar, 3, r);
    }

    
    /**
     * Makes it possible for the user to edit the limit of a given budget-category.
     * 
     * @param clicked the button that is clicked
     */
    private void editLimit(Object clicked) {
        Button b = (Button) clicked;
        b.setText("set");

        TextField limitTextField = (TextField)scene.lookup("#limit" + b.getId());
        limitTextField.setEditable(true);
        limitTextField.requestFocus();

        b.setOnMouseClicked((e) -> {
          setLimit(e.getSource(), limitTextField);
        });
    }

    /**
     * Updates the limit of a given category and saves the changes.
     * 
     * @param clicked the button that was clicked
     * @param limitTextField the TextField to get the limit value from
     */
    private void setLimit(Object clicked, TextField limitTextField) {
        double limit;
        try {
            limit = Double.parseDouble(limitTextField.getText());
        } catch (Exception e) {
            notify("invalid limit", AlertType.WARNING);
            limitTextField.requestFocus();
            return;
        }
        Button b = (Button) clicked;
        b.setText("edit");
        b.setOnMouseClicked((e) -> {
            editLimit(e.getSource());
        });
        int row = Integer.parseInt(b.getId());

        limitTextField.setEditable(false);

        if (user.getBudget() == null) {
            user.setBudget(new Budget());
        }
        String category = ((TextField)scene.lookup("#category" + b.getId())).getText();
        user.getBudget().addCategory(category, limit);
        
        saveToFile();

        updateProgressBar(row, category);

    }

    /**
     * Update the progressbar at a given row.
     * 
     * @param row the row to update
     * @param category the category of the row
     */
    private void updateProgressBar(int row, String category) {
        ProgressBar bar = (ProgressBar)scene.lookup("#bar" + row);
        double used = getCategorySum(category);
        double limit = user.getBudget().getLimit(category);
        double percentage = used / limit;
        bar.setProgress(percentage);

        String color = "green";
        if (percentage> 1) {
            color = "red";
        }
        bar.setStyle("-fx-accent: " + color + ";");
    }

    /**
     * Display the budget stored in the user to the screen.
     * This method is called after the user is set
     */
    private void init() {

        for (int i = 0; i < core.User.defaultExpenseCategories.size(); i++) { {
          String category = core.User.defaultExpenseCategories.get(i);
          
          Double limit;
          if (user.getBudget() == null) {
              limit = null;
          }
          else if (!user.getBudget().getCategories().contains(category)) {
              limit = null;
          }
          else {
              limit = user.getBudget().getLimit(category);
          }
          double categorySum = getCategorySum(category);

          addRow(i + 1, category, categorySum, limit);
      
        }
        }
    }

    /**
     * Gets the sum of the expenses of a given category.
     * Only include the expenses from the current month
     * 
     * @param category the category to sum up
     * @return the sum of the categories
     */
    private double getCategorySum(String category) {
        double categorySum = 0;
        for (Transaction transaction : user.getAccount().getTransactions(t -> t.getCategory().equals(category)
        && YearMonth.from(t.getTime()).equals(YearMonth.now()))) {
          categorySum += transaction.getAmount();
        }

        return categorySum;
    }
}
