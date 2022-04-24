package Server;

public class ClearCommand extends Command {
    public ClearCommand() {
        this.name = "clear";
        this.desc = "clear the collection";
    }

    public String execute() {
        Data.routes.clear();
        Data.saved = false;
        return "collection was cleared";
    }
}
