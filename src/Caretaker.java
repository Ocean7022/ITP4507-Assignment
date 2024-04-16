import java.util.Stack;

public class Caretaker {
    private Stack<Memento> undoList;
    private Stack<Memento> redoList;
    
    public Caretaker() {
        this.undoList = new Stack<Memento>();
        this.redoList = new Stack<Memento>();
    }

    public void saveTeamToUndo(Team team) {
        undoList.push(new MementoTeam(team));
    }

    public void saveTeamToRedo(Team team) {
        redoList.push(new MementoTeam(team));
    }

    public void savePlayerToUndo(Player player) {
        undoList.push(new MementoPlayer(player));
    }

    public void savePlayerToRedo(Player player) {
        redoList.push(new MementoPlayer(player));
    }

    public void undo() {
        undoList.pop().restore();
    }

    public void redo() {
        redoList.pop().restore();
    }

    public void clearRedoList() {
        redoList.clear();
    }
}
