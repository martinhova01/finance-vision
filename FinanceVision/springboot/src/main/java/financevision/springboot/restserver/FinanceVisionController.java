package financevision.springboot.restserver;

import core.FinanceVisionModel;
import core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that defines the REST api for the Finance Vision model.
 * This includes GET, PUT and DELETE methods.
 */
@RestController
@RequestMapping(FinanceVisionController.FINANCE_VISION_SERVICE_PATH)
public class FinanceVisionController {

    public static final String FINANCE_VISION_SERVICE_PATH = "/fv/";
    
    private FinanceVisonService financeVisionService;

    @Autowired
    public FinanceVisionController(FinanceVisonService financeVisionService) {
        this.financeVisionService = financeVisionService;
    }

    public FinanceVisionModel getFinanceVisionModel() {
        return financeVisionService.getModel();
    }


    /**
     * Check if the server is running.
     *
     * @return true if client is succesfully connected to server
     */
    @GetMapping
    public boolean isRunning() {
        return true;
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

        financeVisionService.readModel();
        User user = getFinanceVisionModel().getUser(name);
        if (user == null) {
            return null;
        }
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
        financeVisionService.readModel();
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
        financeVisionService.readModel();
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
        financeVisionService.readModel();
        return getFinanceVisionModel().containsUser(username);
    }
}
