package financevision.springboot.restserver;

import com.google.gson.Gson;

import filesaving.JsonFileSaving;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class TestConfig {

    @Bean
    public TestRestTemplate testRestTemplate() {
      Gson gson = new JsonFileSaving().createGson();
      GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter(gson);
      return new TestRestTemplate(new RestTemplateBuilder().additionalMessageConverters(messageConverter));
    }
}