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

    public void addNewCypher(Cypher cypher){};

    public String getHintForStage(Integer stage)
    {return null;}

    public Cypher getNextCypher (Integer stage)
    {return  null;}

}
