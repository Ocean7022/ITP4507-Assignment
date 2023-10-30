import java.util.Vector;

public class CommandDisplayAll implements Command {
    private Vector<Team> _teams;

    public CommandDisplayAll(Vector<Team> _teams) {
        this._teams = _teams;
    }

    public void execute() {
        for (Team team : _teams) {
            team.displayTeam();
        }
    }

    public void undo() {
    }

    public void redo() {
    }
}
