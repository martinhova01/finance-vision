package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncomeTest {

  private Income income;
  private Income income2;

  @BeforeEach
  public void setUp() {
    income = new Income("Money from granny", 1000.0, "Other");
    income2 = new Income("Money from dad", 200.0, "Other");
    
  }

  @Test
  public void testDescription() {
    assertEquals("Money from granny", income.getDescription());
    assertEquals("Money from dad", income2.getDescription());
  }
  

  @Test
  public void testAmount() {
    assertEquals(1000.0, income.getAmount());
    assertEquals(200.0, income2.getAmount());
  }

  @Test
  public void testInitializedTimestamp() {
    assertNotNull(income.getTime(), "Timestamp has not been set");
  }

}
