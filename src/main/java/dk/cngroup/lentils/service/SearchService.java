package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public SearchService(final EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void initializeSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<Team> searchTeams(final String searchString) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Team.class)
                .get();
        Query luceneQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onFields("name")
                .matching(searchString)
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Team.class);

        List<Team> searchedTeamsList = null;
        try {
            searchedTeamsList = jpaQuery.getResultList();
        } catch (NoResultException e) {
           searchedTeamsList = Collections.emptyList();
        }

        return searchedTeamsList;
    }
}
