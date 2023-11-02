package financevision.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import core.FinanceVisionModel;
import core.Transaction;
import core.User;
import filesaving.JsonFileSaving;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { FinanceVisionController.class, FinanceVisonService.class, FinanceVisionApplication.class })
@WebMvcTest
public class FinanceVisionApplicationTest {
  @Autowired
  private MockMvc mockMvc;

  private Gson gson;

  @BeforeEach
  public void setup() throws Exception {
    gson = new JsonFileSaving().createGson();
  }

  private String financevisionUrl(String... segments) {
    String url = "/" + FinanceVisionController.FINANCE_VISION_SERVICE_PATH;
    for (String segment : segments) {
      url = url + "/" + segment;
    }
    return url;
  }

  @Test
  public void testGet() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(financevisionUrl())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    System.out.println("Result: " + result.toString());
    try {
      FinanceVisionModel financeVisionModel = gson.fromJson(result.getResponse().getContentAsString(), FinanceVisionModel.class);
      System.out.println(financeVisionModel.getUsernames());
      User testUser = financeVisionModel.getUser("testuser");
/*       List<User> users = gson.fromJson(result.getResponse().getContentAsString(), new TypeToken<ArrayList<User>>(){}.getType());
      System.out.println(users);
      assertTrue(users.stream().anyMatch(i -> i.getUsername().equals("testuser")));
      User testUser = users.get(0); */
/*       User[] users = gson.fromJson(result.getResponse().getContentAsString(), new TypeToken<ArrayList<User>>(){}.getType());
      System.out.println(users);
      assertTrue(users.stream().anyMatch(i -> i.getUsername().equals("testuser")));
      User testUser = users[0]; */
      assertNotNull(testUser);
      List<Transaction> testIncomes = testUser.getAccount().getIncomes();
      List<Transaction> testExpenses = testUser.getAccount().getExpenses();
      assertTrue(testIncomes.stream().anyMatch(i -> i.getDescription().equals("Money from granny")));
      assertTrue(testExpenses.stream().anyMatch(e -> e.getDescription().equals("Food")));
    } catch (JsonParseException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

}
