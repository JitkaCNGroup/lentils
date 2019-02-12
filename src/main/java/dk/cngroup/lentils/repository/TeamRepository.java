package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
