package server;

import mid.ServerRequest;
import server.commands.Command;
import server.commands.NotCheckable;
import server.data.Data;
import server.database.RoutePostgresSqlDatabase;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jdbcServer {

    private static final Logger LOGGER = Logger.getLogger("jdbcSever");
    public static String command;
    public static Object argument;
    public static Connection conn;
    public static String login;
    public static String password;
    public static RoutePostgresSqlDatabase pdb;
    private static String serverName = "localhost";
    private static int serverPort = 1234;
    private static DatagramChannel channel;
    private static SocketAddress clientAddress;
    private static SocketAddress lastClientAddress;
    private static InetSocketAddress serverAdd = new InetSocketAddress(serverName, serverPort);
    private static ServerRequest request;
    private static String result;
    private static String dbURL = "jdbc:postgresql://localhost:5432/studs";
    private static String propertiesPath = "/Users/boi/Desktop/client-server-with-collections/config/db.cfg";
    private static Properties info = new Properties();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        info.load(new FileInputStream(propertiesPath));
        pdb = new RoutePostgresSqlDatabase(dbURL, info);
        Data.setRoutes(pdb.getElements());
        Handler handlerObj = new ConsoleHandler();
        handlerObj.setLevel(Level.ALL);
        LOGGER.addHandler(handlerObj);
        LOGGER.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);
        Data.setCommands();

        //Открытие канала, который слушает на заданном адресе serverAdd
        channel = DatagramChannel.open();
        channel.bind(serverAdd);
        LOGGER.fine("channel server started at: " + serverAdd);

        while (true) {
            getRequest();
            sendResult();
        }
    }

    private static void getRequest() throws IOException, ClassNotFoundException {

        //Создание байтбуффера для приема запроса от клиента
        ByteBuffer requestBuffer = ByteBuffer.allocate(4096);

        //Получение датаграммы в байтбуффер и сохраняем адрес клиента в remoteAdd
        clientAddress = channel.receive(requestBuffer);
        byte[] arr = requestBuffer.array();

        //Создаем поток ввода для считывания запроса
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);

        request = (ServerRequest) ois.readObject();
        readRequestData(request);

        LOGGER.info(request + " received from client " + login);
    }

    private static void sendResult() throws IOException {
        System.out.println(login);
        System.out.println(password);
        if (Data.getCommands().containsKey(command)) {
            Command commandToExecute = Data.getCommands().get(command);
            if (commandToExecute instanceof NotCheckable) {
                result = processCommand(commandToExecute);
            } else if (pdb.checkLogin(login)) {
                if (pdb.checkPassword(login, password)) {
                    result = processCommand(Data.getCommands().get(command));
                } else {
                    result = "wrong password";
                    LOGGER.warning("wrong password");
                }
            } else {
                result = "unknown user: use 'register'";
                LOGGER.warning("unknown user");
            }
        } else {
            result = "unknown command, use 'help'";
            LOGGER.info("unknown command received");
        }
        ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
        channel.send(resultBuffer, clientAddress);
        LOGGER.fine("result sent to client at " + clientAddress);
    }

    private static String processCommand(Command command) {
        return command.execute();
    }

    private static void readRequestData(ServerRequest request) {
        command = request.getCommand();
        argument = request.getArgument();
        login = request.getLogin();
        password = request.getPassword();
    }
}