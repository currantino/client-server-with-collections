package server.commands;

/**
 * Абстрактный класс-родитель всех классов команд
 */

public abstract class Command {
    /**
     * Название команды
     */
    protected String name;
    /**
     * Описание команды
     */
    protected String desc;
    public Command(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
    protected String result = "";

    /**
     * Строка, которая вернется клиенту после исполнения команды
     */

    String getDesc() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Абстрактный метод для исполнения команды
     */
    public abstract String execute();
}