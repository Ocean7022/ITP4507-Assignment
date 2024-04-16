public class CommandModifyPlayer implements Command {
    private InputHandler in;
    private Caretaker caretaker;
    private Team currentTeam;
    private Player player;
    private int oldPosition, newPosition;
    private String[] positionsString;
    
    public CommandModifyPlayer(InputHandler in, Caretaker caretaker, Team currentTeam) {
        this.in = in;
        this.caretaker = caretaker;
        this.currentTeam = currentTeam;
    }

    public void execute() {
        Object[] info = in.askForIdAndPosition(currentTeam);
        player = (Player) info[0];
        newPosition = (int) info[1];
        oldPosition = player.getPosition();
        positionsString = currentTeam.getPositionsString();

        caretaker.savePlayerToUndo(player);
        in.sysMsg("Player’s position [" + positionsString[player.getPosition() - 1] + "] is changed to [" + positionsString[newPosition - 1] + "].");
        currentTeam.updatePlayerPosition(player.getPlayerID(), newPosition);
    }

    public void undo() {
        caretaker.savePlayerToRedo(player);
        caretaker.undo();
    }
    
    public void redo() {
        caretaker.savePlayerToUndo(player);
        caretaker.redo();
    }

    public String toString() {
        return "Modify player’s position (" + player.getPlayerID() + ")" + player.getName() + " - " + positionsString[oldPosition - 1] + " to " + positionsString[newPosition - 1];
    }
}
