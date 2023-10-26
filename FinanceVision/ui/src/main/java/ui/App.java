package ui;

import filesaving.JsonFileSaving;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App.
 */
public class App extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        setPrimaryStage(stage);
        primaryStage.setScene(scene);
        primaryStage.show();

        AbstractController controller = loader.getController();
        controller.setModelAccess(new DirectFinanceVisionModelAccess(new JsonFileSaving()));
        controller.setStage(stage);
        controller.setScene(scene);
        controller.init();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}