package dk.cngroup.lentils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LentilsApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(LentilsApplication.class);
    }

    public static void main(final String[] args) {
        SpringApplication.run(LentilsApplication.class, args);
        System.out.print("Runs, see db at localhost:8080/h2-console");
    }
}
