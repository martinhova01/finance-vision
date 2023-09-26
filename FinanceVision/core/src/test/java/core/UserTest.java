package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        // Create a new User instance before each test
        user = new User("johndoe", "password123", "John Doe", "johndoe@example.com", new Account(1000.0));
    }

    @Test
    public void testGetUsername() {
        assertEquals("johndoe", user.getUsername());
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

}
