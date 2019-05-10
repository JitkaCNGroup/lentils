package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
    List<Team> findByNameContaining(String searchString);
}
