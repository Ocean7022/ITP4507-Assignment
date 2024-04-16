import java.util.Enumeration;
import java.util.Vector;

public abstract class Team {
    private String teamID;
    private String name;
    private Vector<Player> players;

    public Team(String teamID) {
        this.teamID = teamID;
        players = new Vector<Player>();
    }

    public String getTeamID() {
        return teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addPlayer(Player player, int index) {
        players.add(index, player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Enumeration<Player> getPlayers() {
        return players.elements();
    }

    public void updatePlayerPosition(String playerID, int newPosition) {
        Enumeration<Player> players = getPlayers();
        while (players.hasMoreElements()) {
            Player player = players.nextElement();
            if (player.getPlayerID().equals(playerID)) {
                player.setPosition(newPosition);
                break;
            }
        }
    };

    public abstract String[] getPositionsString();

    public abstract void displayTeam();
}

