package server.commands;

import server.data.Data;

/**Команда для вывода основной информации о коллекуии*/

public class InfoCommand extends Command {
    public InfoCommand() {
        this.name = "info";
        this.desc = "gives key info about the collection";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            System.out.println("type: " + Data.getRoutes().getClass().getName());
            new SortByDateTimeCommand().execute();
            System.out.println("creation date: " + (Data.routes.getFirst()).getCreationDate());
            new SortByDistanceCommand().execute();
            System.out.println("collection's size: " + Data.getRoutes().size());
        } else {
            System.out.println("collection is empty");
        }
    }
}
