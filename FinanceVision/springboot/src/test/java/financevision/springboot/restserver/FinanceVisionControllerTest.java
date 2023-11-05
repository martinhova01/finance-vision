package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import core.FinanceVisionModel;
@SpringBootTest(classes = {FinanceVisionApplication.class, FinanceVisionController.class, FinanceVisonService.class},
    webEnvironment = WebEnvironment.DEFINED_PORT)
public class FinanceVisionControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;



  private String getUrl() {
    return "http://localhost:" + port + "/loft/";
  }

  @Test
  public void TestGetModel() {
    FinanceVisionModel model = testRestTemplate.getForObject(getUrl(), FinanceVisionModel.class);
    assertNotNull(model);
  }
  
}
