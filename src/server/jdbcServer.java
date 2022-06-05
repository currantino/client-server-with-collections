package server;

import server.commands.Command;
import server.commands.SqlCommand;
import server.data.Data;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jdbcServer {

    public static String command;
    public static Object argument;
    public static Connection conn;
    private static String serverName = "localhost";
    private static int serverPort = 1234;
    private static DatagramChannel channel;
    private static SocketAddress clientAddress;
    private static SocketAddress lastClientAddress;
    private static InetSocketAddress serverAdd = new InetSocketAddress(serverName, serverPort);
    private static Object[] requestArr;
    private static String result;
    private static String dbURL;
    private static Properties info;
    private static final Logger LOGGER = Logger.getLogger("jdbcSever");

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        dbURL = "jdbc:postgresql://localhost:5432/studs";
        info = new Properties();
        info.load(new FileInputStream("/Users/boi/Desktop/client-server-with-collections/config/db.cfg"));



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

        requestArr = (Object[]) ois.readObject();
        LOGGER.info(Arrays.toString(requestArr) + " received from client at: " + clientAddress);

        command = (String) requestArr[0];
        System.out.println(command);
        if (requestArr.length > 1) {
            argument = requestArr[1];
        }

    }

    private static void sendResult() throws IOException {
        if (Data.getCommands().containsKey(command)) {
            processCommand(Data.getCommands().get(command));
        } else {
            result = "unknown command, use 'help' ";
            LOGGER.info("unknown command received");
        }
        ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
        channel.send(resultBuffer, clientAddress);
        LOGGER.fine("result sent to client at " + clientAddress);
        lastClientAddress = clientAddress;
    }

    private static void processCommand(Command command) {
        if (command instanceof SqlCommand) {
            try {
                conn = DriverManager.getConnection(dbURL, info);
                result = command.execute();
                conn.close();
            } catch (SQLException e) {
                LOGGER.warning("connection to db failed");
            }
        } else {
            result = command.execute();
        }
    }
}