package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void testGetLimit() {
        assertEquals(3000.0, budget.getLimit("Other"));
    }
}
