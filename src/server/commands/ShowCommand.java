package server.commands;

import mid.route.Route;
import server.Readable;
import server.data.Data;

public class ShowCommand extends Command implements Readable {
    public ShowCommand() {
        super("show", "shows all routes");
    }

    public String execute() {
        result = "";
        if (!Data.getRoutes().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(result);
            for (Route route : Data.getRoutes()) {
                stringBuilder.append(route.show()).append('\n');
            }
            result = stringBuilder.toString();
        } else {
            result = "collection is empty";
        }
        return result;
    }
}