package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

abstract public class AbstractClientControllerTest {
    protected static final String CORRECT_CODEWORD = "topgun";
    protected static final String FALSE_CODEWORD = "firefly";

    @Autowired
    protected CypherRepository cypherRepository;
    @Autowired
    protected TeamRepository teamRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected StatusRepository statusRepository;
    @Autowired
    protected ObjectGenerator generator;
    @Autowired
    protected FinalPlaceRepository finalPlaceRepository;
    @Autowired
    protected HintRepository hintRepository;

    private Team team;
    private User user;
    private Cypher cypher;

    protected void setupSharedFixture() {
        createTestTeamAndUser();
        createTestCypher();
    }

    protected Team getFixtureTeam() {
        return team;
    }

    protected User getFixtureUser() {
        return user;
    }

    protected Cypher getFixtureCypher() {
        return cypher;
    }

    protected CustomUserDetails getUserDetailsMock() {
        return new CustomUserDetails(user);
    }

    protected void setThatGameHasNotEnded() {
        createFinalPlace(LocalDateTime.now().plusHours(5));
    }

    protected void setThatGameHasAlreadyEnded() {
        createFinalPlace(LocalDateTime.now().minusHours(2));
    }

    protected Cypher createTestCypher() {
        cypher = generator.generateValidCypher();
        cypher.setCodeword(CORRECT_CODEWORD);
        cypher.setTrapCodeword(FALSE_CODEWORD);

        return cypherRepository.save(cypher);
    }

    protected void setCypherStatusForTeam(final Cypher cypher, final CypherStatus value, final Team team) {
        final Status status = new Status();
        status.setCypherStatus(value);
        status.setCypher(cypher);
        status.setTeam(team);
        statusRepository.save(status);
    }

    private void createTestTeamAndUser() {
        team = generator.generateValidTeam();
        teamRepository.save(team);

        user = new User();
        user.setUsername("johnny");
        user.setPassword("dummy");
        user.setTeam(team);
        userRepository.save(user);
    }

    private void createFinalPlace(LocalDateTime time) {
        final FinalPlace finalPlace = generator.generateFinalPlace();
        finalPlace.setFinishTime(time);
        finalPlaceRepository.save(finalPlace);
    }
}
