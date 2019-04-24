package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.HintTaken;

import java.util.List;

public class TeamScoreDetail implements Comparable {
    private final Cypher cypher;
    private final int statusScore;
    private final List<HintTaken> hintsTaken;
    private final int score;

    public TeamScoreDetail(final Cypher cypher,
                           final int statusScore,
                           final List<HintTaken> hintsTaken,
                           final int score) {
        this.cypher = cypher;
        this.statusScore = statusScore;
        this.hintsTaken = hintsTaken;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Cypher getCypher() {
        return cypher;
    }

    public int getStatusScore() {
        return statusScore;
    }


    public List<HintTaken> getHintsTaken() {
        return hintsTaken;
    }

    @Override
    public int compareTo(final Object o) {
        int compareScore = ((TeamScoreDetail) o).getScore();
        return compareScore - this.score;
    }
}

