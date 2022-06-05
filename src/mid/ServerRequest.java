package mid;

import java.io.Serializable;

public class ServerRequest implements Serializable {
    private String command;
    private Object argument;
    private String login;
    private String password;

    public ServerRequest(String command, Object argument, String login, String password) {
        this.command = command;
        this.argument = argument;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "command='" + command + '\'' +
                ", argument=" + argument +
                ", mail='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public Object getArgument() {
        return argument;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
