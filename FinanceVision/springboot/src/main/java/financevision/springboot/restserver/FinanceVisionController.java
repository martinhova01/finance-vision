package financevision.springboot.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.FinanceVisionModel;
import core.User;

@RestController
@RequestMapping(FinanceVisionController.FINANCE_VISION_SERVICE_PATH)
public class FinanceVisionController {
    
    public static final String FINANCE_VISION_SERVICE_PATH = "fv";

    
    private FinanceVisonService financeVisionService;
    

    @Autowired
    public FinanceVisionController(FinanceVisonService financeVisionService) {
        this.financeVisionService = financeVisionService;
    }

    @GetMapping
    public FinanceVisionModel getFinanceVisionModel() {
        return financeVisionService.getModel();
    }

    /**
     * Get the user with the given username.
     * 
     * @param name the username of the user
     * @return the user to get or null if not found
     */
    @GetMapping(path = "/user/{name}")
    public User getUser(@PathVariable("name") String name) {
        return getFinanceVisionModel().getUser(name);
    }

    /**
     * Add or replace a user
     * 
     * @param name the username of the user
     * @param user the user to put
     */
    @PutMapping(path = "/user/{name}")
    public void putUser(@PathVariable("name") String name, @RequestBody User user) {
        getFinanceVisionModel().putUser(user);
    }

    /**
     * Delete a user.
     * 
     * @param name the username of the user to delete
     */
    @DeleteMapping(path = "/user/{name}")
    public void removeUser(@PathVariable("name") String name) {
        getFinanceVisionModel().removeUser(getFinanceVisionModel().getUser(name));
    }

}
