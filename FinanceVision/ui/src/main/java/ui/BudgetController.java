package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BudgetController extends AbstractController {

  @FXML
  private Button backButton;


  @FXML
  void handleBack() throws IOException{
    switchScene("App.fxml", user);
  }
  
}
