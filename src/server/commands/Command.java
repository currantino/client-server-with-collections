package server.commands;

import mid.ServerRequest;

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
    protected String result = "";
    protected String command;
    protected Object argument;
    protected String login;
    protected String password;

    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

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
    public String execute() {
        return result;
    }

    public void unpackRequest(ServerRequest request) {
        this.command = request.getCommand();
        this.argument = request.getArgument();
        this.login = request.getLogin();
        this.password = request.getPassword();
    }
}