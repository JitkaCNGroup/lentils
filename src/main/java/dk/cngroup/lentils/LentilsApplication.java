package dk.cngroup.lentils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LentilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LentilsApplication.class, args);
		System.out.print("Runs, see db at localhost:8080/h2-console");
	}
}
