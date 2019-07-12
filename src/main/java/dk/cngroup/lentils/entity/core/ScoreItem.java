package dk.cngroup.lentils.entity.core;

import dk.cngroup.lentils.entity.view.Rank;
import dk.cngroup.lentils.entity.view.TeamScore;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoreItem)) {
            return false;
        }
        ScoreItem scoreItem = (ScoreItem) o;
        return score == scoreItem.score &&
                Objects.equals(rank, scoreItem.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, rank);
    }
}
