package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CypherRepository extends JpaRepository<Cypher, Integer>
{
    @Override
    default List<Cypher> findAll()
    {
        return null;
    }

    @Override
    default Cypher getOne(Integer integer)
    {
        return null;
    }

    @Override
    default <S extends Cypher> S save(S entity)
    {
        return null;
    }
}

