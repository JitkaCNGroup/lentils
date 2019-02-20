package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.FinalPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalPlaceRepository extends JpaRepository<FinalPlace, Long> {

}
