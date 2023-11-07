package ui;

import core.User;

/**
 * General code for the controllers in the sub window of the app.
 */
public abstract class AbstractSubController {

    protected AppController parentController;

    
    public User getUser() {
        return parentController.getUser();
    }
    
    public void setParentController(AppController appController) {
        this.parentController = appController;
    }
    
    abstract void init();
}
