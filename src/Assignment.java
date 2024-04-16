import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Assignment {
    private static Scanner sc = new Scanner(System.in);
    private static final InputHandler in = new InputHandler(sc);
    private static final String systemTitle = "\n======================= Sport Teams Management System (STMS) =======================";
    private static final String commandList = "c = create team, g = set current team, a = add player, m = modify player’s position,\n" +
            "d = delete player, s = show team, p = display all teams, t = change team’s name,\n" +
            "u = undo, r = redo, l = list undo/redo, x = exit system";
    private static final String optionTitle = "Please enter command [ c | g | a | m | d | s | p | t | u | r | l | x ] : ";
    private static Vector<Team> _teams = new Vector<Team>();
    private static Stack<Team> currentTeamUndoList = new Stack<Team>();
    private static Stack<Team> currentTeamRedoList = new Stack<Team>();
    private static Stack<Command> undoList = new Stack<Command>();
    private static Stack<Command> redoList = new Stack<Command>();
    private static Caretaker caretaker = new Caretaker();
    private static CurrentTeamKeeper currentTeamKeeper = new CurrentTeamKeeper();
    private static Team currentTeam;

    public static void main(String[] args) {
        while (true) {
            System.out.println(systemTitle);
            System.out.println(commandList);
            currentTeam = currentTeamKeeper.getCurrentTeam();
            System.out.print(currentTeam == null ? "" : "The current team is [" + currentTeam + "]\n");
            System.out.print(optionTitle);
            String userOption = sc.nextLine();
            Command command = getCommand(userOption);

            switch (userOption) {
                case "c", "a", "m", "d", "t":
                    // c - no check
                    // a, d, t - check team only
                    // m - check both 
                    if (!userOption.equals("c") && in.teamAndPlayerIsNull(currentTeam, userOption.equals("m") ? "checkBoth" : "checkTeamOnly"))
                        break;

                    currentTeamUndoList.push(currentTeam);
                    undoList.push(command);
                    command.execute();
                    redoList.clear();
                    currentTeamRedoList.clear();
                    caretaker.clearRedoList();
                    break;
                default:
                    command.execute();
            }
            if (_teams.isEmpty())
                currentTeamKeeper.setCurrentTeam(null);
        }
    }

    private static Command getCommand(String userOption) {
        switch (userOption) {
            case "c": // create team
                return new CommandCreateTeam(in, _teams, currentTeamKeeper);
            case "g" : // set current team
                return new CommandSetCurrentTeam(in, _teams, currentTeamKeeper);
            case "a": // add player
                return new CommandAddPlayer(in, currentTeam);
            case "m": // modify player’s position
                return new CommandModifyPlayer(in, caretaker, currentTeam);
            case "d": // delete player
                return new CommandDeletePlayer(in, currentTeam);
            case "s": // show team  
                return new CommandShowTeam(in, currentTeam);
            case "p": // display all teams
                return new CommandDisplayAll(_teams);
            case "t": // change team’s name 
                return new CommandChangeTeam(in, caretaker, _teams, currentTeam);
            case "u": // undo
                return new CommandUndo(undoList, redoList, currentTeamKeeper, currentTeamUndoList, currentTeamRedoList);
            case "r": // redo
                return new CommandRedo(undoList, redoList, currentTeamKeeper, currentTeamUndoList, currentTeamRedoList);
            case "l": // list undo/redo
                return new CommandListUndoRedo(undoList, redoList);
            case "x": // exit system
                return new CommandExit(in);
            default: // invalid command
                return new CommandInvalid(in);
        }
    }
}
