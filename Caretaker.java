import java.util.Stack;

public class Caretaker {
    private Stack<Memento> undoStack;
    private Stack<Memento> redoStack;

    public Caretaker() {
        undoStack = new Stack<Memento>();
        redoStack = new Stack<Memento>();
    }

    public void savePlayer(Player player, int newP) {
        undoStack.push(new PlayerMemento(player, newP));
    }

    public void saveTeam(Team team, String newName) {
        undoStack.push(new TeamMemento(team, newName));
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
        } else {
            Memento m = undoStack.pop();
            redoStack.push(m);
            m.undo();
            System.out.println("Command (" + m + ") is undone.");
        }
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
        } else {
            Memento m = redoStack.pop();
            undoStack.push(m);
            m.redo();
            System.out.println("Command (" + m + ") is redone.");
        }
    }

    public Memento getMementoUndo(int index) {
        return undoStack.get(index);
    }

    public Memento getMementoRedo(int index) {
        return redoStack.get(index);
    }
}
