package ui;

import core.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AppController {

    @FXML
    private TextField balance_view;

    @FXML
    private TextField income_field;

    @FXML
    private TextField expense_field;

    @FXML
    private Button income_button;

    @FXML
    private Button expense_button;

    @FXML
    private ListView<Double> income_view;
    
    @FXML
    private ListView<Double> expense_view;


    private Account account;
    
    public AppController() {
        this.account = new Account(0);
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private void updateBalanceView() {
        //double balance = Double.parseDouble(balance_view.getText());
        balance_view.setText(String.valueOf(getAccount().getBalance()));
    }


}
