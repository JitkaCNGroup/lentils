package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.view.CypherGameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CypherGameInfoRepository extends JpaRepository<CypherGameInfo, Long> {
}
