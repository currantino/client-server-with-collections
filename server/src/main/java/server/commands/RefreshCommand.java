package server.commands;

import org.json.JSONArray;
import server.commands.types.Readable;
import server.data.Data;

public class RefreshCommand extends Command implements Readable {
    public RefreshCommand() {
        super("refresh", "shows all routes");
    }

    public String execute() {
        return new JSONArray(Data.getRoutes()).toString();
    }
}