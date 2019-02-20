package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Progress;
import dk.cngroup.lentils.entity.ProgressKey;
import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, ProgressKey> {
    public Progress findByTeamAndCypher(Team team, Cypher cypher);
    public Progress findByTeamIdAndCypherId (Long teamId, Long cypherId);

    public List<Progress> findByTeam(Team team);
}
