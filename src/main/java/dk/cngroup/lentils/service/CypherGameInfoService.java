package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.view.CypherGameInfo;
import dk.cngroup.lentils.repository.CypherGameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CypherGameInfo> getAllByTeamIdAndStatusIsNotLocked(final Long teamId) {
        int statusLocked = CypherStatus.LOCKED.ordinal();
        return cypherGameInfoRepository.findAllByTeamId(teamId).stream()
                .filter(cypherGameInfo -> cypherGameInfo.getStatus() != statusLocked)
                .collect(Collectors.toList());
    }
}
