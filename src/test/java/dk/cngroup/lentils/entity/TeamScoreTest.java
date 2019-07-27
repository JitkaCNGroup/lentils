package dk.cngroup.lentils.entity;

import dk.cngroup.lentils.entity.view.TeamScore;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TeamScoreTest {

    @Test
    public void testCompareToEquals() {
        ObjectGenerator og = new ObjectGenerator();
        Team team1 = og.generateValidTeam();
        Team team2 = og.generateValidTeam();
        TeamScore x = new TeamScore(team1, 10);
        TeamScore y = new TeamScore(team2, 10);

        assertEquals(0, x.compareTo(y));
        assertEquals(x, y);
    }

    @Test
    public void testCompareToEqualsShouldBeFalse() {
        ObjectGenerator og = new ObjectGenerator();
        Team team1 = og.generateValidTeam();
        Team team2 = og.generateValidTeam();
        TeamScore x = new TeamScore(team1, 10);
        TeamScore y = new TeamScore(team2, 20);

        assertEquals(10, x.compareTo(y));
        assertNotEquals(x, y);
    }
}