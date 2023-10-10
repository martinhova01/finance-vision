package filesaving;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Account;
import core.Expense;
import core.Income;
import core.User;

public class JsonFileSavingTest {
  private User u1, u2, u1Read, u2Read;
  private Account a1, a2;

  @BeforeEach
  public void setUp() throws IOException{

    a1 = new Account(1000);
    a1.addTransaction(new Income("lønn", 500.0, "Food"));
    a1.addTransaction(new Expense("skatt", 200.0, "Food"));
    u1 = new User("martinhova", "password", "Martin Høva", "martirho@stud.ntnu.no", a1);

    a2 = new Account(5000);
    a2.addTransaction(new Income("gave", 700.0, "Food"));
    a2.addTransaction(new Expense("mat", 1500.0, "Food"));
    u2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", a2);

    List<User> users = new ArrayList<>();
    users.add(u1);
    users.add(u2);

    File f = new File(System.getProperty("user.home") + "/testdata.json");
    FileHandler fileHandler = new JsonFileSaving();
    fileHandler.serializeUsers(users, f);

    List<User> readUsers = fileHandler.deserializeUsers(f);

    u1Read = readUsers.get(0);
    u2Read = readUsers.get(1);

  }

  @Test
  public void testUsername() throws IOException{
    assertTrue(u1.getUsername().equals(u1Read.getUsername()));
    assertTrue(u2.getUsername().equals(u2Read.getUsername()));
  }

  @Test
  public void testPassword(){
    assertTrue(u1.getPassword().equals(u1Read.getPassword()));
    assertTrue(u2.getPassword().equals(u2Read.getPassword()));
  }

  @Test
  public void testFullName(){
    assertTrue(u1.getFullName().equals(u1Read.getFullName()));
    assertTrue(u2.getFullName().equals(u2Read.getFullName()));
  }

  @Test
  public void testEmail(){
    assertTrue(u1.getEmail().equals(u1Read.getEmail()));
    assertTrue(u2.getEmail().equals(u2Read.getEmail()));
  }

  @Test
  public void testAccount(){
      //balance
    assertEquals(u1.getAccount().getBalance(), u1Read.getAccount().getBalance(), 0);
      //amount
    assertEquals(u1.getAccount().getIncomes().get(0).getAmount(), u1Read.getAccount().getIncomes().get(0).getAmount(), 0);
      //description
    assertEquals(u2.getAccount().getIncomes().get(0).getDescription(), u2Read.getAccount().getIncomes().get(0).getDescription());
  }

}
