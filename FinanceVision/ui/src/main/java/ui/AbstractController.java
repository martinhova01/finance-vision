package ui;

import core.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * An abstract controller-class that handles notifications, filesaving and switching scenes. 
 */
public abstract class AbstractController {

    protected Stage stage;
    protected Scene scene;
    protected Parent root;
    protected User user;
    protected FinanceVisionModelAccess modelAccess;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModelAccess(FinanceVisionModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }

    public FinanceVisionModelAccess getModelAccess() {
        return modelAccess;
    }

    /**
     * Switches scene to a new fxml file.
     *
     * @param fxmlFileName the fxml file to switch to
     * @throws IOException if the file is not found
     */
    public void switchScene(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setStage(stage);
        controller.setModelAccess(modelAccess);
        controller.init();
    }

    /**
     * Switches scene to a new fxml file and keeps the curent user logged in.
     *
     * @param fxmlFileName the fxml file to switch to
     * @param user the current user logged in
     * @throws IOException if the fxml file is not found
     */
    public void switchScene(String fxmlFileName, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        root = loader.load();
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AbstractController controller = loader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        controller.setUser(user);
        controller.setModelAccess(modelAccess);
        controller.init();
    }

    
    /**
     * Sends a notification to the user.
     *
     * @param message the text in the notification
     * @param type the type of the warning
     */
    public void notify(String message, AlertType type) {
        Alert alert = new Alert(type);
        if (type.equals(AlertType.WARNING)) {
            alert.setTitle("WARNING");
        } else if (type.equals(AlertType.WARNING)) {
            alert.setTitle("ERROR");
        } else {
            alert.setTitle("INFORMATION");
        }
        alert.setHeaderText(message);
        alert.showAndWait();
    }


    /**
     * Saves updates done to the current user to the file data.json.
     */
    public void saveToFile() {
        try {
            modelAccess.putUser(user);
        } catch (Exception e) {
            notify("Something went wrong when saving update.", AlertType.WARNING);
        }
    }



    public abstract void init();
  
}
