package ui;

import core.User;
import filesaving.FileHandler;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    protected FileHandler fileHandler;

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

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
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
        controller.setFileHandler(fileHandler);
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
        controller.setFileHandler(fileHandler);
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
            List<User> users = fileHandler.deserializeUsers(new File(System.getProperty(
                "user.home") + "/data.json"));
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(this.user.getUsername())) {
                    users.set(i, this.user);
                }
            }
            fileHandler.serializeUsers(users, new File(System.getProperty(
                "user.home") + "/data.json"));
        } catch (IOException e) {
            notify("File not found", AlertType.ERROR);
        }
    }



    public abstract void init();
  
}
