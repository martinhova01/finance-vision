package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App.
 */
public class App extends Application {

    private static  Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        setPrimaryStage(stage);
        primaryStage.setTitle("Finance Vision");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void setPrimaryStage(Stage stage) {
        App.primaryStage = stage;
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}