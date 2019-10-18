package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.ContactLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactLinkRepository extends JpaRepository<ContactLink, Long> {
    List<ContactLink> findAllByOrderByIdAsc();
}
