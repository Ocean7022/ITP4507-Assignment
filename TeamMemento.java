public class TeamMemento implements Memento {
    private Team team;
    private String oldName;
    private String newName;

    public TeamMemento(Team team, String newName) {
        this.team = team;
        this.oldName = team.getName();
        this.newName = newName;
    }

    public void undo() {
        team.setName(oldName);
    }

    public void redo() {
        team.setName(newName);
    }

    public String toString() {
        return "Change team's name, " + team.getTeamID() + ", " + newName;
    }
}
