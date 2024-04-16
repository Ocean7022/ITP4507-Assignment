import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Vector;

public class TestCommandSetCurrentTeam {
    private InputHandlerStub inputHandlerStub;
    private CurrentTeamKeeper currentTeamKeeper;
    private Command command;
    private Vector<Team> _teams;
    private String[] teamNames;
    private String[] teamID;

    @Before
    public void setUp() {
        inputHandlerStub = new InputHandlerStub();
        currentTeamKeeper = new CurrentTeamKeeper();
        command = new CommandSetCurrentTeam(inputHandlerStub, _teams, currentTeamKeeper);
        _teams = new Vector<Team>();
        teamNames = new String[] { "T002", "GG", "8888", "F#", "v8", "KFC", "6464!", ">.<", "DDDDFS", "@#$@#" };
        teamID = new String[] { "ZBB", "333", "@3", "GG", "T999", "7.7", "##$$$&", "22ddff", "#FFFFFF", "::::" };
    }

    @Test
    public void testExecute() {
        // Test with no team
        assertNull(currentTeamKeeper.getCurrentTeam());

        // Add teams to vector
        for (int i = 0; i < teamNames.length; i++) {
            Team team;
            if (i % 2 == 0)
                team = new VolleyballTeam(teamID[i]);
            else
                team = new FootballTeam(teamID[i]);

            team.setName(teamNames[i]);
            _teams.add(team);
        }

        // Test to set current team 20 times
        for (int i = 0; i < 20; i++) {
            int index = (int) (Math.random() * teamNames.length);
            inputHandlerStub.setTeam(_teams.get(index));
            command.execute();
            assertEquals(_teams.get(index), currentTeamKeeper.getCurrentTeam());
        }
    }

    @After
    public void tearDown() {
        inputHandlerStub = null;
        command = null;
        _teams = null;
        currentTeamKeeper = null;
        teamNames = null;
        teamID = null;
    }

    private class InputHandlerStub extends InputHandler {
        private Team team;

        public InputHandlerStub() {
            super(null);
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        @Override
        public Team askForTeamIDAndGetTeam(Vector<Team> _teams, Team currentTeam) {
            return team;
        }
    }
}
