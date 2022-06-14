package client;

import common.ServerRequest;
import common.route.Route;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Scanner;

public class Client {

    private static Scanner scanner = new Scanner(System.in);
    private static Scanner loginScanner = new Scanner(System.in);
    private static DatagramSocket ds;
    private static String serverName;
    private static int serverPort;
    private static Object[] inputArr;
    private static String command;
    private static Object argument;
    private static String login;
    private static String password;
    private static InetAddress serverAddress;

    public static void main(String[] args) {
        //Загрузка конфигурации
        try (InputStream input = new FileInputStream("config/client.properties")) {
            Properties serverProp = new Properties();
            serverProp.load(input);
            serverPort = Integer.parseInt(serverProp.getProperty("server.port", "1234"));
            serverName = serverProp.getProperty("server.IPv4", "127.0.0.1");
            System.out.println("connection with server " + serverName + ':' + serverPort);
        } catch (IOException io) {
            io.printStackTrace();
        }

        try {
            serverAddress = InetAddress.getByName(serverName);
            //Создание сокета для отправки команд
            ds = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("connection failed, check serverName");
            throw new RuntimeException(e);
        } catch (UnknownHostException ex) {
            throw new RuntimeException("connection failed, check server name and server port");
        }
        //Запуск общения с сервером
        start();
        go();
    }


    private static void processInput() {
        argument = null;
        inputArr = scanner.nextLine().split(" ");
        command = (String) inputArr[0];
        if (inputArr.length > 1) {
            argument = inputArr[1];
            if (command.equals("update")) {
                Route routeForUpdating = new Route();
                try {
                    routeForUpdating.setId(Integer.parseInt((String) inputArr[1]));
                } catch (NumberFormatException e) {
                    System.out.println("invalid id");
                }
                argument = routeForUpdating;
            }
        }
        if (command.equals("add")) {
            argument = new Route();
        } else if (command.equals("login")) {
            login();
        } else if (command.equals("register")) {
            register();
        }
    }

    private static void sendRequest() {

        byte[] requestArr;

        //Создание потока вывода
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                baos.flush();
                oos.flush();
                //Запись команды, аргумента и данных о пользователе в этот поток
                ServerRequest request = new ServerRequest(command, argument, login, password);
                oos.writeObject(request);
                oos.flush();
                requestArr = baos.toByteArray();

                //Сначала отправляется размер запроса
                sendRequestSize(requestArr);
                //Теперь отправляется сам запрос
                DatagramPacket dp = new DatagramPacket(requestArr, requestArr.length, serverAddress, serverPort);
                ds.send(dp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getResult() {

        try {
            //Считывание размера ответа от сервера
            int resultSize = getResultSize();

            //Прием датаграммы с результатом
            byte[] resultArr = new byte[resultSize];
            DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, serverAddress, serverPort);
            ds.receive(resultPacket);
            //Распаковка полученного ответа от сервера из датаграммы
            String resultString = new String(resultPacket.getData());
            System.out.println(resultString);

            if (resultString.equals("exit")) {
                ds.close();
                System.exit(0);
            }

            if (resultString.equals("bye-bye")) {
                login = null;
                password = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getResultSize() {
        try {
            byte[] resultSizeArr = new byte[4];
            DatagramPacket resultSizePacket = new DatagramPacket(resultSizeArr, resultSizeArr.length, serverAddress, serverPort);
            ds.receive(resultSizePacket);
            return ByteBuffer.wrap(resultSizeArr).getInt();
        } catch (IOException e) {
            System.out.println("failed to get result size");
        }
        return -1;
    }

    private static void sendRequestSize(byte[] requestArr) {
        try {
            byte[] requestSize = ByteBuffer.allocate(4).putInt(requestArr.length).array();
            DatagramPacket requestSizePacket = new DatagramPacket(requestSize, requestSize.length, serverAddress, serverPort);
            ds.send(requestSizePacket);
        } catch (IOException e) {
            System.out.println("failed to send result size");
        }
    }

    private static void login() {
        System.out.println("login: ");
        login = loginScanner.next();
        System.out.println("password: ");
        password = loginScanner.next();
    }

    private static void register() {
        System.out.println("login: ");
        login = loginScanner.next();
        System.out.println("password: ");
        password = loginScanner.next();
        System.out.println("repeat password: ");
        String passwordRepeated = loginScanner.next();
        while (!password.equals(passwordRepeated)) {
            System.out.println("passwords don't match");
            passwordRepeated = loginScanner.next();
        }
    }

    private static void go() {
        while (true) {
            processInput();
            sendRequest();
            getResult();
        }
    }

    private static void start() {
        System.out.println("login or register to start");
        command = loginScanner.next();
        switch (command) {
            case "login" -> {
                login();
                sendRequest();
                getResult();
            }
            case "register" -> {
                register();
                sendRequest();
                getResult();
            }
            default -> start();
        }
    }
}