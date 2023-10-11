package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncomeTest {

  private Income income;
  private Income income2;
  private Income income3;
  private Income income4;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  @BeforeEach
  public void setUp() {
    income = new Income("Money from granny", 1000.0, "Other");
    income2 = new Income("Money from dad", 200.0, "Other", LocalDateTime.parse("2023-10-10 09:09:09", formatter));
    income3 = new Income("Salary", 2500.0, "Other");
    income4 = new Income();
    income4.setDescription("Christmas present");
    income4.setAmount(100.0);
    income4.setCategory("Other");
    income4.setTime(LocalDateTime.now());
  }

  @Test
  public void testDescription() {
    assertEquals("Money from granny", income.getDescription());
    assertThrows(IllegalArgumentException.class, () -> {
      income.setDescription("");
    });
    assertEquals("Money from dad", income2.getDescription());
    assertEquals("Salary", income3.getDescription());
    assertEquals("Christmas present", income4.getDescription());
  }

  @Test
  public void testCategory() {
    assertEquals("Other", income.getCategory());
    assertThrows(IllegalArgumentException.class, () -> {
      income.setCategory("");
    });
    assertEquals("Other", income2.getCategory());
    assertEquals("Other", income3.getCategory());
    assertEquals("Other", income4.getCategory());
  }
  

  @Test
  public void testAmount() {
    assertEquals(1000.0, income.getAmount());
    assertThrows(IllegalArgumentException.class, () -> {
      income.setAmount(-500.0);
    });
    assertEquals(200.0, income2.getAmount());
    assertEquals(2500.0, income3.getAmount());
    assertEquals(100.0, income4.getAmount());
  }

  @Test
  public void testInitializedTimestamp() {
    assertNotNull(income.getTime(), "Timestamp has not been set");
    assertThrows(IllegalArgumentException.class, () -> {
      income.setTime(null);
    });
    assertEquals("2023-10-10T09:09:09", income2.getTime().toString());
    assertNotNull(income3.getTime(), "Timestamp has not been set");
    assertNotNull(income4.getTime(), "Timestamp has not been set");
  }

  @Test
  public void testToString() {
    assertEquals("+ 1000.0    Money from granny", income.toString());
    assertEquals("+ 200.0    Money from dad", income2.toString());
    assertEquals("+ 2500.0    Salary", income3.toString());
    assertEquals("+ 100.0    Christmas present", income4.toString());
  }

}
