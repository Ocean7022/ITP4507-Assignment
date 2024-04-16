import java.util.Vector;

public class CommandSetCurrentTeam implements Command {
    private InputHandler in;
    private Vector<Team> _teams;
    private CurrentTeamKeeper currentTeamKeeper;

    public CommandSetCurrentTeam(InputHandler in, Vector<Team> _teams, CurrentTeamKeeper currentTeamKeeper) {
        this.in = in;
        this._teams = _teams;
        this.currentTeamKeeper = currentTeamKeeper;
    }

    public void execute() {
        currentTeamKeeper.setCurrentTeam(in.askForTeamIDAndGetTeam(_teams, currentTeamKeeper.getCurrentTeam()));
    }

    public void undo() {
    }

    public void redo() {
    }
}
