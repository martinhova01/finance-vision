package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RemoteApp extends Application{

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("remoteApp.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        setPrimaryStage(stage);
        primaryStage.setScene(scene);
        primaryStage.show();

        AbstractController controller = loader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.init();
  }

  private void setPrimaryStage(Stage stage) {
    this.primaryStage = stage;
    }

  public static void main(String[] args) {
    launch(RemoteApp.class, args);
  }
}
