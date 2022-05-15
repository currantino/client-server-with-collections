package server.commands;

import server.data.Data;

public class HeadCommand extends Command {

    public HeadCommand() {
        super("head", "print out the first element in the collection");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            new SortByDistanceCommand().execute();
            return Data.getRoutes().getFirst().toString();
        }
        return "collection is empty";
    }
}
