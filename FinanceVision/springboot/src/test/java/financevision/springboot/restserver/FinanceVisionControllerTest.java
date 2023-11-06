package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import core.FinanceVisionModel;
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
  public void TestGetFinanceVisionModel() {
    FinanceVisionModel model1 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertNotNull(model1);
    System.out.println(model1.getUsernames());
    assertTrue(model1.containsUser("testuser"));
  }

  @Test
  public void TestGetUser() {
    User testUser = testRestTemplate.getForObject(getUrl() + "/user/testuser", User.class);
    assertNotNull(testUser);
    assertEquals("testuser", testUser.getUsername());
    List<Transaction> testIncomes = testUser.getAccount().getIncomes();
    List<Transaction> testExpenses = testUser.getAccount().getExpenses();
    assertTrue(testIncomes.stream().anyMatch(i -> i.getDescription().equals("Money from granny")));
    assertTrue(testExpenses.stream().anyMatch(e -> e.getDescription().equals("Food")));
  }
  
  @Test
  public void TestPutUser() {
    User testuser = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", new Account());
    testRestTemplate.put(getUrl() + "/user/doejohn", testuser);
    FinanceVisionModel model2 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertTrue(model2.containsUser("doejohn"));
  }

  @Test
  public void TestRemoveUser() {
    User testuser2 = new User("marco", "adecentPassword!", "Mark Zuck", "marco@example.com", new Account());
    testRestTemplate.put(getUrl() + "/user/marco", testuser2);
    testRestTemplate.delete(getUrl() + "/user/marco");
    FinanceVisionModel model3 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertFalse(model3.containsUser("marco"));
  }
}
