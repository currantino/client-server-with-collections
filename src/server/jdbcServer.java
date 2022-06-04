package server;

import server.commands.SQLCommand;
import server.data.Data;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

public class jdbcServer {

    public static String command;
    public static Object argument;
    public static PreparedStatement statement;
    private static String serverName = "localhost";
    private static int serverPort = 1234;
    private static DatagramChannel channel;
    private static SocketAddress clientAddress;
    private static SocketAddress lastClientAddress;
    private static InetSocketAddress serverAdd = new InetSocketAddress(serverName, serverPort);
    private static Object[] requestArr;
    private static String result;
    private static String dbURL;
    public static Connection conn;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        dbURL = "jdbc:postgresql://localhost:5432/studs";


        Logger logger = Logger.getLogger("server logger");
        Data.setCommands();

        //Открытие канала, который слушает на заданном адресе serverAdd
        channel = DatagramChannel.open();
        channel.bind(serverAdd);
        logger.fine("channel server started at: " + serverAdd);

        System.out.println("sql connection established");

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
        System.out.println(Arrays.toString(requestArr) + " received from client at: " + clientAddress);

        command = (String) requestArr[0];
        System.out.println(command);
        if (requestArr.length > 1) {
            argument = requestArr[1];
        }

    }

    private static void sendResult() throws IOException, SQLException {
        if (Data.getCommands().containsKey(command)) {
//            if (Data.getCommands().get(command) instanceof SQLCommand) {
                Properties info = new Properties();
                info.load(new FileInputStream("/Users/boi/Desktop/client-server-with-collections/config/db.cfg"));
                conn = DriverManager.getConnection(dbURL, info);
                String sqlRequest = Data.getCommands().get(command).execute();
                Statement s = conn.createStatement();
                s.executeUpdate(sqlRequest);
                conn.close();
//            }
            result = Data.getCommands().get(command).execute();
        } else {
            result = "unknown command, use 'help' ";
        }
        ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
        channel.send(resultBuffer, clientAddress);
        System.out.println("'" + result + "'" + " sent to client at: " + clientAddress);
        lastClientAddress = clientAddress;
    }
}