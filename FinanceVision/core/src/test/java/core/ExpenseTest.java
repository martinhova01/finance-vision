package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

  private Expense expense;
  private Expense expense2;
  private Expense expense3;
  private Expense expense4;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private LocalDateTime testDateTime = LocalDateTime.parse("2023-10-10 09:09:09", formatter);



  @BeforeEach
  public void setUp() {
    expense = new Expense("Netflix", 90.0, "Fun");
    expense2 = new Expense("Viaplay", 95.0, "Fun", testDateTime);
    expense3 = new Expense("Rent", 2500.0, "Other");
    expense4 = new Expense();
    expense4.setDescription("Taxes");
    expense4.setAmount(200.0);
    expense4.setCategory("Other");
    expense4.setTime(LocalDateTime.now());
  }

  @Test
  public void testDescription() {
    assertEquals("Netflix", expense.getDescription());
    assertThrows(IllegalArgumentException.class, () -> {
      expense.setDescription("");
    });
    assertEquals("Viaplay", expense2.getDescription());
    assertEquals("Rent", expense3.getDescription());
    assertEquals("Taxes", expense4.getDescription());
  }

  @Test
  public void testCategory() {
    assertEquals("Fun", expense.getCategory());
    assertThrows(IllegalArgumentException.class, () -> {
      expense.setDescription("");
    });
    assertEquals("Fun", expense2.getCategory());
    assertEquals("Other", expense3.getCategory());
    assertEquals("Other", expense4.getCategory());
  }

  @Test
  public void testAmount() {
    assertEquals(90.0, expense.getAmount());
    assertThrows(IllegalArgumentException.class, () -> {
      expense.setAmount(-500.0);
    });
    assertEquals(95.0, expense2.getAmount());
    assertEquals(2500.0, expense3.getAmount());
    assertEquals(200.0, expense4.getAmount());
  }
  
  @Test
  public void testInitializedTimestamp() {
    assertNotNull(expense.getTime(), "Timestamp has not been set");
    assertThrows(IllegalArgumentException.class, () -> {
      expense.setTime(null);
    });
    assertEquals("2023-10-10T09:09:09", expense2.getTime().toString());
    assertNotNull(expense3.getTime(), "Timestamp has not been set");
    assertNotNull(expense4.getTime(), "Timestamp has not been set");
  }

  @Test
  public void testToString() {
    assertEquals("- 90.0    Netflix    " + LocalDate.now(), expense.toString());
    assertEquals("- 95.0    Viaplay    " + testDateTime.toLocalDate(), expense2.toString());
    assertEquals("- 2500.0    Rent    " + LocalDate.now(), expense3.toString());
    assertEquals("- 200.0    Taxes    " + LocalDate.now(), expense4.toString());
  }
  


}
