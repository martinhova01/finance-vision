package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Account;
import core.FinanceVisionModel;
import core.User;
import filesaving.JsonFileSaving;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Testing of REST-api endpoints.
 */
@SpringBootTest(classes = {FinanceVisionApplication.class, FinanceVisionController.class,
    FinanceVisonService.class}, webEnvironment = WebEnvironment.DEFINED_PORT)
public class FinanceVisionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private JsonFileSaving filesaving = new JsonFileSaving();

    private static final String testFilepath = System.getProperty("user.home")
              + System.getProperty("file.separator") + "testdata.json";
    private static final String filePath = System.getProperty("user.home")
              + System.getProperty("file.separator") + "testdata.json";


    private String getUrl() {
        return "http://localhost:" + port + "/fv/";
    }

    /**
     * Deletes the testdata file and sets the filepath for filesaving.
     */
    @BeforeAll
    public static void setup() {
        deleteTestfile();
        JsonFileSaving.setFilepath(testFilepath);

    }

    /**
     * Clean up after the tests by deleting the temporary testdata file.
     */
    @AfterEach
    public void cleanUp() {
        deleteTestfile();
    }

    @BeforeEach
    public void setFilepath() {
        JsonFileSaving.setFilepath(filePath);
    }

    @Test void testIsConnected() {
        assertTrue(
            testRestTemplate.getForObject(getUrl(), Boolean.class));
    }


    @Test
    public void testUserExists() throws IOException {
        assertFalse(
            testRestTemplate.getForObject(getUrl() + "user/testuser/exists", Boolean.class));

        User user = new User(
            "testuser", "password", "Test User", "test@valid.com", new Account(1000));
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        filesaving.writeModel(model);
        assertTrue(testRestTemplate.getForObject(getUrl() + "user/testuser/exists", Boolean.class));

    }

    @Test
    public void testGetUser() throws IOException {
        assertNull(testRestTemplate.getForObject(
            getUrl() + "user/testuser?password=password", User.class));

        User user = new User(
            "testuser", "password", "Test User", "test@valid.com", new Account(1000));
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        filesaving.writeModel(model);
      
        assertNull(testRestTemplate.getForObject(
            getUrl() + "user/testuser?password=wrongpassword", User.class));
        assertEquals(testRestTemplate.getForObject(
            getUrl() + "user/testuser?password=password", User.class), user);
    }
    
    @Test
    public void testPutUser() throws IOException {
        User user = new User(
            "testuser", "password", "Test User", "test@valid.com", new Account(1000));

        
        testRestTemplate.put(getUrl() + "/user/testuser", user);
        assertTrue(filesaving.readModel().containsUser("testuser"));

    }

    @Test
    public void testRemoveUser() throws IOException {
        User user = new User(
            "testuser", "password", "Test User", "test@valid.com", new Account(1000));
        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(user);
        filesaving.writeModel(model);
        testRestTemplate.delete(getUrl() + "user/testuser");
        assertFalse(filesaving.readModel().containsUser("testuser"));
    }

    private static void deleteTestfile() {
        try {
            if ((new File(filePath)).exists()) {
                Files.delete(Path.of(filePath));
            }
        } catch (IOException e) {
            System.err.println("Error deleting file");
        }
    }
}
