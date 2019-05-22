package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {
    List<Hint> findByCypher(Cypher cypher);
    void deleteByCypher(Cypher cypher);

    @Query(value = "SELECT DISTINCT h.* FROM hint h " +
            "LEFT JOIN hint_taken ht ON ht.hint_hint_id = h.hint_id " +
            "WHERE h.hint_id NOT IN (SELECT ht2.hint_hint_id FROM hint_taken ht2 " +
            "WHERE ht2.team_team_id = ?1) AND h.cypher_id = ?2 " +
            "ORDER BY h.VALUE ASC",
    nativeQuery = true)
    List<Hint> findHintsNotTakenByTeam(Long teamId, Long cypherId);
}
