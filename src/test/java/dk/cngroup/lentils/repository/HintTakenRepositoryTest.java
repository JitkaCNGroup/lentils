package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class HintTakenRepositoryTest {

    @Autowired
    HintTakenRepository hintTakenRepository;

    @Autowired
    HintRepository hintRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    CypherService cypherService;

    @Autowired
    HintService hintService;

    @Autowired
    ObjectGenerator generator;

    private List<Hint> hints;

    private List<Team> teams;

    @Test
    public void addAllHintTakensForOneCypherOneTeam() {
        // one team
        Team team = teamService.save(generator.generateTeam());
        // one cypher
        Cypher cypher = cypherService.add(generator.generateCypher());
        // all hints for one cypher
        List<Hint> hints = hintService.addAll(generator.generateHintList(cypher));

        hints.stream().forEach(hint -> {
            hintTakenRepository.save(new HintTaken(new HintTakenKey(team.getId(), hint.getHintId())));
        });

        assertEquals(ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER, hintTakenRepository.count());
    }
}