package financevision.springboot.restserver;

import core.FinanceVisionModel;
import core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that defines the REST api for the Finance Vision model.
 * This includes GET, PUT and DELETE methods.
 */
@RestController
public class FinanceVisionController {
    
    private FinanceVisonService financeVisionService;

    @Autowired
    public FinanceVisionController(FinanceVisonService financeVisionService) {
        this.financeVisionService = financeVisionService;
    }

    public FinanceVisionModel getFinanceVisionModel() {
        return financeVisionService.getModel();
    }

    /**
     * Get the user with the given username and password.
     *
     * @param name the username of the user
     * @param password the password of the user
     * @return the user to get or null if not found
     */
    @GetMapping(path = "/user/{name}")
    public User getUser(@PathVariable("name") String name,
        @RequestParam String password) {

        User user = getFinanceVisionModel().getUser(name);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Add or replace a user.
     *
     * @param name the username of the user
     * @param user the user to put
     * @returns true if this method is called whithout error
     */
    @PutMapping(path = "/user/{name}")
    public void putUser(@PathVariable("name") String name, @RequestBody User user) {
        getFinanceVisionModel().putUser(user);
        financeVisionService.saveModel();
    }

    /**
     * Delete a user.
     *
     * @param name the username of the user to delete
     * @returns true if this method is called whithout error
     */
    @DeleteMapping(path = "/user/{name}")
    public void removeUser(@PathVariable("name") String name) {
        getFinanceVisionModel().removeUser(getFinanceVisionModel().getUser(name));
        financeVisionService.saveModel();
    }

    
    /**
     *Checks if username exists in the model.
     *
     * @param username the username to check
     * @return true id user exists
     */
    @GetMapping(path = "/user/{username}/exists")
    public boolean containsUser(@PathVariable("username") String username) {
        return getFinanceVisionModel().containsUser(username);
    }
}
