package dk.cngroup.lentils.factory;

import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.TeamProgressFinished;
import dk.cngroup.lentils.entity.TeamProgressNotStarted;
import dk.cngroup.lentils.entity.TeamProgressPlaying;
import dk.cngroup.lentils.service.StatusService;

import java.util.List;

public class TeamProgressFactory {

    public static String create(final Team team, final StatusService statusService) {
        if (statusService.getAllByTeam(team).isEmpty()) {
            return new TeamProgressNotStarted().toString();
        }
        List<Status> pendingCyphers = statusService.getPendingCyphers(team);
        if (pendingCyphers.isEmpty()) {
            return new TeamProgressFinished().toString();
        }
        int stageOfPendingCypher = pendingCyphers.get(0).getCypher().getStage();
        return new TeamProgressPlaying(stageOfPendingCypher).toString();
    }
}
