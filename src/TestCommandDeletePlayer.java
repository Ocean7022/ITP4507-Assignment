import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.Stack;

public class TestCommandDeletePlayer {
    private InputHandlerStub inputHandlerStub;
    private Team currentTeam;
    private Command command;
    private String[] playerID;
    private String[] playerName;
    private Stack<Command> undoList;
    private Stack<Command> redoList;

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        currentTeam = new FootballTeam("T001");
        command = new CommandDeletePlayer(inputHandlerStub, currentTeam);
        playerID = new String[] { "P004", "P102", "P233", "332", "@123", "91966", "WDGV8", "WMI2X", "EHR6X", "XP72C" };
        playerName = new String[] {
                "Jacqueline Ramirez", "Charles Watts", "Kevin Perez", "Michael Powell",
                "Lisa Vargas", "Nancy Davis", "Cassandra Alexander", "Sherry Nguyen",
                "Daniel Villarreal", "Michael Hurley"
        };
        undoList = new Stack<Command>();
        redoList = new Stack<Command>();
        // Add 10 player
        for (int i = 0; i < playerID.length; i++) {
            Player player = new Player(playerID[i], playerName[i]);
            player.setPosition(1);
            currentTeam.addPlayer(player);
        }
    }

    @Test
    public void testExecute() {
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), 0);
            inputHandlerStub.setPlayer(player);
            // Test is player added
            assertEquals(playerID.length - i, getTeamPlayerLength(currentTeam));
            assertEquals(player, getPlayer(currentTeam.getPlayers(), player));
            command.execute();
            // Test is player deleted
            assertEquals(playerID.length - i - 1, getTeamPlayerLength(currentTeam));
            assertNull(getPlayer(currentTeam.getPlayers(), player));
        }
    }

    @Test
    public void testUndo() {
        Player[] players = new Player[playerID.length];
        // Delete 10 players
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), 0);
            players[i] = player;
            inputHandlerStub.setPlayer(player);
            Command command = new CommandDeletePlayer(inputHandlerStub, currentTeam);
            command.execute();
            undoList.push(command);
        }

        // Test undo
        for (int i = playerID.length - 1; i >= 0; i--) {
            Player player = players[i];

            // Test before undo
            assertEquals(playerID.length - i - 1, getTeamPlayerLength(currentTeam));
            assertNull(getPlayer(currentTeam.getPlayers(), player));
            undoList.pop().undo();
            // Test is player added
            assertEquals(playerID.length - i, getTeamPlayerLength(currentTeam));
            assertEquals(player, getPlayer(currentTeam.getPlayers(), player));
        }
    }

    @Test
    public void testRedo() {
        Player[] players = new Player[playerID.length];
        // Delete 10 players
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), 0);
            players[i] = player;
            inputHandlerStub.setPlayer(player);
            Command command = new CommandDeletePlayer(inputHandlerStub, currentTeam);
            command.execute();
            undoList.push(command);
        }

        // Undo 10 times
        for (int i = playerID.length - 1; i >= 0; i--) {
            Command command = undoList.pop();
            command.undo();
            redoList.push(command);
        }

        // Test redo
        for (int i = 0; i < playerID.length; i++) {
            Player player = players[i];

            // Test before redo
            assertEquals(playerID.length - i, getTeamPlayerLength(currentTeam));
            assertEquals(player, getPlayer(currentTeam.getPlayers(), player));
            redoList.pop().redo();
            // Test is player deleted
            assertEquals(playerID.length - i - 1, getTeamPlayerLength(currentTeam));
            assertNull(getPlayer(currentTeam.getPlayers(), player));
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), 0);
            inputHandlerStub.setPlayer(player);
            command.execute();
            assertEquals("Delete Player - " + player, command.toString());
        }
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        currentTeam = null;
        command = null;
        playerID = null;
        playerName = null;
        undoList = null;
        redoList = null;
    }

    private int getTeamPlayerLength(Team team) {
        int length = 0;
        Enumeration<Player> players = team.getPlayers();
        while (players.hasMoreElements()) {
            players.nextElement();
            length++;
        }
        return length;
    }

    private Player getPlayer(Enumeration<Player> players, Player player) {
        while (players.hasMoreElements()) {
            Player p = players.nextElement();
            if (p.equals(player))
                return p;
        }
        return null;

    }

    private Player getPlayer(Enumeration<Player> players, int index) {
        int i = 0;
        while (players.hasMoreElements()) {
            Player player = players.nextElement();
            if (i == index)
                return player;
            i++;
        }
        return null;
    }

    private class InputHandlerStub extends InputHandler {
        private Player player;

        public InputHandlerStub() {
            super(null);
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        @Override
        public Object[] askForPlayerIndex(Team team) {
            int index = 0;
            Enumeration<Player> players = team.getPlayers();
            while (players.hasMoreElements()) {
                Player player = players.nextElement();
                if (player.equals(this.player))
                    break;
                index++;
            }
            return new Object[] { index, player };
        }
    }
}
