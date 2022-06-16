package server.commands;

import org.json.JSONArray;
import server.commands.types.Readable;
import server.data.Data;

public class ShowCommand extends Command implements Readable {
    public ShowCommand() {
        super("show", "shows all routes");
    }

    public String execute() {
        return new JSONArray(Data.getRoutes()).toString();
    }
}