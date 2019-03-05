package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CypherRepository extends JpaRepository<Cypher, Long> {
    public Cypher findByStage(Integer stage);
}
