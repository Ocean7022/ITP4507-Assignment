public class CommandInvalid implements Command {
    private InputHandler in;

    public CommandInvalid(InputHandler in) {
        this.in = in;
    }

    public void execute() {
        in.sysMsg("Invalid command.");
    }

    public void undo() {
    }

    public void redo() {
    }
}
