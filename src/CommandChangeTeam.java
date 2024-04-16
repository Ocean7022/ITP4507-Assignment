import java.util.Vector;

public class CommandChangeTeam implements Command {
    private InputHandler in;
    private Caretaker caretaker;
    private Vector<Team> _teams;
    private Team team;
    private String oldTeamName, newTeamName;

    public CommandChangeTeam(InputHandler in, Caretaker caretaker, Vector<Team> _teams, Team currentTeam) {
        this.in = in;
        this.caretaker = caretaker;
        this._teams = _teams;
        this.team = currentTeam;
    }

    public void execute() {
        newTeamName = in.askForNewTeamName(_teams, team);
        oldTeamName = team.getName();

        caretaker.saveTeamToUndo(team);
        in.sysMsg("Team name [" + team.getName() + "] is changed to [" + newTeamName + "].");
        team.setName(newTeamName);
    }

    public void undo() {
        caretaker.saveTeamToRedo(team);
        caretaker.undo();
    }

    public void redo() {
        caretaker.saveTeamToUndo(team);
        caretaker.redo();
    }

    public String toString() {
        return "Change team's (" + team.getTeamID() + ") name, [" + oldTeamName + "] to [" + newTeamName + "]";
    }
}
