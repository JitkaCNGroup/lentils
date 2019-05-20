package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.core.ScoreItem;

public class TeamScore extends ScoreItem {
    private final Team team;

    public TeamScore(final Team team, final int score) {
        super(score);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}

