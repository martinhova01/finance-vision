package ui;

import core.FinanceVisionModel;
import core.User;
import filesaving.FileHandler;
import java.io.IOException;

/**
 * FinanceVisionAccess class that directly access a model object.
 */
public class DirectFinanceVisionModelAccess implements FinanceVisionModelAccess {

    private FinanceVisionModel model;
    private FileHandler fileHandler;

    

    /**
     * Init a new object, and read data from filehandler.
     *
     * @param fileHandler the filehandler that takes care of saving data to file
     */
    public DirectFinanceVisionModelAccess(FileHandler fileHandler) {
        this.model = new FinanceVisionModel();
        this.fileHandler = fileHandler;
        try {
            model = this.fileHandler.readModel();
        } catch (IOException e) {
            model = new FinanceVisionModel();
            try {
                fileHandler.writeModel(model);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
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

    private void saveToFile() throws IOException {
        fileHandler.writeModel(model);
    }

    @Override
    public User getUser(String username, String password) throws Exception {
        User user = model.getUser(username);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean isConnected() throws Exception {
        fileHandler.readModel();
        return true;
    }

  
}
