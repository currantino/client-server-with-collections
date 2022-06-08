package server.commands;

import server.commands.types.NotCheckable;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command implements NotCheckable {

    public ExitCommand() {
        super("exit", "exit application");
    }

    public String execute() {
        return "exit";
    }
}