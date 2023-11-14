package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testclass for Budget.java using JUnit.
 */
public class BudgetTest {
    private Budget budget;

    @BeforeEach
    public void setUp() {
        budget = new Budget();
        budget.addCategory("Other", 3000.0);
    }

    @Test
    public void testAddCategory() {
        assertEquals(1, budget.getCategories().size());
        assertTrue(budget.getCategories().stream().anyMatch(a -> a.equals("Other")));
        assertThrows(IllegalArgumentException.class, () -> budget.addCategory("", 150));
    }

    @Test
    public void testGetLimit() {
        assertEquals(3000.0, budget.getLimit("Other"));
    }
}
