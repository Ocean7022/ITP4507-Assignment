import java.util.Scanner;
import java.util.Vector;

public class CommandCreateTeam implements Command {
    private Team team;
    private Vector<Team> _teams;
    private int index;
    private Scanner sc;
    private String sportType;

    public CommandCreateTeam(Scanner sc, Vector<Team> _teams) {
        this.sc = sc;
        this._teams = _teams;
    }

    public void execute() {
        System.out.print("Enter sport type (v = volleyball, f = football): ");
        sportType = sc.nextLine();
        System.out.print("Team ID: ");
        String teamID = sc.nextLine();
        System.out.print("Team name: ");
        String teamName = sc.nextLine();

        if (sportType.equals("v")) {
            team = new VolleyballTeam(teamID);
            team.setName(teamName);
            _teams.add(team);
            index = _teams.size() - 1;
            System.out.println("Volleyball team is created.");
        } else if (sportType.equals("f")) {
            team = new FootballTeam(teamID);
            team.setName(teamName);
            _teams.add(team);
            index = _teams.size() - 1;
            System.out.println("Football team is created.");
        }
    }

    public void undo() {
        _teams.remove(index);
    }

    public void redo() {
        _teams.add(index, team);
    }

    public String toString() {
        if (sportType.equals("v"))
            return "Create Volleyball team, " + team.getTeamID() + ", " + team.getName();
        else
            return "Create Football team, " + team.getTeamID() + ", " + team.getName();
    }
}
