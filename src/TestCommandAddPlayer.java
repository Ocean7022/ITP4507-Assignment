import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.Stack;

public class TestCommandAddPlayer {
    private InputHandlerStub inputHandlerStub;
    private Command command;
    private Team[] team;
    private String[] playerID;
    private String[] playerName;
    private Stack<Command> undoList;
    private Stack<Command> redoList;

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        team = new Team[] { new VolleyballTeam("T001"), new FootballTeam("T002") };
        playerID = new String[] { "P004", "P102", "P233", "332", "@123", "91966", "WDGV8", "WMI2X", "EHR6X", "XP72C" };
        playerName = new String[] {
                "Jacqueline Ramirez", "Charles Watts", "Kevin Perez", "Michael Powell",
                "Lisa Vargas", "Nancy Davis", "Cassandra Alexander", "Sherry Nguyen",
                "Daniel Villarreal", "Michael Hurley"
        };
        undoList = new Stack<Command>();
        redoList = new Stack<Command>();
    }

    @Test
    public void testExecute() {
        for (int i = 0; i < playerID.length; i++) {
            inputHandlerStub.setPlayerID(playerID[i]);
            inputHandlerStub.setPlayerName(playerName[i]);
            int position = 0;
            if (i % 2 == 0)
                position = (int) (Math.random() * 2) + 1;
            else
                position = (int) (Math.random() * 4) + 1;
            inputHandlerStub.setPosition(position);
            command = new CommandAddPlayer(inputHandlerStub, team[i % 2]);
            command.execute();
            assertEquals(playerID[i], getLastPlayer(team[i % 2].getPlayers()).getPlayerID());
            assertEquals(playerName[i], getLastPlayer(team[i % 2].getPlayers()).getName());
            assertEquals(position, getLastPlayer(team[i % 2].getPlayers()).getPosition());
        }
    }

    @Test
    public void testUndo() {
        int[] positionArray = new int[playerID.length];
        // Add 10 players
        for (int i = 0; i < playerID.length; i++) {
            inputHandlerStub.setPlayerID(playerID[i]);
            inputHandlerStub.setPlayerName(playerName[i]);
            int position = 0;
            if (i % 2 == 0)
                position = (int) (Math.random() * 2) + 1;
            else
                position = (int) (Math.random() * 4) + 1;
            positionArray[i] = position;
            inputHandlerStub.setPosition(position);
            command = new CommandAddPlayer(inputHandlerStub, team[i % 2]);
            command.execute();
            undoList.push(command);
            System.out.println("index: " + i + " = " + team[i % 2]);
        }

        for (int i = playerID.length - 1; i > 0; i--) {
            // Test before undo
            assertEquals(playerID[i], getLastPlayer(team[i % 2].getPlayers()).getPlayerID());
            assertEquals(playerName[i], getLastPlayer(team[i % 2].getPlayers()).getName());
            assertEquals(positionArray[i], getLastPlayer(team[i % 2].getPlayers()).getPosition());
            undoList.pop().undo();
            // Test after undo
            assertEquals(playerID[i - 1], getLastPlayer(team[(i - 1) % 2].getPlayers()).getPlayerID());
            assertEquals(playerName[i - 1], getLastPlayer(team[(i - 1) % 2].getPlayers()).getName());
            assertEquals(positionArray[i - 1], getLastPlayer(team[(i - 1) % 2].getPlayers()).getPosition());
        }
    }

    @Test
    public void testRedo() {
        int[] positionArray = new int[playerID.length];
        // Add 10 players
        for (int i = 0; i < playerID.length; i++) {
            inputHandlerStub.setPlayerID(playerID[i]);
            inputHandlerStub.setPlayerName(playerName[i]);
            int position = 0;
            if (i % 2 == 0)
                position = (int) (Math.random() * 2) + 1;
            else
                position = (int) (Math.random() * 4) + 1;
            positionArray[i] = position;
            inputHandlerStub.setPosition(position);
            command = new CommandAddPlayer(inputHandlerStub, team[i % 2]);
            command.execute();
            undoList.push(command);
            System.out.println("index: " + i + " = " + team[i % 2]);
        }

        // Undo 10 times
        for (int i = playerID.length - 1; i > 0; i--) {
            Command command = undoList.pop();
            command.undo();
            redoList.push(command);
        }

        // Test redo
        for (int i = 0; i < playerID.length - 1; i++) {
            redoList.pop().redo();
            assertEquals(playerID[i + 1], getLastPlayer(team[(i + 1) % 2].getPlayers()).getPlayerID());
            assertEquals(playerName[i + 1], getLastPlayer(team[(i + 1) % 2].getPlayers()).getName());
            assertEquals(positionArray[i + 1], getLastPlayer(team[(i + 1) % 2].getPlayers()).getPosition());
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < playerID.length; i++) {
            inputHandlerStub.setPlayerID(playerID[i]);
            inputHandlerStub.setPlayerName(playerName[i]);
            int position = 0;
            if (i % 2 == 0)
                position = (int) (Math.random() * 2) + 1;
            else
                position = (int) (Math.random() * 4) + 1;
            inputHandlerStub.setPosition(position);
            command = new CommandAddPlayer(inputHandlerStub, team[i % 2]);
            command.execute();
            assertEquals("Add player (" + playerID[i] + ")" + playerName[i] + " - "
                    + team[i % 2].getPositionsString()[position - 1], command.toString());
        }
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        command = null;
        team = null;
        playerID = null;
        playerName = null;
        undoList = null;
        redoList = null;
    }

    private Player getLastPlayer(Enumeration<Player> players) {
        Player lastPlayer = null;
        while (players.hasMoreElements())
            lastPlayer = players.nextElement();
        return lastPlayer;
    }

    private class InputHandlerStub extends InputHandler {
        private String playerID;
        private String playerName;
        private int position;

        public InputHandlerStub() {
            super(null);
        }

        public void setPlayerID(String playerID) {
            this.playerID = playerID;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public String[] askForPlayerIdAndName(Team team) {
            return new String[] { playerID, playerName };
        }

        @Override
        public int askForPlayerPosition(Team team) {
            return position;
        }
    }
}
