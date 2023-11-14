package financevision.springboot.restserver;

import com.google.gson.Gson;
import filesaving.JsonFileSaving;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Class that is used to configure the TestRestTemplate object used in the tests.
 */
@Configuration
public class TestConfig {

    /**
     * Creates a TestRestTemplate object that uses a custom Gson object.
     *
     * @return the custom TestRestTemplate object
     */
    @Bean
    public TestRestTemplate getTestRestTemplate() {
        Gson gson = new JsonFileSaving().createGson();
        GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter(gson);
        return new TestRestTemplate(
            new RestTemplateBuilder().additionalMessageConverters(messageConverter));
    }
}