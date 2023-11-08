import java.util.Stack;

public class CommandListUndoRedo implements Command {
    private Stack<Command> undoList;
    private Stack<Command> redoList;
    private Stack<String> undoTypeList;
    private Stack<String> redoTypeList;
    private Caretaker caretaker;

    public CommandListUndoRedo(Stack<Command> undoList, Stack<Command> redoList, Stack<String> undoTypeList,
            Stack<String> redoTypeList, Caretaker caretaker) {
        this.undoList = undoList;
        this.redoList = redoList;
        this.undoTypeList = undoTypeList;
        this.redoTypeList = redoTypeList;
        this.caretaker = caretaker;
    }

    public void execute() {
        int command = 0;
        int memento = 0;
        System.out.println("Undo List:");
        if (undoTypeList.size() == 0) {
            System.out.println("No undo command.");
        } else {
            command = 0;
            memento = 0;
            for (int i = 0; i < undoTypeList.size(); i++) {
                if (undoTypeList.get(i).equals("command")) {
                    System.out.println(undoList.get(i - memento));
                    command++;
                } else {
                    System.out.println(caretaker.getMementoUndo(i - command));
                    memento++;
                }
            }
        }
        System.out.println("-- End of redo list --");

        System.out.println("Redo List:");
        if (redoTypeList.size() == 0) {
            System.out.println("No redo command.");
        } else {
            command = 0;
            memento = 0;
            for (int i = 0; i < redoTypeList.size(); i++) {
                if (redoTypeList.get(i).equals("command")) {
                    System.out.println(redoList.get(i - memento));
                    command++;
                } else {
                    System.out.println(caretaker.getMementoRedo(i - command));
                    memento++;
                }
            }
        }
        System.out.println("-- End of redo list --");

    }

    public void undo() {
    }

    public void redo() {
    }
}
