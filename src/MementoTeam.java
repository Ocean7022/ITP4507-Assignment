public class MementoTeam implements Memento {
    private Team team;
    private String name;

    public MementoTeam(Team team) {
        this.team = team;
        this.name = team.getName();
    }

    public void restore() {
        team.setName(name);
    }
}
