import java.util.Vector;

public class CommandCreateTeam implements Command {
    private InputHandler in;
    private Team team;
    private Vector<Team> _teams;
    private int index;
    private String sportType, teamID, teamName;
    private CurrentTeamKeeper currentTeamKeeper;

    public CommandCreateTeam(InputHandler in, Vector<Team> _teams, CurrentTeamKeeper currentTeamKeeper) {
        this.in = in;
        this._teams = _teams;
        this.currentTeamKeeper = currentTeamKeeper;
    }

    public void execute() {
        sportType = in.askForSportType();

        FactoryTeam factoryTeam;
        if (sportType.equals("Volleyball")) {
            factoryTeam = new FactoryVolleyballTeam();
            team = factoryTeam.createTeam(in, _teams);
        } else {
            factoryTeam = new FactoryFootballTeam();
            team = factoryTeam.createTeam(in, _teams);
        }

        teamName = team.getName();
        teamID = team.getTeamID();
        _teams.add(team);
        index = _teams.size() - 1;
        in.sysMsg(sportType + " team is created.");
        currentTeamKeeper.setCurrentTeam(team);
        in.sysMsg("Current team is change to " + team.getTeamID() + ".");
    }

    public void undo() {
        _teams.remove(index);
    }

    public void redo() {
        _teams.add(index, team);
    }

    public String toString() {
        return "Create " + sportType + " team (" + teamID + ")" + teamName;
    }
}
