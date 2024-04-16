import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.Stack;

public class TestCommandModifyPlayer {
    private InputHandlerStub inputHandlerStub;
    private Caretaker caretaker;
    private Team currentTeam;
    private Command command;
    private String[] playerID;
    private String[] playerName;
    private int[] positionArraySet;
    private int[] positionArrayModify;
    private Stack<Command> undoList;
    private Stack<Command> redoList;

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        caretaker = new Caretaker();
        currentTeam = new FootballTeam("T001");
        command = new CommandModifyPlayer(inputHandlerStub, caretaker, currentTeam);
        playerID = new String[] { "P004", "P102", "P233", "332", "@123", "91966", "WDGV8", "WMI2X", "EHR6X", "XP72C" };
        playerName = new String[] {
                "Jacqueline Ramirez", "Charles Watts", "Kevin Perez", "Michael Powell",
                "Lisa Vargas", "Nancy Davis", "Cassandra Alexander", "Sherry Nguyen",
                "Daniel Villarreal", "Michael Hurley"
        };
        positionArraySet = new int[] { 1, 2, 3, 4, 1, 2, 3, 4, 1, 2 }; 
        positionArrayModify = new int[] { 2, 3, 4, 1, 2, 3, 4, 1, 2, 3 };
        undoList = new Stack<Command>();
        redoList = new Stack<Command>();
        // Add 10 player
        for (int i = 0; i < playerID.length; i++) {
            Player player = new Player(playerID[i], playerName[i]);
            player.setPosition(positionArraySet[i]);
            currentTeam.addPlayer(player);
        }
    }

    @Test
    public void testExecute() {
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), i);
            inputHandlerStub.setPlayer(player);
            inputHandlerStub.setPosition(positionArrayModify[i]);
            // Test before execute
            assertEquals(positionArraySet[i], player.getPosition());
            command.execute();
            // Test after execute
            assertEquals(positionArrayModify[i], player.getPosition());
        }
    }

    @Test
    public void testUndo() {
        // Modify 10 players
        for (int i = 0; i < playerID.length; i++) {
            Command command = new CommandModifyPlayer(inputHandlerStub, caretaker, currentTeam);
            inputHandlerStub.setPlayer(getPlayer(currentTeam.getPlayers(), i));
            inputHandlerStub.setPosition(positionArrayModify[i]);
            command.execute();
            undoList.push(command);
        }

        // Test undo
        for (int i = playerID.length - 1; i >= 0; i--) {
            Command command = undoList.pop();
            Player player = getPlayer(currentTeam.getPlayers(), i);
            // Test before undo
            assertEquals(positionArrayModify[i], player.getPosition());
            command.undo();
            // Test after undo
            assertEquals(positionArraySet[i], player.getPosition());
        }
    }

    @Test
    public void testRedo() {
        // Modify 10 players
        for (int i = 0; i < playerID.length; i++) {
            Command command = new CommandModifyPlayer(inputHandlerStub, caretaker, currentTeam);
            inputHandlerStub.setPlayer(getPlayer(currentTeam.getPlayers(), i));
            inputHandlerStub.setPosition(positionArrayModify[i]);
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
            Command command = redoList.pop();
            Player player = getPlayer(currentTeam.getPlayers(), i);
            // Test before redo
            assertEquals(positionArraySet[i], player.getPosition());
            command.redo();
            // Test after redo
            assertEquals(positionArrayModify[i], player.getPosition());
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < playerID.length; i++) {
            Player player = getPlayer(currentTeam.getPlayers(), i);
            inputHandlerStub.setPlayer(player);
            inputHandlerStub.setPosition(positionArrayModify[i]);
            command.execute();
            assertEquals("Modify player’s position (" + player.getPlayerID() + ")" + player.getName() + " - "
                    + currentTeam.getPositionsString()[positionArraySet[i] - 1] + " to "
                    + currentTeam.getPositionsString()[positionArrayModify[i] - 1], command.toString());
        }
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        caretaker = null;
        currentTeam = null;
        command = null;
        playerID = null;
        playerName = null;
        positionArraySet = null;
        positionArrayModify = null;
        undoList = null;
        redoList = null;
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
        Player player;
        int position;

        public InputHandlerStub() {
            super(null);
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public Object[] askForIdAndPosition(Team team) {
            return new Object[] { player, position };
        }
    }
}
