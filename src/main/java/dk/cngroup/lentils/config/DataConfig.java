package dk.cngroup.lentils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dk.cngroup.lentils.repository")
//@PropertySource("application.properties")
public class DataConfig
{
}
