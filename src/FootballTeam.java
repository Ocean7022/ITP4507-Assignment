public class FootballTeam extends Team {
    private final int GOALKEEPER_POSITION = 1;
    private final int DEFENDER_POSITION = 2;
    private final int MIDFIELDER_POSITION = 3;
    private final int FORWARD_POSITION = 4;

    public FootballTeam(String teamID) {
        super(teamID);
    }

    public String[] getPositionsString() {
        String[] positions = new String[4];
        positions[0] = "Goalkeeper";
        positions[1] = "Defender";
        positions[2] = "Midfielder";
        positions[3] = "Forward";
        return positions;
    }

    public void displayTeam() {
        System.out.println("Football Team " + "(" + getTeamID() + ")" + getName());
    }

    public String toString() {
        return "Football Team " + "(" + getTeamID() + ")" + getName();
    }
}
