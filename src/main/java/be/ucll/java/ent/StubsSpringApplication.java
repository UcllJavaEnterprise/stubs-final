package be.ucll.java.ent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// Disable Spring MVC auto configuration, as this interferes with how Vaadin works
@SpringBootApplication (exclude = ErrorMvcAutoConfiguration.class)
public class StubsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(StubsSpringApplication.class, args);
    }

}
