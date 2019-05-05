package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.formEntity.Codeword;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.StatusService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    private final Printer printer;
    private final ScoreService scoreService;
    private final HintService hintService;
    private final StatusService statusService;

    @Autowired
    public Logger(final Printer printer, final ScoreService scoreService, HintService hintService, StatusService statusService) {
        this.printer = printer;
        this.scoreService = scoreService;
        this.hintService = hintService;
        this.statusService = statusService;
    }

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
        printer.println(message.toString());

        return result;
    }

    @After("execution(* dk.cngroup.lentils.controller.ClientController.getHint(..))" +
            "&& args(hintId,user,..)")
    public void getHint(Long hintId,
                        CustomUserDetails user){

        Hint hint = hintService.getHint(hintId);
        int points = -hint.getValue();
        int score = scoreService.getScoreByTeam(user.getTeam());

        Message<Long> message = MessageFactory.createGetHint(user, hintId, points, score);
        printer.println(message.toString());
    }

    @After("execution(* dk.cngroup.lentils.controller.ClientController.skipCypher(..))" +
            "&& args(cypher,user)")
    public void skipCypher(Cypher cypher,
                           CustomUserDetails user){
        int score = scoreService.getScoreByTeam(user.getTeam());

        Message<Long> message = MessageFactory.createSkipCypher(user, cypher.getCypherId(), 0, score);
        printer.println(message.toString());
    }

    private int getVerifyCodewordPoints(String result) {
        int points = 0;
        if (result.startsWith("redirect:/cypher/")) {
            points = 10;
        }
        return points;
    }
}
