package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinanceVisionModelTest {
    private FinanceVisionModel model;
    private User user;

    @BeforeEach
    public void setUp() {
        model = new FinanceVisionModel();
        user = new User("johndoe", "password123", "John Doe", "johndoe@example.com", new Account());
        model.putUser(user);
    }

    @Test
    public void testPutUser() {
        assertFalse(model.containsUser("marco"));
        User user2 = new User("marco", "password456", "Mark Zuck", "marco@example.com", new Account());
        model.putUser(user2);
        assertTrue(model.containsUser("marco"));
        User user3 = new User("marco", "password456", "Mark Polo", "marco@example.com", new Account());
        model.putUser(user3);
        assertEquals("Mark Polo", model.getUser("marco").getFullName());
    }

    @Test
    public void testRemoveUser() {
        assertTrue(model.containsUser("johndoe"));
        model.removeUser(user);
        assertFalse(model.containsUser("johndoe"));
    }

    @Test
    public void testContainsUser() {
        assertTrue(model.containsUser("johndoe"));
        assertFalse(model.containsUser("banana"));
    }

    @Test
    public void testGetUsernames() {
        assertTrue(model.getUsernames().contains("johndoe"));
        model.removeUser(user);
        assertFalse(model.getUsernames().contains("johndoe"));
    }

    @Test
    public void testGetUser() {
        assertEquals(user, model.getUser("johndoe"));
        assertThrows(IllegalArgumentException.class, () -> model.getUser("banana"));
    }

    @Test
    public void testGetUsers() {
        assertEquals(new ArrayList<>(List.of(user)), model.getUsers());
        User user2 = new User("marco", "password456", "Mark Zuck", "marco@example.com", new Account());
        model.putUser(user2);
        model.putUser(user);
        assertEquals(new ArrayList<>(List.of(user, user2)), model.getUsers());
    }
}
