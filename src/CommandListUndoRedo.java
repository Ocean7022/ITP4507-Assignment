import java.util.Stack;

public class CommandListUndoRedo implements Command {
    private Stack<Command> undoList;
    private Stack<Command> redoList;

    public CommandListUndoRedo(Stack<Command> undoList, Stack<Command> redoList) {
        this.undoList = undoList;
        this.redoList = redoList;
    }

    public void execute() {
        System.out.println("Undo List:");
        if (undoList.isEmpty())
            System.out.println("No undo command.");
        else
            for (Command command : undoList)
                System.out.println(command);
        System.out.println("-- End of undo list --");

        System.out.println("Redo List:");
        if (redoList.isEmpty())
            System.out.println("No redo command.");
        else
            for (Command command : redoList)
                System.out.println(command);
        System.out.println("-- End of redo list --");
    }

    public void undo() {
    }

    public void redo() {
    }
}
