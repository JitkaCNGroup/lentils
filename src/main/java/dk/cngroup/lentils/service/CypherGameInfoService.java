package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.view.CypherGameInfo;
import dk.cngroup.lentils.repository.CypherGameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CypherGameInfoService {

    private CypherGameInfoRepository cypherGameInfoRepository;

    @Autowired
    public CypherGameInfoService(final CypherGameInfoRepository cypherGameInfoRepository) {
        this.cypherGameInfoRepository = cypherGameInfoRepository;
    }

    public List<CypherGameInfo> getAll() {
        return cypherGameInfoRepository.findAll();
    }

    public List<CypherGameInfo> getAllByTeamId(Long teamId) {
        return cypherGameInfoRepository.findAllByTeamId(teamId);
    }
}
