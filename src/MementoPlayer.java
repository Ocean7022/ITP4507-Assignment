public class MementoPlayer implements Memento {
    private Player player;
    private String name;
    private int position;

    public MementoPlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.position = player.getPosition();
    }

    public void restore() {
        player.setName(name);
        player.setPosition(position);
    }
}
