import java.util.Enumeration;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class AssignmentTest {
    private static Scanner sc = new Scanner(System.in);
    private static Vector<Team> teams = new Vector<Team>();
    private static Stack<Command> undoList = new Stack<Command>();
    private static Stack<Command> redoList = new Stack<Command>();
    private static Stack<String> undoTypeList = new Stack<String>();
    private static Stack<String> redoTypeList = new Stack<String>();
    private static Caretaker caretaker = new Caretaker();
    private static Command command;
    private static Team currentT;

    public static void main(String[] args) {
        while (true) {
            System.out.print("\nSport Teams Management System (STMS)\n");
            System.out.print(
                    "c = create team, g = set current team, a = add player, m = modify player’s position, d = delete player, s = show team, p = display all teams, t = change team’s name, u = undo, r = redo, l = list undo/redo, x = exit system\n");
            if (currentT != null)
                System.out.print("Current team is " + currentT.getTeamID() + " " + currentT.getName() + "\n");
            System.out.print("Please enter command [ c | g | a | m | d | s | p | t | u | r | l | x ] :");
            String userOption = sc.nextLine();
            Command command;

            switch (userOption) {
                case "c":
                    command = new CommandCreateTeam(sc, teams);
                    undoList.push(command);
                    command.execute();
                    redoList.clear();
                    redoTypeList.clear();
                    undoTypeList.push("command");
                    currentT = teams.get(teams.size() - 1);
                    System.out.println("Current team is change to " + currentT.getTeamID() + " " + currentT.getName());
                    break;
                case "g":
                    System.out.print("Please enter team ID: ");
                    String teamID = sc.nextLine();
                    for (int i = 0; i < teams.size(); i++) {
                        if (teams.get(i).getTeamID().equals(teamID)) {
                            currentT = teams.get(i);
                            break;
                        }
                    }
                    break;
                case "a":
                    command = new CommandAddPlayer(sc, currentT);
                    command.execute();
                    undoList.push(command);
                    undoTypeList.push("command");
                    break;
                case "m":
                    System.out.print("Please enter player ID: ");
                    String playerID = sc.nextLine();
                    if (currentT.getClass().getSimpleName().equals("VolleyballTeam")) {
                        System.out.print("Position (1 = Attacker | 2 = Defender): ");
                    } else {
                        System.out.print("Position (1 = Goalkeeper | 2 = Defender | 3 = Midfielder | 4 = Forward):");
                    }
                    int position = sc.nextInt();

                    Enumeration<Player> players = currentT.getPlayers();
                    Player playerSlelct = null;
                    while (players.hasMoreElements()) {
                        Player player = players.nextElement();
                        if (player.getPlayerID().equals(playerID)) {
                            playerSlelct = player;
                            break;
                        }
                    }

                    caretaker.savePlayer(playerSlelct, position);
                    playerSlelct.setPosition(position);
                    String positionString = "";
                    if (currentT.getClass().getSimpleName().equals("VolleyballTeam")) {
                        if (position == 1) {
                            positionString = "Attacker";
                        } else {
                            positionString = "Defender";
                        }
                    } else {
                        if (position == 1) {
                            positionString = "Goalkeeper";
                        } else if (position == 2) {
                            positionString = "Defender";
                        } else if (position == 3) {
                            positionString = "Midfielder";
                        } else {
                            positionString = "Forward";
                        }
                    }
                    System.out.println("Modify player’s position, " + playerID + ", " + positionString);
                    undoTypeList.push("memento");
                    break;
                case "d":
                    command = new CommandDelete(sc, currentT);
                    command.execute();
                    undoList.push(command);
                    undoTypeList.push("command");
                    break;
                case "s":
                    Enumeration<Player> players2 = currentT.getPlayers();
                    System.out.println(currentT.getClass().getSimpleName() + " Team " + currentT.getName() + "(" + currentT.getTeamID() + ")");

                    if (currentT.getClass().getSimpleName().equals("VolleyballTeam")) {
                        System.out.println("Attacker:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 1) {
                                System.out.println(player);
                            }
                        }
                        System.out.println("Defender:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 2) {
                                System.out.println(player);
                            }
                        }
                    } else {
                        System.out.println("Goalkeeper:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 1) {
                                System.out.println(player);
                            }
                        }
                        System.out.println("Defender:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 2) {
                                System.out.println(player);
                            }
                        }
                        System.out.println("Midfielder:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 3) {
                                System.out.println(player);
                            }
                        }
                        System.out.println("Forward:");
                        players2 = currentT.getPlayers();
                        while (players2.hasMoreElements()) {
                            Player player = players2.nextElement();
                            if (player.getPosition() == 4) {
                                System.out.println(player);
                            }
                        }
                    }
                    break;
                case "p":
                    command = new CommandDisplayAll(teams);
                    command.execute();
                    break;
                case "t":
                    System.out.println("Please input new name of the current team: ");
                    String newName = sc.nextLine();
                    caretaker.saveTeam(currentT, newName);
                    currentT.setName(newName);
                    System.out.println("Team’s name is updated.");
                    undoTypeList.push("memento");
                    break;
                case "u":
                    if (undoTypeList.isEmpty()) {
                        System.out.println("Nothing to undo.");
                        break;
                    }
                    String uT = undoTypeList.pop();
                    if (uT.equals("command")) {
                        command = undoList.pop();
                        command.undo();
                        redoList.push(command);
                        redoTypeList.push(uT);
                        System.out.println("Command (" + command + ") is undone.");
                    } else {
                        caretaker.undo();
                        redoTypeList.push(uT);
                    }
                    break;
                case "r":
                    if (redoTypeList.isEmpty()) {
                        System.out.println("Nothing to redo.");
                    }
                    String rT = redoTypeList.pop();
                    if (rT.equals("command")) {
                        command = redoList.pop();
                        command.redo();
                        undoList.push(command);
                        undoTypeList.push(rT);
                        System.out.println("Command (" + command + ") is redone.");
                    } else {
                        caretaker.redo();
                        undoTypeList.push(rT);
                    }
                    break;
                case "l":
                    command = new CommandListUndoRedo(undoList, redoList, undoTypeList, redoTypeList, caretaker);
                    command.execute();
                    break;
                case "x":
                    System.out.println("Exit system!");
                    System.exit(0);
            }
        }
    }
}
