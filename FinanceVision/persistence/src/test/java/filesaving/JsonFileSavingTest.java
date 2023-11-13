package filesaving;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Account;
import core.Expense;
import core.FinanceVisionModel;
import core.Income;
import core.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testclass for JsonFileSaving. Test reading from file and writing to file.
 */
public class JsonFileSavingTest {
    private User u1;
    private User u2;
    private User u1Read;
    private User u2Read;
    private Account a1;
    private Account a2;
    private JsonFileSaving fileshandler;
    private static final String filepath = System.getProperty("user.home")
        + System.getProperty("file.separator") + "testdata.json";

    /**
     * Deletes the testdata file and sets the filepath for filesaving.
     */
    @BeforeAll
    public static void setUp() {
        deleteTestfile();
        JsonFileSaving.setFilepath(filepath);
    }

    /**
     * Clean up after the tests by deleting the temporary testdata file.
     */
    @AfterEach
    public void cleanUp() {
        deleteTestfile();
    }

    /**
     * Inits the testobjects before each test by writing and reading from file.
     */
    @BeforeEach
    public void setUpData() throws IOException {

        a1 = new Account(1000);
        a1.addTransaction(new Income("lønn", 500.0, "Food"));
        a1.addTransaction(new Expense("skatt", 200.0, "Food"));
        u1 = new User("martinhova", "password", "Martin Høva", "martirho@stud.ntnu.no", a1);

        a2 = new Account(5000);
        a2.addTransaction(new Income("gave", 700.0, "Food"));
        a2.addTransaction(new Expense("mat", 1500.0, "Food"));
        u2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", a2);

        FinanceVisionModel model = new FinanceVisionModel();
        model.putUser(u1);
        model.putUser(u2);

        JsonFileSaving fileHandler = new JsonFileSaving(filepath);
        fileHandler.writeModel(model);

        FinanceVisionModel readModel = fileHandler.readModel();

        u1Read = readModel.getUsers().get(0);
        u2Read = readModel.getUsers().get(1);

    }

    @Test
    public void testUsername() {
        assertTrue(u1.getUsername().equals(u1Read.getUsername()));
        assertTrue(u2.getUsername().equals(u2Read.getUsername()));
    }

    @Test
    public void testPassword() {
        assertTrue(u1.getPassword().equals(u1Read.getPassword()));
        assertTrue(u2.getPassword().equals(u2Read.getPassword()));
    }

    @Test
    public void testFullName() {
        assertTrue(u1.getFullName().equals(u1Read.getFullName()));
        assertTrue(u2.getFullName().equals(u2Read.getFullName()));
    }

    @Test
    public void testEmail() {
        assertTrue(u1.getEmail().equals(u1Read.getEmail()));
        assertTrue(u2.getEmail().equals(u2Read.getEmail()));
    }

    @Test
    public void testAccount() {
        //balance
        assertEquals(u1.getAccount().getBalance(), u1Read.getAccount().getBalance(), 0);
        //amount
        assertEquals(u1.getAccount().getIncomes().get(0).getAmount(),
            u1Read.getAccount().getIncomes().get(0).getAmount(), 0);
        //description
        assertEquals(u2.getAccount().getIncomes().get(0).getDescription(),
            u2Read.getAccount().getIncomes().get(0).getDescription());
    }

    private static void deleteTestfile() {
        try {
            if ((new File(filepath)).exists()) {
                Files.delete(Path.of(filepath));
            }
        } catch (IOException e) {
            System.err.println("Error deleting file");
        }
    }

}
