package dk.cngroup.lentils.entity;

public class TeamProgressWithTeam {

    private Team team;
    private String teamProgress;

    public TeamProgressWithTeam(Team team, String teamProgress) {
        this.team = team;
        this.teamProgress = teamProgress;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getTeamProgress() {
        return teamProgress;
    }

    public void setTeamProgress(String teamProgress) {
        this.teamProgress = teamProgress;
    }

    @Override
    public String toString() {
        return "TeamProgressWithTeam{" +
                "team=" + team +
                ", teamProgress='" + teamProgress + '\'' +
                '}';
    }
}
