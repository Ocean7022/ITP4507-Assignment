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
        if (undoList.size() == 0) {
            System.out.println("No undo command.");
        } else {
            for (int i = undoList.size() - 1; i >= 0; i--)
                System.out.println(undoList.get(i));
        }
        System.out.println("-- End of redo list --");

        System.out.println("Redo List:");
        if (redoList.size() == 0) {
            System.out.println("No redo command.");
        } else {
            for (int i = redoList.size() - 1; i >= 0; i--)
                System.out.println(redoList.get(i));
        }
        System.out.println("-- End of redo list --");

    }

    public void undo() {
    }

    public void redo() {
    }
}
