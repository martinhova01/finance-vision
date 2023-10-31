package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AppController extends AbstractController {

    @FXML
    private Label transactionLabel, budgetLabel, userSettingsLabel;
    //private Transaction selectedTransaction;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane TransactionsPane, budgetPane;

    @Override
    public void init() {
        budgetPane = loadBudgetPane();
        TransactionsPane = loadTransactionsPane();
        // switchBorderPane("Transactions.fxml");
        borderPane.setCenter(loadTransactionsPane());
        transactionLabel.getStyleClass().add("nav-itemPressed");
        budgetLabel.setOnMouseClicked(event -> {
            borderPane.setCenter(budgetPane);
            setDefaultStyleClass();
            budgetLabel.getStyleClass().add("nav-itemPressed");
        });
        transactionLabel.setOnMouseClicked(event -> {
            borderPane.setCenter(TransactionsPane);
            setDefaultStyleClass();
            transactionLabel.getStyleClass().add("nav-itemPressed");
        });



    }



    private void setDefaultStyleClass() {
        transactionLabel.getStyleClass().clear();
        budgetLabel.getStyleClass().clear();
        userSettingsLabel.getStyleClass().clear();
        transactionLabel.getStyleClass().add("nav-item");
        budgetLabel.getStyleClass().add("nav-item");
        userSettingsLabel.getStyleClass().add("nav-item");
    }

    private AnchorPane loadTransactionsPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Transactions.fxml"));
            AnchorPane transactionsPane = loader.load();
            AbstractController controller = loader.getController();
            controller.setStage(stage);
            return transactionsPane;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately. For example, show an error dialog.
            notify();
            return null;
        }


    }


    private AnchorPane loadBudgetPane() {
        try {
            // Load the budget pane from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budget.fxml"));
            AnchorPane budgetPane = loader.load();
            setStage(stage);
            // Scene scene = new Scene(budgetPane);
            // stage.setScene(scene);

            // AbstractController controller = loader.getController();
            // controller.setStage(stage);
            // controller.setFileHandler(fileHandler);
            // controller.init();

    
            // Get the controller for the pane, if needed
            // You can cast this to whatever controller class the FXML file uses
            // BudgetPaneController controller = loader.getController();
    
            // Initialize or set up the controller, if needed
            // controller.someMethod();
    
            return budgetPane;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately. For example, show an error dialog.
            notify();
            return null;
        }
    }

    public void switchBorderPane(String anchorPane) {
        try {
            borderPane.setCenter(loadAnchorPane(anchorPane));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }

    private AnchorPane loadAnchorPane(String anchorPaneLoadIn) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(anchorPaneLoadIn));
        AnchorPane anchorPane = loader.load();
        AbstractController controller = loader.getController();
        controller.setStage(stage);
        return anchorPane;
    }

}
