import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

// This class is used to ask user for input and check if the input is valid
public class InputHandler {
    private Scanner sc;
    private String inputErrorString, systemMessageString;

    public InputHandler(Scanner sc) {
        this.sc = sc;
        this.inputErrorString = "[Input Error] - ";
        this.systemMessageString = "[System Message] - ";
    }

    // Create Team
    public String askForSportType() {
        System.out.print("Enter sport type (v = volleyball, f = football): ");
        String sportType = sc.nextLine();

        if (!sportType.equals("v") && !sportType.equals("f")) {
            errMsg("Invalid sport type!");
            return askForSportType();
        } else {
            if (sportType.equals("v"))
                return "Volleyball";
            else
                return "Football";
        }
    }

    // Create Team
    public String askForTeamID(Vector<Team> _teams) {
        System.out.print("Team ID: ");
        String teamID = sc.nextLine();

        if (isNull(teamID))
            return askForTeamID(_teams);
        for (Team team : _teams) {
            if (team.getTeamID().equals(teamID)) {
                errMsg("Team ID already exists!");
                return askForTeamID(_teams);
            }
        }
        return teamID;
    }

    // Create Team
    public String askForTeamName(Vector<Team> _teams) {
        System.out.print("Team name: ");
        String teamName = sc.nextLine();

        if (isNull(teamName))
            return askForTeamName(_teams);
        for (Team team : _teams) {
            if (team.getName().equals(teamName)) {
                errMsg("Team name already exists!");
                return askForTeamName(_teams);
            }
        }
        return teamName;
    }

    // Change team’s name
    public String askForNewTeamName(Vector<Team> _teams, Team currentTeam) {
        System.out.print("Please input new name of the current team: ");
        String newTeamName = sc.nextLine();

        if (isNull(newTeamName))
            return askForNewTeamName(_teams, currentTeam);
        if (newTeamName.equals(currentTeam.getName())) {
            errMsg("New team name is the same as the current team name!");
            return askForNewTeamName(_teams, currentTeam);
        }
        for (Team team : _teams) {
            if (team.getName().equals(newTeamName)) {
                errMsg("Team name already exists!");
                return askForNewTeamName(_teams, currentTeam);
            }
        }
        return newTeamName;
    }

    // Add Player
    public String[] askForPlayerIdAndName(Team team) {
        System.out.print("Please input player information (ID, name): ");
        String[] playerInfo = sc.nextLine().split("\\s*,\\s*");

        if (playerInfo.length != 2) {
            errMsg("Invalid input! Please follow the format (ID, name)");
            return askForPlayerIdAndName(team);
        }
        if (isNull(playerInfo[0]) || isNull(playerInfo[1]))
            return askForPlayerIdAndName(team);

        Enumeration<Player> players = team.getPlayers();
        while (players.hasMoreElements()) {
            Player player = players.nextElement();
            if (player.getPlayerID().equals(playerInfo[0])) {
                errMsg("Player ID already exists!");
                return askForPlayerIdAndName(team);
            }
        }
        return playerInfo;
    }

    // Add Player || Modify Player’s Position
    public int askForPlayerPosition(Team team) {
        String[] positions = team.getPositionsString();

        System.out.print("Position (");
        for (int numOfPositions = 0; numOfPositions < positions.length; numOfPositions++) // Print out all positions
            System.out.print(numOfPositions + 1 + " = " + positions[numOfPositions]
                    + (numOfPositions == positions.length - 1 ? "" : " | "));
        System.out.print("): ");

        if (!sc.hasNextInt()) { // Check if input is integer
            errMsg("Invalid input! Please enter number only!");
            sc.nextLine();
            return askForPlayerPosition(team);
        }
        int position = sc.nextInt();
        sc.nextLine();

        if (position >= 1 && position <= positions.length)
            return position;
        errMsg("Unknown Position! Please try again.");
        return askForPlayerPosition(team);
    }

    // Change current team
    public Team askForTeamIDAndGetTeam(Vector<Team> _teams, Team currentTeam) {
        System.out.print("Please input team ID: ");
        String teamID = sc.nextLine();

        if (isNull(teamID))
            return currentTeam;
        if ( currentTeam != null && currentTeam.getTeamID().equals(teamID)) {
            errMsg("Team ID is the same as the current team ID!");
            return currentTeam;
        }
        for (Team team : _teams) // Get team by team ID
            if (team.getTeamID().equals(teamID)) {
                sysMsg("Team " + team.getName() + " is selected.");
                return team;
            }
        errMsg("Team ID does not exist!");
        return currentTeam;
    }

    // Remove Player
    public Object[] askForPlayerIndex(Team team) {
        System.out.print("Please input player ID: ");
        String playerID = sc.nextLine();

        if (isNull(playerID))
            return askForPlayerIndex(team);

        Enumeration<Player> players = team.getPlayers();
        int index = 0;
        while (players.hasMoreElements()) {
            Player player = players.nextElement();
            if (player.getPlayerID().equals(playerID))
                return new Object[] { index, player };
            index++;
        }
        errMsg("Player ID " + playerID + " does not exist!");
        return askForPlayerIndex(team);
    }

    // Modify Player’s Position
    public Object[] askForIdAndPosition(Team team) {
        System.out.print("Please input player ID: ");
        String playerID = sc.nextLine();
        if (isNull(playerID))
            return askForIdAndPosition(team);

        Enumeration<Player> players = team.getPlayers();
        Player selectedPlayer = null;
        while (players.hasMoreElements()) {
            Player player = players.nextElement();
            if (player.getPlayerID().equals(playerID))
                selectedPlayer = player;
        }
        if (selectedPlayer == null) {
            errMsg("Player ID " + playerID + " does not exist!");
            return askForIdAndPosition(team);
        }

        while (true) {
            int position = askForPlayerPosition(team);
            if (selectedPlayer.getPosition() == (int) position)
                errMsg("Player " + selectedPlayer.getName() + " is already in " + team.getPositionsString()[position - 1] + " position!");
            else
                return new Object[] { selectedPlayer, position };
        }
    }

    public void errMsg(String msg) {
        System.out.println(inputErrorString + msg);
    }

    public void sysMsg(String msg) {
        System.out.println(systemMessageString + msg);
    }

    // mod = 1: check team if null || mod = 2: check team and _player if null
    public boolean teamAndPlayerIsNull(Team currentTeam, String mod) {
        if (currentTeam == null) {
            errMsg("No team is selected.");
            return true;
        }
        if (!currentTeam.getPlayers().hasMoreElements() && mod.equals("checkBoth")) {
            errMsg("No player in the team.");
            return true;
        }
        return false;
    }

    private boolean isNull(String inputString) {
        if (inputString.equals("")) {
            errMsg("Input cannot be empty!");
            return true;
        }
        return false;
    }
}
