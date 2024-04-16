import java.util.Vector;

public class FactoryFootballTeam implements FactoryTeam {

    public Team createTeam(InputHandler in, Vector<Team> _teams) {
        Team team = new FootballTeam(in.askForTeamID(_teams));
        String teamName = in.askForTeamName(_teams);
        team.setName(teamName);
        return team;
    }
}
