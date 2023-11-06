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
    assertEquals(HttpStatus.OK, statusCode);
    FinanceVisionModel model1 = response.getBody();
    assertNotNull(model1);
    assertTrue(model1.containsUser("testuser"));
  }

  @Test
  public void TestGetUser() {
    ResponseEntity<User> response = testRestTemplate.getForEntity(getUrl() + "/user/testuser", User.class);
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode);
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
    assertEquals(HttpStatus.OK, statusCode); // Juster statuskoden etter behov
    //testRestTemplate.put(getUrl() + "/user/doejohn", testuser);
    FinanceVisionModel model2 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    System.out.println(model2);
    //assertTrue(model2.containsUser("doejohn"));
/*     ResponseEntity<String> result = testRestTemplate.getForEntity(getUrl(), String.class);
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),"Should return 400 when missing required parameter(s)"); */
  }

  @Test
  public void TestRemoveUser() {
    User testuser2 = new User("marco", "adecentPassword!", "Mark Zuck", "marco@example.com", new Account());
    testRestTemplate.put(getUrl() + "/user/marco", testuser2);
    ResponseEntity<Void> response = testRestTemplate.exchange(
        getUrl() + "/user/marco",
        HttpMethod.DELETE,
        null,
        Void.class
    );
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode); // Juster statuskoden etter behov
    ResponseEntity<User> responseFail = testRestTemplate.getForEntity(getUrl() + "/user/marco", User.class);
    HttpStatus statusCodeFail = responseFail.getStatusCode();
    System.out.println("Feil: " + statusCodeFail.value());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCodeFail);

/*     
    testRestTemplate.delete(getUrl() + "/user/marco");
    FinanceVisionModel model3 = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertFalse(model3.containsUser("marco"));

    ResponseEntity<FinanceVisionModel> response = testRestTemplate.getForEntity(getUrl(), FinanceVisionModel.class);
    HttpStatus statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode);
    FinanceVisionModel model1 = response.getBody();
    assertNotNull(model1);
    assertTrue(model1.containsUser("testuser")); */
  }
}
