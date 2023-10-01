package ui;

import java.io.IOException;
import java.util.List;

import core.User;
import fileSaving.JsonFileSaving;
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

  /**
   * Switches scene to a new fxml file
   * 
   * @param fxmlFileName the fxml file to switch to
   * @throws IOException if the file is not found
   */
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

  public void setScene(Scene scene){
    this.scene = scene;
  }


  /**
   * Switches scene to a new fxml file and keeps the cuurent user logged in
   * 
   * @param fxmlFileName the fxml file to switch to
   * @param user the current user logged in
   * @throws IOException if the fxml file is not found
   */
  public void switchScene(String fxmlFileName, User user) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        root = loader.load();
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setScene(scene);
        controller.setUser(user);
  }

  
  /**
   * Sends a notification to the user
   * 
   * @param message the text in the notification
   * @param type the type of the warning
   */
  public void notify(String message, AlertType type) {
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


  /**
   * Saves updates done to the current user to the file data.txt
   * 
   */
  public void saveToFile(){
    try {
            //List<User> users = FileSaving.readFromFile("data.txt");
            List<User> users = JsonFileSaving.deserializeUsers("data.json");
            for (User user : users) {
                if (user.getUsername().equals(this.user.getUsername())) {
                    users.remove(user);
                    users.add(this.user);
                }
            }
            //FileSaving.writeToFile(users, "data.txt");
            JsonFileSaving.serializeUsers(users, "data.json");
        } catch (IOException e) {
            notify("File not found", AlertType.ERROR);
        }
  }
  
}
