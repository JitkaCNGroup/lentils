package dk.cngroup.lentils.config;

import dk.cngroup.lentils.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@EnableAutoConfiguration
@Configuration
public class SearchConfiguration {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    SearchService searchService() {
        SearchService searchService = new SearchService(entityManager);
        searchService.initializeSearch();
        return searchService;
    }
}
