package server.commands;

import server.data.Data;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "clear the collection");
    }

    public String execute() {
        Data.getRoutes().clear();
        Data.saved = false;
        return "collection was cleared";
    }
}
