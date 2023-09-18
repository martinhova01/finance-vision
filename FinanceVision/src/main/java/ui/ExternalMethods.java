package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExternalMethods {
  
    @FXML
    static void notify(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
