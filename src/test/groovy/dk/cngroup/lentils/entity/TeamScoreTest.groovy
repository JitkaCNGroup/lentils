package dk.cngroup.lentils.entity

import dk.cngroup.lentils.entity.view.TeamScore
import dk.cngroup.lentils.service.ObjectGenerator
import spock.lang.Specification
import spock.lang.Unroll

class TeamScoreTest extends Specification {

    static ObjectGenerator og = new ObjectGenerator()
    static Team team1 = og.generateValidTeam()
    static Team team2 = og.generateValidTeam()

    @Unroll
    def "TeamsScores where one team has score #x.getScore() and another team has score #y.getScore() should be equal"() {
        expect:
        x == y

        where:
        x                         || y
        new TeamScore(team1, -5)  || new TeamScore(team2, -5)
        new TeamScore(team1, 0)   || new TeamScore(team2, 0)
        new TeamScore(team1, 10)  || new TeamScore(team2, 10)
        new TeamScore(team1, 133) || new TeamScore(team2, 133)
    }

    @Unroll
    def "TeamScores where one team has score #x.getScore() and another team has score #y.getScore() should NOT be equal"() {
        expect:
        x != y

        where:
        x                        || y
        new TeamScore(team1, -5) || new TeamScore(team2, -10)
        new TeamScore(team1, -5) || new TeamScore(team2, 0)
        new TeamScore(team1, -5) || new TeamScore(team2, 5)
        new TeamScore(team1, 0)  || new TeamScore(team2, 2)
        new TeamScore(team1, 8)  || new TeamScore(team2, 90)
    }

    @Unroll
    def "CompareTo method should return #value when score of team1 is #x.getScore() and score of team2 is #y.getScore()"() {
        expect:
        value == x.compareTo(y)

        where:
        value || x                         || y
        0     || new TeamScore(team1, -10) || new TeamScore(team2, -10)
        5     || new TeamScore(team1, -10) || new TeamScore(team2, -5)
        10    || new TeamScore(team1, -10) || new TeamScore(team2, 0)
        0     || new TeamScore(team1, 0)   || new TeamScore(team2, 0)
        15    || new TeamScore(team1, 0)   || new TeamScore(team2, 15)
        12    || new TeamScore(team1, 2)   || new TeamScore(team2, 14)
        0     || new TeamScore(team1, 12)  || new TeamScore(team2, 12)
        -20   || new TeamScore(team1, 20)  || new TeamScore(team2, 0)
        -20   || new TeamScore(team1, -10) || new TeamScore(team2, -30)
        -20   || new TeamScore(team1, 0)   || new TeamScore(team2, -20)
    }
}
