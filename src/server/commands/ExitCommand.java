package server.commands;

import server.data.Data;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "exits application");
    }

    public String execute() {
        Data.autoSave();
        return "exit";
    }
}