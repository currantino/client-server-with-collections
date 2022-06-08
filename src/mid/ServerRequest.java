package mid;

import java.io.Serializable;
import java.net.SocketAddress;

public class ServerRequest implements Serializable {
    private String command;
    private Object argument;
    private String login;
    private String password;
    private SocketAddress senderAddress;

    public ServerRequest(String command, Object argument, String login, String password) {
        this.command = command;
        this.argument = argument;
        this.login = login;
        this.password = password;
    }

    public SocketAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(SocketAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "command='" + command + '\'' +
                ", argument=" + argument +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", senderAddress=" + senderAddress +
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
