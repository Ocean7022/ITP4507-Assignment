import java.util.Scanner;

public class CommandAddPlayer implements Command {
    private Team team;
    private Player player;
    private Scanner sc;
    private String pos;

    public CommandAddPlayer(Scanner sc, Team team) {
        this.sc = sc;
        this.team = team;
    }
    
    public void execute() {
        System.out.print("Please input player information (ID, name): ");
        String playerInfo = sc.nextLine();
        String[] playerInfoArr = playerInfo.split(",");
        player = new Player(playerInfoArr[0], playerInfoArr[1]);
        if (team.getClass().getSimpleName().equals("VolleyballTeam")) {
            System.out.print("Position (1 = Attacker | 2 = Defender): ");
        } else {
            System.out.print("Position (1 = Goalkeeper | 2 = Defender | 3 = Midfielder | 4 = Forward):");
        }
        int position = sc.nextInt();
        sc.nextLine();
        player.setPosition(position);
        team.addPlayer(player);
        System.out.println("Player is added.");

        if (team.getClass().getSimpleName().equals("VolleyballTeam")) {
            if (position == 1) {
                pos = "Attacker";
            } else {
                pos = "Defender";
            }
        } else {
            if (position == 1) {
                pos = "Goalkeeper";
            } else if (position == 2) {
                pos = "Defender";
            } else if (position == 3) {
                pos = "Midfielder";
            } else {
                pos = "Forward";
            }
        }
    }

    public void undo() {
        team.removePlayer(player);
    }

    public void redo() {
        team.addPlayer(player);
    }

    public String toString() {
        return "Add player, " + player.getPlayerID() + ", " + player.getName() + ", " + pos;
    }
}
