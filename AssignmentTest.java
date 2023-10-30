import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class AssignmentTest {
    private static Scanner sc = new Scanner(System.in);
    private static Vector<Team> teams = new Vector<Team>();
    private static Stack<Command> undoList = new Stack<Command>();
    private static Stack<Command> redoList = new Stack<Command>();
    private static Command command;
    private static Team currentT;

    public static void main(String[] args) {
        while (true) {
            System.out.print("Sport Teams Management System (STMS)\n");
            System.out.print(
                    "c = create team, g = set current team, a = add player, m = modify player’s position, d = delete player, s = show team, p = display all teams, t = change team’s name, u = undo, r = redo, l = list undo/redo, x = exit system\n");
            System.out.print("Please enter command [ c | g | a | m | d | s | p | t | u | r | l | x ] :");
            String userOption = sc.nextLine();
            Command command;

            switch (userOption) {
                case "c":
                    command = new CommandCreateTeam(sc, teams);
                    undoList.push(command);
                    command.execute();
                    currentT = teams.get(teams.size() - 1);
                    System.out.println("Current team is change to " + currentT.getTeamID() + ".");
                    break;
                case "g":

                    break;
                case "a":

                    break;
                case "m":

                    break;
                case "d":

                    break;
                case "s":

                    break;
                case "p":
                    command = new CommandDisplayAll(teams);
                    command.execute();
                    break;
                case "t":

                    break;
                case "u":
                    command = undoList.pop();
                    command.undo();
                    redoList.push(command);
                    System.out.println("Command (" + command + ") is undone.");
                    break;
                case "r":
                    command = redoList.pop();
                    command.redo();
                    undoList.push(command);
                    System.out.println("Command (" + command + ") is redone.");
                    break;
                case "l":
                    command = new CommandListUndoRedo(undoList, redoList);
                    command.execute();
                    break;
                case "x":
                    System.out.println("Exit system!");
                    System.exit(0);
            }
        }
    }
}
