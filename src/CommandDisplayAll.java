import java.util.Vector;

public class CommandDisplayAll implements Command {
    private Vector<Team> _teams;
    private InputHandler in;

    public CommandDisplayAll(Vector<Team> _teams) {
        this._teams = _teams;
        this.in = new InputHandler(null);
    }

    public void execute() {
        if (_teams.isEmpty())
            in.sysMsg("Team list is null.");
        else
            for (Team team : _teams)
                team.displayTeam();
    }

    public void undo() {
    }

    public void redo() {
    }
}
