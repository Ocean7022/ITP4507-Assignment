public class VolleyballTeam extends Team{
    private final int ATTACKER_POSITION = 1;
    private final int DEFENDER_POSITION = 2;

    public VolleyballTeam(String teamID) {
        super(teamID);
    }

    public void updatePlayerPosition() {

    }

    public void displayTeam() {
        System.out.println("Volleyball Team " + getName() + "(" + getTeamID() + ")");
    }
}
