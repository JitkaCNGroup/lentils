package dk.cngroup.lentils.entity.core;

import dk.cngroup.lentils.entity.view.Rank;
import dk.cngroup.lentils.entity.view.TeamScore;

public class ScoreItem implements Comparable {
    private int score;
    private Rank rank;

    public ScoreItem(final int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setRank(final Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int compareTo(final Object o) {
        int compareScore = ((TeamScore) o).getScore();
        return compareScore - getScore();
    }
}
