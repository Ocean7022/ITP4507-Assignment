import java.util.Stack;

public class CommandRedo implements Command {
    private InputHandler in;
    private Stack<Command> undoList;
    private Stack<Command> redoList;
    private CurrentTeamKeeper currentTeamKeeper;
    private Stack<Team> currentTeamUndoList;
    private Stack<Team> currentTeamRedoList;

    public CommandRedo(Stack<Command> undoList, Stack<Command> redoList, CurrentTeamKeeper currentTeamKeeper, Stack<Team> currentTeamUndoList, Stack<Team> currentTeamRedoList) {
        this.in = new InputHandler(null);
        this.undoList = undoList;
        this.redoList = redoList;
        this.currentTeamKeeper = currentTeamKeeper;
        this.currentTeamUndoList = currentTeamUndoList;
        this.currentTeamRedoList = currentTeamRedoList;
    }

    public void execute() {
        if (redoList.isEmpty())
            in.sysMsg("Nothing to redo.");
        else {
            Command command = redoList.pop();
            undoList.push(command);
            command.redo();
            in.sysMsg("Command (" + command + ") is redone.");
        }

        if (!currentTeamRedoList.isEmpty()) {
            currentTeamUndoList.push(currentTeamKeeper.getCurrentTeam());
            currentTeamKeeper.setCurrentTeam(currentTeamRedoList.pop());
        }
    }

    public void undo() {
    }

    public void redo() {
    }
}
