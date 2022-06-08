package server.commands;

import server.commands.types.Readable;
import server.data.Data;

public class HeadCommand extends Command implements Readable {
    public HeadCommand() {
        super("head", "print out the first element in the collection");
    }

    @Override
    public String execute() {
        String result = "";
        if (!Data.getRoutes().isEmpty()) {
            result = Data.getRoutes().get(0).show();
        } else result = "collection is empty";
        return result;
    }
}
