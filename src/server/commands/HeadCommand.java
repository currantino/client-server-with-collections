package server.commands;

import server.Readable;
import server.data.Data;

public class HeadCommand extends Command implements Readable {
    public HeadCommand() {
        super("head", "print out the first element in the collection");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            return Data.getRoutes().get(0).show();
        }
        return "collection is empty";
    }
}
