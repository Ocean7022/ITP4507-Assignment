import java.util.Enumeration;
import java.util.Vector;

public class Team {
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

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Enumeration<Player> getPlayers() {
        return players.elements();
    }

    public void updatePlayerPosition() {
    }

    public void displayTeam() {
    }
}

