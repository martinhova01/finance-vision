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


    /**
     * Helper method for headless testing.
     */
    public static void supportHeadless() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadLoginScreen.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setModelAccess(new DirectFinanceVisionModelAccess(new JsonFileSaving()));
        controller.setStage(stage);
        controller.setScene(scene);
        controller.init();
    }

    public static void main(String[] args) {
        launch();
    }
}