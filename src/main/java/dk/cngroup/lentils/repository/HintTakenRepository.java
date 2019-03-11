package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.HintTakenKey;
import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HintTakenRepository extends JpaRepository<HintTaken, HintTakenKey> {
    List<HintTaken> findByTeam(Team team) ;
    HintTaken findByTeamAndHint(Team team, Hint hint);
}
