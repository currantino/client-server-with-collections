package server.commands;

import server.NotCheckable;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command implements NotCheckable {

    public ExitCommand() {
        super("exit", "exits application");
    }

    public String execute() {
        return "exit";
    }
}