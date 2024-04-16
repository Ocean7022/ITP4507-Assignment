public class VolleyballTeam extends Team {
    private final int ATTACKER_POSITION = 1;
    private final int DEFENDER_POSITION = 2;

    public VolleyballTeam(String teamID) {
        super(teamID);
    }

    public String[] getPositionsString() {
        String[] positions = new String[2];
        positions[0] = "Attacker";
        positions[1] = "Defender";
        return positions;
    }

    public void displayTeam() {
        System.out.println("Volleyball Team " + "(" + getTeamID() + ")" + getName());
    }

    public String toString() {
        return "Volleyball Team " + "(" + getTeamID() + ")" + getName();
    }
}
