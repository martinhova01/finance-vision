package core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds contains the user data for 
 * a Finance Vision instance.
 */
public class FinanceVisionModel {
    private List<User> users;

    public FinanceVisionModel() {
        this.users = new ArrayList<>();
    }

    /**
     * Adds a user to the list of users or replace the user with the same username.
     *
     * @param user the user to be added
     */
    public void putUser(User user) {
        int i = users.indexOf(user);
        if (i > -1) {
            users.set(i, user);
        } else {
            users.add(user);
        }
    }

    /**
     * Removes the user from the list of users.
     * If user not in the list, it does nothing.
     *
     * @param user the user to be removed
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    /**
     * Checks if a username is taken.
     *
     * @param username the username to check
     * @return true if the username is allready taken
     */
    public boolean containsUser(String username) {
        return getUsernames().contains(username);
    }

    /**
     * Gets a list of all the usernames.
     *
     * @return a list of all usernames
     */
    public List<String> getUsernames() {
        return users.stream().map(u -> u.getUsername()).toList();
    }

   
    /**
     * Gets a user based on username.
     *
     * @param username the user to find
     * @return the user or null if not found
     */
    public User getUser(String username) {
        if (!containsUser(username)) {
            throw new IllegalArgumentException("User not found.");
        }
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Gets a list of all users.
     *
     * @return a copy of the list of users
     */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
    
}
