package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<FinanceVisionModel> response = testRestTemplate.getForEntity(getUrl(), FinanceVisionModel.class);
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "En feil oppsto under hentingen av modell-objektet");
    FinanceVisionModel model1 = response.getBody();
    assertNotNull(model1);
    assertTrue(model1.containsUser("testuser"), "Sample-brukeren ble ikke funnet i modell-objektet");
  }

  @Test
  public void TestGetUser() {
    ResponseEntity<User> response = testRestTemplate.getForEntity(getUrl() + "/user/testuser", User.class);
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "En feil oppsto under hentingen av user-objektet");
    User testUser = response.getBody();
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
    ResponseEntity<Void> response = testRestTemplate.exchange(
        getUrl() + "/user/doejohn",
        HttpMethod.PUT,
        new HttpEntity<>(testuser),
        Void.class
    );
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "En feil oppsto under PUT-requesten");
  }

  @Test
  public void TestRemoveUser() {
    User testuser2 = new User("marco", "adecentPassword!", "Mark Zuck", "marco@example.com", new Account());
        ResponseEntity<Void> putResponse = testRestTemplate.exchange(
        getUrl() + "/user/marco",
        HttpMethod.PUT,
        new HttpEntity<>(testuser2),
        Void.class
    );
    HttpStatus putStatusCode = putResponse.getStatusCode();
    assertEquals(HttpStatus.OK, putStatusCode);
    ResponseEntity<Void> deleteResponse = testRestTemplate.exchange(
        getUrl() + "/user/marco",
        HttpMethod.DELETE,
        null,
        Void.class
    );
    HttpStatus deleteStatusCode = deleteResponse.getStatusCode();
    assertEquals(HttpStatus.OK, deleteStatusCode, "En feil oppsto under DELETE-requesten");
  }
}
