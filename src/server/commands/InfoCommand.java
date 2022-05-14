package server.commands;

import server.data.Data;

/**
 * Команда для вывода основной информации о коллекуии
 */

public class InfoCommand extends Command {
    public InfoCommand() {
        this.name = "info";
        this.desc = "gives key info about the collection";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            result += "type: " + Data.getRoutes().getClass().getSimpleName() + '\n';
            new SortByDateTimeCommand().execute();
            result += "creation date: " + (Data.routes.getFirst()).getCreationDate() + '\n';
            new SortByDistanceCommand().execute();
            result += "collection's size: " + Data.getRoutes().size() + '\n';
            return result;
        }
        return "collection is empty";
    }
}
