package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.StatusRepositorySpec;
import dk.cngroup.lentils.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
public class StatisticsService {
    private final StatusRepository statusRepository;
    private final CypherRepository cypherRepository;
    private final HintTakenRepository hintTakenRepository;
    private final TeamRepository teamRepository;
    private final HintTakenService hintTakenService;

    @Autowired
    public StatisticsService(final StatusRepository statusRepository,
                             final CypherRepository cypherRepository,
                             final HintTakenRepository hintTakenRepository,
                             final TeamRepository teamRepository,
                             final HintTakenService hintTakenService) {
        this.statusRepository = statusRepository;
        this.cypherRepository = cypherRepository;
        this.hintTakenRepository = hintTakenRepository;
        this.teamRepository = teamRepository;
        this.hintTakenService = hintTakenService;
    }

    public Map<Cypher, Integer> getCountOfSpecificStatusesOfCyphers(final CypherStatus cypherStatus) {
        List<Cypher> allCyphers = cypherRepository.findAll();
        return sortCyphersMapByValueDesc(getCyphersAndStatusCountMap(cypherStatus, allCyphers));
    }

    public Map<Team, Integer> getCountOfSpecificStatusesOfTeams(final CypherStatus cypherStatus) {
        List<Team> allTeams = teamRepository.findAll();
        return sortTeamsMapByValueDesc(getTeamsAndStatusCountMap(cypherStatus, allTeams));
    }

    public Map<Cypher, Integer> getNumberOfHintsTakenOfCyphers() {
        List<Cypher> cyphers = cypherRepository.findAll();
        return sortCyphersMapByValueDesc(getCyphersAndHintsTakenCountMap(cyphers));
    }

    public Map<Team, Integer> getNumberOfHintsTakenOfTeams() {
        List<Team> teams = teamRepository.findAll();
        return sortTeamsMapByValueDesc(getTeamsAndHintsTakenCountMap(teams));
    }

    public Map<Cypher, Integer> getPointsOfHintsTakenOfCyphers() {
        List<Cypher> cyphers = cypherRepository.findAll();
        List<Team> teams = teamRepository.findAll();
        return sortCyphersMapByValueDesc(getCyphersAndHintsTakenPointsMap(cyphers, teams));
    }

    public Map<Team, Integer> getPointsOfHintsTakenOfTeams() {
        List<Cypher> cyphers = cypherRepository.findAll();
        List<Team> teams = teamRepository.findAll();
        return sortTeamsMapByValueDesc(getTeamsAndHintsTakenPointsMap(cyphers, teams));
    }

    private Map<Cypher, Integer> sortCyphersMapByValueDesc(final Map<Cypher, Integer> cyphersMap) {
        return cyphersMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Map<Team, Integer> sortTeamsMapByValueDesc(final Map<Team, Integer> teamsMap) {
        return teamsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Map<Cypher, Integer> getCyphersAndHintsTakenCountMap(final List<Cypher> cyphers) {
        return cyphers
                .stream()
                .collect(Collectors.toMap(
                        cypher -> cypher,
                        cypher -> hintTakenRepository.findAllByHintCypherCypherId(cypher.getCypherId()).size()
                ));
    }

    private Map<Team, Integer> getTeamsAndHintsTakenCountMap(final List<Team> teams) {
        return teams
                .stream()
                .collect(Collectors.toMap(
                        team -> team,
                        team -> hintTakenRepository.findByTeam(team).size()
                ));
    }

    private Map<Cypher, Integer> getCyphersAndStatusCountMap(final CypherStatus cypherStatus,
                                                             final List<Cypher> allCyphers) {
        return allCyphers.stream()
                .collect(Collectors.toMap(
                        cypher -> cypher,
                        cypher -> statusRepository.findAll(Specification
                                .where(StatusRepositorySpec.hasCypher(cypher)
                                        .and(StatusRepositorySpec.hasCypherStatus(cypherStatus))))
                                .size()));
    }

    private Map<Cypher, Integer> getCyphersAndHintsTakenPointsMap(final List<Cypher> cyphers,
                                                                  final List<Team> teams) {
        return cyphers.stream()
                .collect(Collectors.toMap(
                        cypher -> cypher,
                        cypher -> teams.stream().mapToInt(team -> hintTakenService.getHintScore(team, cypher)).sum()
                ));
    }

    private Map<Team, Integer> getTeamsAndHintsTakenPointsMap(final List<Cypher> cyphers,
                                                                  final List<Team> teams) {
        return teams.stream()
                .collect(Collectors.toMap(
                        team -> team,
                        team -> cyphers.stream().mapToInt(cypher -> hintTakenService.getHintScore(team, cypher)).sum()
                ));
    }

    private Map<Team, Integer> getTeamsAndStatusCountMap(final CypherStatus cypherStatus,
                                                             final List<Team> allTeams) {
        return allTeams.stream()
                .collect(Collectors.toMap(
                        team -> team,
                        team -> statusRepository.findAll(Specification
                                .where(StatusRepositorySpec.hasTeamName(team.getName())
                                        .and(StatusRepositorySpec.hasCypherStatus(cypherStatus))))
                                .size()));
    }
}
