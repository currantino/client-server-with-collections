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
    /**
     * Строка, которая вернется клиенту после исполнения команды
     */
    protected String result = "";
    String command;
    String login;
    String password;

    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }


    String getDesc() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Метод для исполнения команды
     */
    public String execute() {
        return result;
    }

    public void unpackRequest(ServerRequest request) {
        command = request.getCommand();
        login = request.getLogin();
        password = request.getPassword();
    }
}