package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {
    private User user;
    private User user2;
    private Account account;

    @BeforeEach
    public void setUp() {
        // Create a new User instance before each test
        account = new Account(1000.0);
        user = new User("johndoe", "password123", "John Doe", "johndoe@example.com", account);
        user2 = new User();
    }

    @Test
    public void testGetUsername() {
        assertEquals("johndoe", user.getUsername());
        user2.setUsername("johnwick");
        assertEquals("johnwick", user2.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testGetFullName() {
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("johndoe@example.com", user.getEmail());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername());
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("user name"), "username cannot include a space");
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""), "username cannot be empty");
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("pass"), "password too short");
    }

    @Test
    public void testSetFullName() {
        user.setFullName("Jane Doe");
        assertEquals("Jane Doe", user.getFullName());
        assertThrows(IllegalArgumentException.class, () -> user.setFullName("Martin"), "Full name must be a least one first and one last name");
    }

    @Test
    public void testSetEmail() {
        user.setEmail("janedoe@example.com");
        assertEquals("janedoe@example.com", user.getEmail());
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("johndoe.com"), "email must contain @");
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("johndoe@example"), "email must contain .xxx");
        
    }

    @Test
    public void testGetAccount() {
        assertEquals(account, user.getAccount());
    }

    @Test
    public void testSetAccount() {
        Account account2 = new Account(2000);
        user.setAccount(account2);
        assertEquals(account2, user.getAccount());
    }

    @Test
    public void testSetAndGetBudget() {
        Budget budget = new Budget();
        budget.addCategory("Other", 2000);
        user.setBudget(budget);
        assertEquals(budget, user.getBudget());
    }

    @Test
    public void testEquals() {
        assertTrue(user.equals(user));
        user2.setUsername("Peter");
        assertFalse(user.equals(user2));
    }

    @Test
    public void testHashCode() {
        user2.setUsername("Peter");
        assertFalse(user.hashCode() == user2.hashCode());
        user2.setUsername("johndoe");
        assertTrue(user.hashCode() == user2.hashCode());
    }

}
