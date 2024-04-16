public class FactoryPlayer {
    public Player createPlayer(InputHandler in, Team team) {
        String[] playerInfo = in.askForPlayerIdAndName(team);
        Player player = new Player(playerInfo[0], playerInfo[1]);
        player.setPosition(in.askForPlayerPosition(team));
        return player;
    }
}
