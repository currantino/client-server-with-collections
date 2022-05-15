package server;

import server.data.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Logger;

public class Server {

    private static String serverName = "localhost";
    private static int serverPort = 1234;
    private static DatagramChannel channel;
    private static SocketAddress clientAddress;
    private static SocketAddress lastClientAddress;
    private static InetSocketAddress serverAdd = new InetSocketAddress(serverName, serverPort);
    private static Object[] requestArr;
    public static String command;
    public static Object argument;
    private static String result;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logger logger = Logger.getLogger("server logger");
        Data.setCommands();

        //Открытие канала, который слушает на заданном адресе serverAdd
        channel = DatagramChannel.open();
        channel.bind(serverAdd);
        logger.fine("channel server started at: " + serverAdd);

        loadAutoSave();
        sendResult();

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
        if (requestArr.length > 1) {
            argument = requestArr[1];
        }

    }

    private static void sendResult() throws IOException {
        if (Data.getCommands().containsKey(command)) {
            result = Data.getCommands().get(command).execute();
        } else {
            result = "unknown command, use 'help' ";
        }
        ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
        channel.send(resultBuffer, clientAddress);
        System.out.println("'" + result + "'" + " sent to client at: " + clientAddress);
        lastClientAddress = clientAddress;
    }

    private static void loadAutoSave() throws IOException, ClassNotFoundException {
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

        if ((command = (String) requestArr[0]).equals("load")) {
            Data.readAutoSave();
        }
    }
}

