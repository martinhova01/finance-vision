package ui;

import core.Transaction;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Main controller for the app.
 * Holds the top nav-menu.
 */
public class AppController extends AbstractController {

    @FXML
    private Label transactionLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label userSettingsLabel;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField balanceField;

    private Transaction transaction;

    /**
     * Shows total bank balance and list of transactions.
     */
    @Override
    public void init() {

        switchBorderPane("Transactions.fxml");
        transactionLabel.getStyleClass().add("nav-itemPressed");
        budgetLabel.setOnMouseClicked(event -> {
            switchBorderPane("budget.fxml");
            setDefaultStyleClass();
            budgetLabel.getStyleClass().add("nav-itemPressed");
        });
        transactionLabel.setOnMouseClicked(event -> {
            switchBorderPane("Transactions.fxml");
            setDefaultStyleClass();
            transactionLabel.getStyleClass().add("nav-itemPressed");
        });
        userSettingsLabel.setOnMouseClicked(event -> {
            switchBorderPane("userSettings.fxml");
            setDefaultStyleClass();
            userSettingsLabel.getStyleClass().add("nav-itemPressed");
        });
        updateBalanceField();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    private void setDefaultStyleClass() {
        transactionLabel.getStyleClass().clear();
        budgetLabel.getStyleClass().clear();
        userSettingsLabel.getStyleClass().clear();
        transactionLabel.getStyleClass().add("nav-item");
        budgetLabel.getStyleClass().add("nav-item");
        userSettingsLabel.getStyleClass().add("nav-item");
    }


    /**
     * Switches the content of the window below the nav menu. 
     *
     * @param fxmlFileName the filename of the fxml-file to load.
     */
    public void switchBorderPane(String fxmlFileName) {
        try {
            borderPane.setCenter(loadAnchorPane(fxmlFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateBalanceField();
    }

    private void updateBalanceField() {
        balanceField.setText(String.valueOf(Math.round(this.user.getAccount().getBalance())));
        this.balanceField.setFocusTraversable(false);
    }

    private AnchorPane loadAnchorPane(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        AnchorPane anchorPane = loader.load();
        AbstractSubController controller = loader.getController();
        controller.setParentController(this);
        controller.init();
        return anchorPane;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
