package ui;

import core.FinanceVisionModel;
import core.User;
import filesaving.FileHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FinanceVisionAccess class that directly access a model object.
 */
public class DirectFinanceVisionModelAccess implements FinanceVisionModelAccess {

    private FinanceVisionModel model;
    private FileHandler fileHandler;
    private File saveFile;

    

    /**
     * Init a new object, and read data from filehandler.
     *
     * @param fileHandler the filehandler that takes care of saving data to file
     */
    public DirectFinanceVisionModelAccess(FileHandler fileHandler) {
        this.model = new FinanceVisionModel();
        this.fileHandler = fileHandler;
        List<User> users = null;
        try {
            this.saveFile = new File(System.getProperty("user.home") + "/data.json");
            users = this.fileHandler.deserializeUsers(saveFile);
        } catch (IOException e) {
            users = new ArrayList<>();
            try {
                fileHandler.serializeUsers(users, saveFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        for (User user : users) {
            model.putUser(user);
        }
    }

    @Override
    public FinanceVisionModel getModel() {
        return model;
    }

    @Override
    public void putUser(User user) throws IOException {
        model.putUser(user);
        saveToFile();
    }

    @Override
    public void removeUser(User user) throws IOException {
        model.removeUser(user);
        saveToFile();
    }

    @Override
    public boolean containsUser(String username) {
        return model.containsUser(username);
    }

    @Override
    public List<String> getUsernames() {
        return model.getUsernames();
    }

    @Override
    public User getUser(String username) {
        return model.getUser(username);
    }

    private void saveToFile() throws IOException {
        fileHandler.serializeUsers(getUsers(), saveFile);
    }

    @Override
    public List<User> getUsers() {
        return model.getUsers();
    }

    
  
}
