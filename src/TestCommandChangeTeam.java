import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Stack;
import java.util.Vector;

public class TestCommandChangeTeam {
    private InputHandlerStub inputHandlerStub;
    private Caretaker caretaker;
    private Vector<Team> _teams;
    private Team currentTeams;
    private Command command;
    private String[] teamNames;
    private Stack<Command> undoList;
    private Stack<Command> redoList;    

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        caretaker = new Caretaker();
        _teams = new Vector<Team>();
        currentTeams = new FootballTeam("T001");
        currentTeams.setName("ABC");
        teamNames = new String[] { "T002", "GG", "8888", "F#", "v8", "KFC", "6464!", ">.<", "DDDDFS", "@#$@#" };
        undoList = new Stack<Command>();
        redoList = new Stack<Command>();
    }

    @Test
    public void testExecute() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setNewTeamName(teamNames[i]);
            command = new CommandChangeTeam(inputHandlerStub, caretaker, _teams, currentTeams);
            command.execute();
            assertEquals(teamNames[i], currentTeams.getName());
        }
    }

    @Test
    public void testUndo() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setNewTeamName(teamNames[i]);
            command = new CommandChangeTeam(inputHandlerStub, caretaker, _teams, currentTeams);
            command.execute();
            undoList.push(command);
        }

        // Test undo
        for (int i = teamNames.length - 1; i > 0; i--) {
            command = undoList.pop();
            // Test before undo
            assertEquals(teamNames[i], currentTeams.getName());
            command.undo();
            // Test after undo
            assertEquals(teamNames[i - 1], currentTeams.getName());
        }
    }

    @Test
    public void testRedo() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setNewTeamName(teamNames[i]);
            command = new CommandChangeTeam(inputHandlerStub, caretaker, _teams, currentTeams);
            command.execute();
            undoList.push(command);
        }

        // Undo command
        for (int i = teamNames.length - 1; i > 0; i--) {
            command = undoList.pop();
            command.undo();
            redoList.push(command);
        }

        // Test redo
        for (int i = 0; i < teamNames.length - 1; i++) {
            command = redoList.pop();
            // Test before redo
            assertEquals(teamNames[i], currentTeams.getName());
            command.redo();
            // Test after redo
            assertEquals(teamNames[i + 1], currentTeams.getName());
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setNewTeamName(teamNames[i]);
            String oldName = currentTeams.getName();
            command = new CommandChangeTeam(inputHandlerStub, caretaker, _teams, currentTeams);
            command.execute();
            assertEquals("Change team's (T001) name, [" + oldName + "] to [" + teamNames[i] + "]", command.toString());
        }
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        caretaker = null;
        _teams = null;
        currentTeams = null;
        command = null;
        teamNames = null;
        undoList = null;
        redoList = null;
    }

    private class InputHandlerStub extends InputHandler {
        private String newTeamName;

        public InputHandlerStub() {
            super(null);
        }

        public void setNewTeamName(String newTeamName) {
            this.newTeamName = newTeamName;
        }

        public String askForNewTeamName(Vector<Team> _teams, Team currentTeam) {
            return newTeamName;
        }
    }
}
