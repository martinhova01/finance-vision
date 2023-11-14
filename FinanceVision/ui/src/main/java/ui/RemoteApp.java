package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Javafx app using remote endpoint.
 */
public class RemoteApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("remoteApp.fxml"));
        Parent root = loader.load();

        Image icon = new Image(getClass().getResourceAsStream("img/icon.png"));
        stage.getIcons().add(icon);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.init();
    }

    public static void main(String[] args) {
        launch(RemoteApp.class, args);
    }
}
