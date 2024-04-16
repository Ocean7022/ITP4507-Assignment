public class CommandDeletePlayer implements Command {
    private InputHandler in;
    private Team team;
    private Player player;
    private int playerIndex;

    public CommandDeletePlayer(InputHandler in, Team team) {
        this.in = in;
        this.team = team;
    }

    public void execute() {
        Object[] playerInfo = in.askForPlayerIndex(team);
        playerIndex = (int) playerInfo[0];
        team.removePlayer(player = (Player) playerInfo[1]);
        in.sysMsg("Player is deleted.");
    }

    public void undo() {
        team.addPlayer(player, playerIndex);
    }

    public void redo() {
        team.removePlayer(player);
    }

    public String toString() {
        return "Delete Player - " + player;
    }
}
