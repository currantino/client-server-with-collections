package server.commands;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "exits application");
    }

    public String execute() {
        new AutoSaveCommand().execute();
        return "exit";
    }
}