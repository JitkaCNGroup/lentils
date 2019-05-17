package dk.cngroup.lentils.entity;

public class TeamProgressWithTeam {

    private Team team;
    private TeamProgress teamProgress;

    public TeamProgressWithTeam(final Team team, final TeamProgress teamProgress) {
        this.team = team;
        this.teamProgress = teamProgress;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    public TeamProgress getTeamProgress() {
        return teamProgress;
    }

    public void setTeamProgress(final TeamProgress teamProgress) {
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
