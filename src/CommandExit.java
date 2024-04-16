public class CommandExit implements Command {
    private InputHandler in;

    public CommandExit(InputHandler in) {
        this.in = in;
    }

    public void execute() {
        in.sysMsg("Exit system!");
        System.exit(0);
    }

    public void undo() {
    }

    public void redo() {
    }
}
