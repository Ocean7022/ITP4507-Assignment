import java.util.Enumeration;

public class CommandShowTeam implements Command {
    private InputHandler in;
    private Team team;

    public CommandShowTeam(InputHandler in, Team team) {
        this.in = in;
        this.team = team;
    }

    public void execute() {
        if (team == null) {
            in.sysMsg("No team is selected.");
            return;
        }

        System.out.println(team + " infroamtion:");
        String[] positions = team.getPositionsString();

        for (int index = 0; index < positions.length; index++) {
            int conutPlayer = 0;
            System.out.println(positions[index] + ":");
            Enumeration<Player> players = team.getPlayers();
            
            while (players.hasMoreElements()) {
                Player player = players.nextElement();
                if (player.getPosition() == index + 1) {
                    System.out.println(" - " + player);
                    conutPlayer++;
                }
            }
            if (conutPlayer == 0)
                System.out.println(" - No player in this position -");
        }
    }

    public void undo() {
    }

    public void redo() {
    }

}
