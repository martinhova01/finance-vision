package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import core.Account;
import core.Expense;
import core.FinanceVisionModel;
import core.Income;
import core.Transaction;
import core.User;
@SpringBootTest(classes = {FinanceVisionApplication.class, FinanceVisionController.class, FinanceVisonService.class},
    webEnvironment = WebEnvironment.DEFINED_PORT)
public class FinanceVisionControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate = new TestConfig().testRestTemplate();

  private String getUrl() {
    return "http://localhost:" + port + "/fv/";
  }

  @Test
  public void TestGetModelUser() {
    FinanceVisionModel model = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertNotNull(model);
    assertTrue(model.containsUser("testuser"));
    User testUser = model.getUser("testuser");
    List<Transaction> testIncomes = testUser.getAccount().getIncomes();
    List<Transaction> testExpenses = testUser.getAccount().getExpenses();
    assertTrue(testIncomes.stream().anyMatch(i -> i.getDescription().equals("Money from granny")));
    assertTrue(testExpenses.stream().anyMatch(e -> e.getDescription().equals("Food")));
    System.out.println("GET" + model.getUsernames());
  }
  
  @Test
  public void TestPutModelUser() {
    FinanceVisionModel model1 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    System.out.println("put1" + model1.getUsernames());
    Account testAccount = new Account(5000);
    testAccount.addTransaction(new Income("gave", 700.0, "Food"));
    testAccount.addTransaction(new Expense("mat", 1500.0, "Food"));
    User testuser2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", testAccount);
    testRestTemplate.put(getUrl() + "/user/doejohn", testuser2);
    FinanceVisionModel model = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    System.out.println("put" + model.getUsernames());
    //assertTrue(model.containsUser("testuser"));
    assertTrue(model.containsUser("doejohn"));
  }

  @Test
  public void TestDeleteModelUser() {
    FinanceVisionModel model1 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    System.out.println("delete1" + model1.getUsernames());
    testRestTemplate.delete(getUrl() + "/user/testuser");
    FinanceVisionModel model = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    System.out.println("delete" + model.getUsernames());
    assertFalse(model.containsUser("testuser"));
    //assertTrue(model.containsUser("doejohn"));
  }
}
