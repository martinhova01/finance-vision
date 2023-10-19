package ui;

import java.io.IOException;

import core.Budget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
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
    @FXML
    private ScrollPane scrollPane;

    private Button addButton;

    private int rowNumber;


    @Override
    public void init() {
        for (int i = 0; i < user.getBudget().getCategories().size(); i++) {
            String category = user.getBudget().getCategories().get(i);
            double limit = user.getBudget().getLimit(category);
            addRow(category, limit, i);
        }
        HBox container = new HBox();
        container.getChildren().addAll(categoryBox, limitBox);
        scrollPane.setContent(container);

        this.rowNumber = user.getBudget().getCategories().size();
        addButton = new Button("+");
        addButton.setOnMouseClicked(e -> {
            handleAddButton();
        });
        categoryBox.getChildren().add(addButton);
    }

    private void addRow(String category, double limit, int rowNumber) {
        Button removeButton = new Button("-");
        removeButton.setId("remove" + rowNumber);
        removeButton.setOnMouseClicked(e -> {
            handleRemoveButton(rowNumber);
        });

        TextField categoryField = new TextField(category);
        categoryField.setId("category" + rowNumber);
        
        HBox categorContainer = new HBox(10);
        categorContainer.setId("categoryContainer" + rowNumber);
        categorContainer.getChildren().addAll(removeButton, categoryField);
        categoryBox.getChildren().add(categorContainer);
        
        TextField limitField = new TextField("" + limit);
        limitField.setId("limit" + rowNumber);
        limitBox.getChildren().add(limitField);
        
        categoryField.requestFocus();
    }

    private void handleAddButton() {
        categoryBox.getChildren().remove(addButton);
        addRow("", 0, this.rowNumber);
        this.rowNumber++;
        categoryBox.getChildren().add(addButton);
    }

    private void handleRemoveButton(int rowNumber) {
        categoryBox.getChildren().remove((HBox)scene.lookup("#categoryContainer" + rowNumber));
        limitBox.getChildren().remove((TextField)scene.lookup("#limit" + rowNumber));
    }

    @FXML
    private void handleBack() throws IOException {
        switchScene("budget.fxml", user);
    }

    @FXML
    private void handleConfirm() throws IOException {
        Budget b = new Budget();
        for (int i = 0; i < rowNumber; i++) {

                //the row has been removed
            if (scene.lookup("#category" + i) == null) {
                continue;
            }
            String category = ((TextField)scene.lookup("#category" + i)).getText();
            Double limit;
            try {
                limit = Double.parseDouble(((TextField)scene.lookup("#limit" + i)).getText());
              
            } catch (Exception e) {
                notify("invalid limit", AlertType.WARNING);
                return;
            }
            try {
                b.addCategory(category, limit);
            } catch (IllegalArgumentException e) {
                notify(e.getMessage(), AlertType.WARNING);
                return;
            }

        }
        user.setBudget(b);
        saveToFile();
        switchScene("budget.fxml", user); 
    }

  
}
