package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.StatusKey;
import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, StatusKey> {
    List<Status> findAllByCypher(Cypher cypher);
    Status findByTeamAndCypher(Team team, Cypher cypher);
    List<Status> findByTeam(Team team);
    Boolean existsStatusByCypherAndTeam(Cypher cypher, Team team);
    boolean existsStatusByCypherAndTeamAndCypherStatus(Cypher cypher, Team team, CypherStatus cypherStatus);
    void deleteAllByCypherCypherId(Long cypherId);
    void deleteAllByTeamTeamId(Long teamId);
    List<Status> findByTeamAndCypherStatusOrderByCypherStageAsc(Team team, CypherStatus cypherStatus);
    List<Status> findByTeamOrderByCypherStageAsc(Team team);
}
