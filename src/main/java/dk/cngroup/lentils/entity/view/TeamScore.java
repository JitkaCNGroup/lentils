package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.Team;

public class TeamScore implements Comparable {
    private final Team team;
    private final int score;

    public TeamScore(Team team, int score) {
        this.team = team;
        this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        int compareScore = ((TeamScore) o).getScore();
        return compareScore - this.score;
    }
}

