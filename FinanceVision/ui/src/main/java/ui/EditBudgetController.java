package ui;

import core.Budget;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller for editing the budget.
 */
public class EditBudgetController extends AbstractSubController {

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
        for (int i = 0; i < getUser().getBudget().getCategories().size(); i++) {
            String category = getUser().getBudget().getCategories().get(i);
            double limit = getUser().getBudget().getLimit(category);
            addRow(category, limit, i);
        }
        HBox container = new HBox();
        container.getChildren().addAll(categoryBox, limitBox);
        scrollPane.setContent(container);

        this.rowNumber = getUser().getBudget().getCategories().size();
        addButton = new Button("+");
        addButton.getStyleClass().add("defaultButton");
        addButton.setOnMouseClicked(e -> {
            handleAddButton();
        });
        categoryBox.getChildren().add(addButton);
    }

    private void addRow(String category, double limit, int rowNumber) {
        Button removeButton = new Button("-");
        removeButton.setId("remove" + rowNumber);
        removeButton.getStyleClass().add("defaultButton");
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
        categoryBox.getChildren()
            .remove((HBox) getScene().lookup("#categoryContainer" + rowNumber));
        limitBox.getChildren()
            .remove((TextField) getScene().lookup("#limit" + rowNumber));
    }

    @FXML
    private void handleBack() throws IOException {
        parentController.switchBorderPane("budget.fxml");
    }

    @FXML
    private void handleConfirm() throws IOException {
        Budget b = new Budget();
        for (int i = 0; i < rowNumber; i++) {

            //the row has been removed
            if (getScene().lookup("#category" + i) == null) {
                continue;
            }
            String category = ((TextField) getScene().lookup("#category" + i)).getText();
            Double limit;
            try {
                limit = Double.parseDouble(((TextField) getScene().lookup("#limit" + i)).getText());
              
            } catch (Exception e) {
                parentController.notify("invalid limit", AlertType.WARNING);
                return;
            }
            try {
                b.addCategory(category, limit);
            } catch (IllegalArgumentException e) {
                parentController.notify(e.getMessage(), AlertType.WARNING);
                return;
            }

        }
        getUser().setBudget(b);
        parentController.saveToFile();
        parentController.switchBorderPane("budget.fxml");
    }

    public Scene getScene() {
        return parentController.getScene();
    }

  
}
