package server.commands;

/**
 * Команда для завершения работы приложения
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        this.name = "exit";
        this.desc = "exits program";
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