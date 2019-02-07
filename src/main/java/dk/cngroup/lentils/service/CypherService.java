package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Integer addNewCypher(Cypher cypher){
        Cypher newCypher = cypherRepository.save(cypher);
        return newCypher.getId();
    };

    public Cypher saveNewCypher(Cypher cypher){
        return cypherRepository.save(cypher);
    };

    public String getHintForStage(Integer stage)
    {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypher.getHint();
    }

    public Cypher getNextCypher (Integer stage)
    {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypherRepository.findByStage(cypher.getStage() + 1 );
    }

    public boolean checkCodeword(String codeword, Integer stage)
    {
        Cypher cypher = cypherRepository.findByStage(stage);

        return codeword.equals(cypher.getCodeword());
    }

    public void deleteAllCyphers()
    {
        cypherRepository.deleteAll();

    }
}
