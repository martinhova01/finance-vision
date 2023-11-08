package ui;

import core.User;

/**
 * An interface that defines how the ui is connected to the model.
 * 
 */
public interface FinanceVisionModelAccess {

    /**
     * Checks if the model is accessable.
     *
     * @return true if successfull
     * @throws Exception is model can not be reached
     */
    public boolean isConnected() throws Exception;

    /**
     * Adds or replaces a user.
     *
     * @param user the user to put
     * @throws Exception if something went wrong
     */
    public void putUser(User user) throws Exception;

    /**
     * Remove a user.
     * Does nothing if the user is not in the model.
     *
     * @param user the user to remove
     * @throws Exception if something went wrong
     */
    public void removeUser(User user) throws Exception;

    /**
     * Check if a user with a given name exits.
     *
     * @param username the username to check
     * @return true if the user exists
     * @throws Exception if something went wrong
     */
    public boolean containsUser(String username) throws Exception;

    
    /**
     * Gets a user based on username.
     *
     * @param username the username to get
     * @return the user or null if user not found
     *
     * @throws Exception if something went wrong
     */
    public User getUser(String username, String password) throws Exception;

}
