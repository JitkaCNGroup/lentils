package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.core.ScoreItem;

import java.util.Objects;

public class TeamScore extends ScoreItem {
    private final Team team;

    public TeamScore(final Team team, final int score) {
        super(score);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TeamScore teamScore = (TeamScore) o;
        return team.equals(teamScore.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), team);
    }
}

