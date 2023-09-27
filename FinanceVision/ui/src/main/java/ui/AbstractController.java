package ui;

import java.io.IOException;

import core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public abstract class AbstractController {

  protected Stage stage = App.getPrimaryStage();
  protected Scene scene;
  protected Parent root;
  protected User user;

  public void switchScene(String fxmlFileName) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
    root = loader.load();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void setUser(User user){
    this.user = user;
  }


  public void switchScene(String fxmlFileName, User user) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        root = loader.load();
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setUser(user);
  }

  @FXML
  static void notify(String message, AlertType type) {
      Alert alert = new Alert(type);
      if (type.equals(AlertType.WARNING)) {
        alert.setTitle("WARNING");
      }
      else {
        alert.setTitle("ERROR");
      }
      alert.setHeaderText(message);
      alert.showAndWait();
  }
  
}
