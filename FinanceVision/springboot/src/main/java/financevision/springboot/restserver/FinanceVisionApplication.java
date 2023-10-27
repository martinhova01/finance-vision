package financevision.springboot.restserver;

import com.google.gson.Gson;
import filesaving.JsonFileSaving;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * The Spring application.
 */
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class FinanceVisionApplication {

    @Bean
    public Gson gson() {
        return new JsonFileSaving().createGson();
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceVisionApplication.class, args);
    }
}
