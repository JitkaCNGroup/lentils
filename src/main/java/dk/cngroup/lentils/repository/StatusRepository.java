package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.StatusKey;
import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, StatusKey> {
    List<Status> findAllByCypher(Cypher cypher);
    Status findByTeamAndCypher(Team team, Cypher cypher);
    List<Status> findByTeam(Team team);
    Boolean existsStatusByCypherAndTeam(Cypher cypher, Team team);
    void deleteAllByCypherCypherId(Long cypherId);
    @Query(value = "SELECT DISTINCT s.* FROM status s " +
            "WHERE s.cypher_status = 2 AND s.team_team_id = ?1",
            nativeQuery = true)
    List<Status> findStatusPendingByTeam(Long teamId);
}
