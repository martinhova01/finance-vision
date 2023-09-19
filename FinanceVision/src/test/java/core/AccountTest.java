package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

  private Account account;
  private Account account2;
  private Transaction expenseFood;
  private Transaction incomeAllowance;
  private Transaction incomeSalary;

  @BeforeEach
  public void setUp() {
    account = new Account(1500);
    account2 = new Account(4090.0);
    expenseFood = new Expense("Food", 105.0);
    incomeAllowance = new Income("Weekly allowance", 500.0);
    incomeSalary = new Income("Salary", 1430.0);
    };

  

  @Test
  public void testTransactions() {
    assertEquals(0, account.getTransactions().size());
    account.addTransaction(expenseFood);
    assertEquals(expenseFood, account.getTransactions().get(0));
    account.addTransaction(incomeAllowance);
    account.addTransaction(incomeSalary);
    assertEquals(3, account.getTransactions().size());


    
  }


  @Test
  public void testIncomes() {
    assertEquals(1500, account.getBalance());
    account.addTransaction(incomeAllowance); //Legger til to transaksjoner av type 'income'
    assertEquals(2000, account.getBalance());
    assertEquals(incomeAllowance, account.getIncomes().get(0));
    account.addTransaction(incomeSalary); // ...
    assertEquals(2, account.getIncomes().size());
    assertEquals(3430, account.getBalance());
    assertEquals("Weekly allowance", account.getIncomes().get(0).getDescription());


  }

  @Test
  public void testExpenses() {
    assertEquals(1500, account.getBalance());
    account.addTransaction(expenseFood);
    assertEquals(1395, account.getBalance());
    assertEquals(expenseFood, account.getExpenses().get(0));
    assertEquals("Food", account.getExpenses().get(0).getDescription());




  }


  @Test
  public void testTimestamp() {
    account.addTransaction(expenseFood);
    assertNotNull(account.getTransactions().get(0).getTime());
  }



  @Test
  public void testStartValue() {
    assertEquals(1500, account.getStartValue()); //Sjekker at startValue stemmer
    assertEquals(4090.0, account2.getStartValue()); //...

  }



}
