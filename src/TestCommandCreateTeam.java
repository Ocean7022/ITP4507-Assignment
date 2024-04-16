import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Stack;
import java.util.Vector;

public class TestCommandCreateTeam {
    private InputHandlerStub inputHandlerStub;
    private Vector<Team> _teams;
    private CurrentTeamKeeper currentTeamKeeper;
    private Command command;
    private String[] teamNames;
    private String[] teamID;
    private String[] sportType;
    private String[] className;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        _teams = new Vector<Team>();
        currentTeamKeeper = new CurrentTeamKeeper();
        command = new CommandCreateTeam(inputHandlerStub, _teams, currentTeamKeeper);
        teamNames = new String[] { "T002", "GG", "8888", "F#", "v8", "KFC", "6464!", ">.<", "DDDDFS", "@#$@#" };
        teamID = new String[] { "ZBB", "333", "@3", "GG", "T999", "7.7", "##$$$&", "22ddff", "#FFFFFF", "::::" };
        sportType = new String[] { "Volleyball", "Football" };
        className = new String[] { "VolleyballTeam", "FootballTeam" };
        undoStack = new Stack<Command>();
        redoStack = new Stack<Command>();
    }

    @Test
    public void testExecute() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setSportType(sportType[i % 2]);
            inputHandlerStub.setTeamID(teamID[i]);
            inputHandlerStub.setTeamName(teamNames[i]);
            command.execute();
            assertEquals(i + 1, _teams.size());
            assertEquals(teamID[i], _teams.get(i).getTeamID());
            assertEquals(teamNames[i], _teams.get(i).getName());
            assertEquals(className[i % 2], _teams.get(i).getClass().getSimpleName());
            assertEquals(_teams.get(i), currentTeamKeeper.getCurrentTeam());
        }
    }

    @Test
    public void testUndo() {
        // Add 10 teams
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setSportType(sportType[i % 2]);
            inputHandlerStub.setTeamID(teamID[i]);
            inputHandlerStub.setTeamName(teamNames[i]);
            command = new CommandCreateTeam(inputHandlerStub, _teams, currentTeamKeeper);
            command.execute();
            undoStack.push(command);
        }
        // Test undo
        for (int i = teamNames.length - 1; i >= 0; i--) {
            Team team = _teams.get(_teams.size() - 1);
            undoStack.pop().undo();
            assertFalse(isTeamExist(_teams, team));
        }
    }

    @Test
    public void testRedo() {
        // Add 10 teams
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setSportType(sportType[i % 2]);
            inputHandlerStub.setTeamID(teamID[i]);
            inputHandlerStub.setTeamName(teamNames[i]);
            command = new CommandCreateTeam(inputHandlerStub, _teams, currentTeamKeeper);
            command.execute();
            undoStack.push(command);
        }
        // Undo command
        for (int i = 0; i < teamNames.length; i++) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
        // Test redo
        for (int i = 0; i < teamNames.length; i++) {
            redoStack.pop().redo();
            Team team = _teams.get(_teams.size() - 1);
            assertEquals(teamID[i], team.getTeamID());
            assertEquals(teamNames[i], team.getName());
            assertEquals(className[i % 2], team.getClass().getSimpleName());            
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < teamNames.length; i++) {
            inputHandlerStub.setSportType(sportType[i % 2]);
            inputHandlerStub.setTeamID(teamID[i]);
            inputHandlerStub.setTeamName(teamNames[i]);
            command = new CommandCreateTeam(inputHandlerStub, _teams, currentTeamKeeper);
            command.execute();
            assertEquals("Create " + sportType[i % 2] + " team (" + teamID[i] + ")" + teamNames[i], command.toString());
        }
    }

    private boolean isTeamExist(Vector<Team> _teams, Team team) {
        for (Team t : _teams) {
            if (t.getTeamID().equals(team.getTeamID()))
                return true;
        }
        return false;
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        _teams = null;
        currentTeamKeeper = null;
        command = null;
        teamNames = null;
        teamID = null;
        sportType = null;
        className = null;
    }

    private class InputHandlerStub extends InputHandler {
        private String sportType;
        private String teamID;
        private String teamName;

        public InputHandlerStub() {
            super(null);
        }

        public void setSportType(String sportType) {
            this.sportType = sportType;
        }

        public void setTeamID(String teamID) {
            this.teamID = teamID;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        @Override
        public String askForSportType() {
            return sportType;
        }

        @Override
        public String askForTeamID(Vector<Team> teams) {
            return teamID;
        }

        @Override
        public String askForTeamName(Vector<Team> teams) {
            return teamName;
        }
    }
}
