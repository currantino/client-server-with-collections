package server.commands;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "exits application");
    }

    public String execute() {
//        if (Data.saved == true) {
//            System.exit(1);
//        } else{
//            new TempSaveCommand().execute();
        return "exit";
//        }
    }
}