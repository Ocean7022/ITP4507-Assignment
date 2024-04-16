public class CurrentTeamKeeper {
    private Team currentTeam;

    public CurrentTeamKeeper() {
        currentTeam = null;
    }

    public void setCurrentTeam(Team team) {
        currentTeam = team;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
