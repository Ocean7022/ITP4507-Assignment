public class PlayerMemento implements Memento {
    private Player player;
    private int oldP;
    private int newP;

    public PlayerMemento(Player player, int newP) {
        this.player = player;
        this.oldP = player.getPosition();
        this.newP = newP;
    }

    public void undo() {
        player.setPosition(oldP);
    }

    public void redo() {
        player.setPosition(newP);
    }
    
    public String toString() {
        return "Modify player’s position, " + player.getName() + ", " + oldP;
    }
}
