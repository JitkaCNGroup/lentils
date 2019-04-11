package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.HintTakenKey;
import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HintTakenRepository extends JpaRepository<HintTaken, HintTakenKey> {
    List<HintTaken> findByTeam(Team team);
    HintTaken findByTeamAndHint(Team team, Hint hint);

    @Query(value = "SELECT ht.* FROM hint_taken ht " +
            "LEFT JOIN hint h ON ht.hint_hint_id = h.hint_id " +
            "WHERE ht.team_team_id = ?1 AND h.cypher_id = ?2",
            nativeQuery = true)
    List<HintTaken> findAllByTeamAndCypher(Long teamId, Long cypherId);
}
