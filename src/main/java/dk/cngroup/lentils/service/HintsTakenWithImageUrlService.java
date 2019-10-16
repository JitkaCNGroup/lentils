package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.HintsTakenWithImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HintsTakenWithImageUrlService {
    private final HintService hintService;
    private final HintTakenService hintTakenService;

    @Autowired
    public HintsTakenWithImageUrlService(final HintService hintService,
                                         final HintTakenService hintTakenService) {
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
    }

    public List<HintsTakenWithImageUrl> addImageUrlsToHints(final Team team, final Cypher cypher) {
        List<HintTaken> hintsTaken = hintTakenService.getAllByTeamAndCypher(team, cypher);
        return hintsTaken.stream()
                .map(hintTaken -> new HintsTakenWithImageUrl(
                        hintTaken, hintService.getFileUrlForHint(hintTaken.getHint().getHintId())))
                .collect(Collectors.toList());
    }
}
