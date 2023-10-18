package ui;

import java.io.IOException;

import core.Budget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class EditBudgetController extends AbstractController {

    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;
    @FXML
    private VBox categoryBox;
    @FXML
    private VBox limitBox;


    @Override
    public void init() {
        for (int i = 0; i < user.getBudget().getCategories().size(); i++) {
            String category = user.getBudget().getCategories().get(i);
            TextField categoryField = new TextField(category);
            categoryField.setId("category" + i);
            categoryBox.getChildren().addAll(categoryField);

            TextField limitField = new TextField("" + user.getBudget().getLimit(category));
            limitField.setId("limit" + i);
            limitBox.getChildren().addAll(limitField);
        }
    }

    @FXML
    private void handleBack() throws IOException {
        switchScene("budget.fxml", user);
    }

    @FXML
    private void handleConfirm() throws IOException {
        Budget b = new Budget();
        for (int i = 0; i < user.getBudget().getCategories().size(); i++) {
            String category = ((TextField)scene.lookup("#category" + i)).getText();
            Double limit;
            try {
                limit = Double.parseDouble(((TextField)scene.lookup("#limit" + i)).getText());
              
            } catch (Exception e) {
                notify("invalid limit on line " + (i + 1), AlertType.WARNING);
                return;
            }
            b.addCategory(category, limit);

        }
        user.setBudget(b);
        saveToFile();
        switchScene("budget.fxml", user); 
    }

  
}
