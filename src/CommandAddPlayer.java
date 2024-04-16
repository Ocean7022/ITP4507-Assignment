public class CommandAddPlayer implements Command {
    private InputHandler in;
    private Team team;
    private Player player;
    private String position;

    public CommandAddPlayer(InputHandler in, Team team) {
        this.in = in;
        this.team = team;
    }

    public void execute() {
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        team.addPlayer(player = factoryPlayer.createPlayer(in, team));
        position = team.getPositionsString()[player.getPosition() - 1];
        in.sysMsg("Player is added.");
    }

    public void undo() {
        team.removePlayer(player);
    }

    public void redo() {
        team.addPlayer(player);
    }

    public String toString() {
        return "Add player " + "(" + player.getPlayerID() + ")" + player.getName() + " - " + position;
    }
}
