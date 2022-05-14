package server.commands;

/**
 * Абстрактный класс-родитель всех классов команд
 */

public abstract class Command {
    String name;
    /**
     * Название команды
     */
    String desc;
    /**
     * Описание команды
     */
    String result = "";

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