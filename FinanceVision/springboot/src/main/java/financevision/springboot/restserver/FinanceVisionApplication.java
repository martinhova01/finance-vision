package financevision.springboot.restserver;

import java.util.EnumSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Spring application.
 */
@SpringBootApplication
public class FinanceVisionApplication {

  // @Bean
  // public Module objectMapperModule() {
  //   return TodoPersistence.createJacksonModule(EnumSet.of(TodoModelParts.LISTS));
  // }

  public static void main(String[] args) {
    SpringApplication.run(FinanceVisionApplication.class, args);
  }
}
