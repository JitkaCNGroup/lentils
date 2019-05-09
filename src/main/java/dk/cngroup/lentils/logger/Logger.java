package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.entity.formEntity.Codeword;
import dk.cngroup.lentils.factory.CypherStatusFactory;
import dk.cngroup.lentils.logger.printer.Printer;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static dk.cngroup.lentils.entity.CypherStatus.SKIPPED;
import static dk.cngroup.lentils.entity.CypherStatus.SOLVED;
import static dk.cngroup.lentils.logger.utils.LoggerUtils.*;

@Aspect
@Component
public class Logger {

    private final Printer printer;
    private final ScoreService scoreService;
    private final HintService hintService;
    private final TeamService teamService;
    private final CypherService cypherService;
    private final StatusService statusService;
    private final HintTakenService hintTakenService;

    @Autowired
    public Logger(final Printer printer,
                  final ScoreService scoreService,
                  final HintService hintService,
                  final TeamService teamService,
                  final CypherService cypherService,
                  final StatusService statusService,
                  final HintTakenService hintTakenService) {
        this.printer = printer;
        this.scoreService = scoreService;
        this.hintService = hintService;
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
    }

    /**
     * CLIENT METHODS
     */
    @Around("execution(* dk.cngroup.lentils.controller.ClientController.verifyCodeword(..))" +
            "&& args(cypherId,codeword,user,..)")
    public Object verifyCodeword(ProceedingJoinPoint joinPoint,
                                 Long cypherId,
                                 Codeword codeword,
                                 CustomUserDetails user) throws Throwable {
        String result = (String) joinPoint.proceed(joinPoint.getArgs());

        int points = getVerifyCodewordPoints(result);
        int score = scoreService.getScoreByTeam(user.getTeam());

        Message<Codeword> message = MessageFactory.createVerifyCodeword(user, codeword, points, score);
        printer.println(message);

        return result;
    }

    @After("execution(* dk.cngroup.lentils.controller.ClientController.getHint(..))" +
            "&& args(hintId,user,..)")
    public void getHint(Long hintId,
                        CustomUserDetails user){

        Hint hint = hintService.getHint(hintId);
        int points = -hint.getValue();
        int score = scoreService.getScoreByTeam(user.getTeam());

        Message<Long> message = MessageFactory.createTakeHint(user, hintId, points, score);
        printer.println(message);
    }

    @After("execution(* dk.cngroup.lentils.controller.ClientController.skipCypher(..))" +
            "&& args(cypher,user)")
    public void skipCypher(Cypher cypher,
                           CustomUserDetails user){
        int score = scoreService.getScoreByTeam(user.getTeam());

        Message<Long> message = MessageFactory.createSkipCypher(user, cypher.getCypherId(), SKIPPED.getStatusValue(),
                score);
        printer.println(message);
    }

    /**
     * ORGANIZER METHODS
     */
    @Around("execution(* dk.cngroup.lentils.controller.ProgressController.changeCypherStatus(..))" +
            "&& args(cypherId,teamId,newStatus,..)")
    public String changeCypherStatus(ProceedingJoinPoint joinPoint,
                                     Long cypherId,
                                     Long teamId,
                                     String newStatus) throws Throwable {
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        CypherStatus oldCypherStatus = statusService.getCypherStatusByTeamAndCypher(team, cypher);
        CypherStatus newCypherStatus = CypherStatusFactory.create(newStatus);
        int points = getChangeCypherStatusPoints(oldCypherStatus, newCypherStatus);

        String result = (String) joinPoint.proceed(joinPoint.getArgs());

        int score = scoreService.getScoreByTeam(team);
        StatusChange statusChange = new StatusChange(cypherId, oldCypherStatus, newCypherStatus);

        Message<StatusChange> message = MessageFactory.createChangeCypherStatus(team, statusChange, points, score);
        printer.println(message);

        return result;
    }

    @Around("execution(* dk.cngroup.lentils.controller.ProgressController.viewHintsForCypherByTeam(..))" +
            "&& args(teamId,hintId,pin)")
    public ResponseEntity takeHint(final ProceedingJoinPoint joinPoint,
                                   final Long teamId,
                                   final Long hintId,
                                   final String pin) throws Throwable {
        Team team = teamService.getTeam(teamId);
        Hint hint = hintService.getHint(hintId);

        ResponseEntity result = (ResponseEntity) joinPoint.proceed(joinPoint.getArgs());

        boolean success = result.getStatusCode() == HttpStatus.OK;
        int points = getTakeHintPoints(hint, success);
        int score = scoreService.getScoreByTeam(team);

        TakeHintChange takeHintChange = new TakeHintChange(hintId, success);
        Message<TakeHintChange> message = MessageFactory.createTakeHint(team, takeHintChange, points, score);
        printer.println(message);

        return result;
    }

    @Around("execution(* dk.cngroup.lentils.controller.ProgressController.revertHint(..))" +
            "&& args(teamId,hintId,..)")
    public String revertHint(final ProceedingJoinPoint joinPoint,
                           final Long teamId,
                           final Long hintId) throws Throwable {
        Team team = teamService.getTeam(teamId);
        Hint hint = hintService.getHint(hintId);
        boolean isHintTaken = isHintTakenByTeam(hint, team);

        String result = (String) joinPoint.proceed(joinPoint.getArgs());

        int points = getRevertHintPoints(hint, isHintTaken);
        int score = scoreService.getScoreByTeam(team);

        RevertHintChange takeHintChange = new RevertHintChange(hintId, isHintTaken);
        Message<RevertHintChange> message = MessageFactory.createRevertHint(team, takeHintChange, points, score);
        printer.println(message);

        return result;
    }

    private boolean isHintTakenByTeam(Hint hint, Team team) {
        List<HintTaken> takenHints = hintTakenService.getAllByTeamAndCypher(team, hint.getCypher());
        Optional<HintTaken> takenHint = takenHints.stream()
                .filter(ht -> ht.getHint().equals(hint))
                .findAny();
        return takenHint.isPresent();
    }
}
