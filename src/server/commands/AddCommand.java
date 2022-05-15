package server.commands;

import route.Route;
import server.data.Data;

import java.util.InputMismatchException;

/**
 * Команда для добавления нового элемента в коллекцию через комндную строку
 */

public class AddCommand extends Command {
    public AddCommand() {
        this.name = "add";
        this.desc = "adds an element to the collection of routes";
    }

    public String execute() {
        try {
            Route newRoute = new Route();
            Data.routes.add(newRoute);
            //new SortByDistanceCommand().execute();
        } catch (InputMismatchException e) {
            this.execute();
        }
        return "new element added to the collection";
    }
}
