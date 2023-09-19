package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AbstractController {

  protected Stage stage = App.getPrimaryStage();
  protected Scene scene;
  protected Parent root;

  public void switchScene(String fxmlFileName) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
    root = loader.load();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  static void notify(String message) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("WARNING");
      alert.setHeaderText(message);
      alert.showAndWait();
  }
  
}
