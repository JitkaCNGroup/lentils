package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.HintTaken;

import java.util.List;

public class TeamScoreDetail {
    private final Cypher cypher;
    private final int statusScore;
    private final List<HintTaken> hintsTaken;
    private final int hintTakenScore;
    private final int score;

    public TeamScoreDetail(final Cypher cypher,
                           final int statusScore,
                           final List<HintTaken> hintsTaken,
                           final int hintTakenScore,
                           final int score) {
        this.cypher = cypher;
        this.statusScore = statusScore;
        this.hintsTaken = hintsTaken;
        this.hintTakenScore = hintTakenScore;
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

    public int getHintTakenScore() {
        return hintTakenScore;
    }

    @Override
    public String toString() {
        String string = "";
        for (HintTaken hintTaken: hintsTaken) {
            string = string + hintTaken.getHint().getHintId() + ", ";
        }
        if (string.endsWith(", ")) {
            string = string.substring(0, string.length() - 2);
        }
        return string;
    }
}

