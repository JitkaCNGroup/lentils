package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CypherService
{
    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    public CypherService(CypherRepository cypherRepository)
    {
        this.cypherRepository = cypherRepository;
    }

    public Optional<Cypher> findById(Integer id)
    {
        return cypherRepository.findById(id);
    }

    public List<Cypher> findAll()
    {
        return cypherRepository.findAll();
    }

    public void save(Cypher cypher)
    {
        cypherRepository.save(cypher);
    }
}
