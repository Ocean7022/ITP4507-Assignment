public class Player {
    private String playerID;
    private String name;
    private int position;

    public Player(String id, String name) {
        this.playerID = id;
        this.name = name;
    }

    public String getPlayerID() {
        return playerID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
