package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

  private Expense expense;
  private Expense expense2;


  @BeforeEach
  public void setUp() {
    expense = new Expense("Netflix", 90.0, "Fun");
    expense2 = new Expense("Viaplay", 95.0, "Fun");
  }

  @Test
  public void testDescription() {
    assertEquals("Netflix", expense.getDescription());
    assertEquals("Viaplay", expense2.getDescription());

  }

  @Test
  public void testAmount() {
    assertEquals(90.0, expense.getAmount());
    assertEquals(95.0, expense2.getAmount());
  }
  
  @Test
  public void testInitializedTimestamp() {
    assertNotNull(expense.getTime(), "Timestamp has not been set");
  }
  


}
