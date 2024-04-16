import java.util.Stack;

public class CommandUndo implements Command {
    private InputHandler in;
    private Stack<Command> undoList;
    private Stack<Command> redoList;
    private CurrentTeamKeeper currentTeamKeeper;
    private Stack<Team> currentTeamUndoList;
    private Stack<Team> currentTeamRedoList;

    public CommandUndo(Stack<Command> undoList, Stack<Command> redoList, CurrentTeamKeeper currentTeamKeeper, Stack<Team> currentTeamUndoList, Stack<Team> currentTeamRedoList) {
        this.in = new InputHandler(null);
        this.undoList = undoList;
        this.redoList = redoList;
        this.currentTeamKeeper = currentTeamKeeper;
        this.currentTeamUndoList = currentTeamUndoList;
        this.currentTeamRedoList = currentTeamRedoList;
    }

    public void execute() {
        if (undoList.isEmpty())
            in.sysMsg("Nothing to undo.");
        else {
            Command command = undoList.pop();
            redoList.push(command);
            command.undo();
            in.sysMsg("Command (" + command + ") is undone.");
        }

        if (!currentTeamUndoList.isEmpty()) {
            currentTeamRedoList.push(currentTeamKeeper.getCurrentTeam());
            currentTeamKeeper.setCurrentTeam(currentTeamUndoList.pop());
        }
    }

    public void undo() {
    }

    public void redo() {
    }
}
