import java.util.Enumeration;
import java.util.Scanner;

public class CommandDelete implements Command {
    private Scanner sc;
    private Team team;
    private Player player;

    public CommandDelete(Scanner sc, Team team) {
        this.sc = sc;
        this.team = team;
    }

    public void execute() {
        System.out.print("Please input player ID: ");
        String playerID = sc.nextLine();
        Enumeration<Player> players = team.getPlayers();
        while (players.hasMoreElements()) {
            Player p = players.nextElement();
            if (p.getPlayerID().equals(playerID)) {
                player = p;
                break;
            }
        }
        team.removePlayer(player);
        System.out.println("Player is deleted.");
    }

    public void undo() {
        team.addPlayer(player);
    }

    public void redo() {
        team.removePlayer(player);
    }

    public String toString() {
        return "Delete player, " + player;
    }
}
