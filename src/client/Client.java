package client;

import mid.ServerRequest;
import mid.route.Route;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client {

    private static Scanner scanner = new Scanner(System.in);
    private static Scanner loginScanner = new Scanner(System.in);
    private static DatagramSocket ds;
    private static InetAddress serverAddress;
    private static String serverName = "localhost";
    private static int serverPort = 1234;
    private static int clientPort = 0;
    private static Object[] inputArr;
    private static String command;
    private static Object argument;
    private static String login;
    private static String password;


    public static void main(String[] args) throws Exception {
        serverAddress = InetAddress.getByName(serverName);
        //Создание сокета для отправки команд
        ds = new DatagramSocket();
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
        byte[] requestSize;

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

                //Сначала отправляется размер буфера с запросом
                requestSize = ByteBuffer.allocate(4).putInt(requestArr.length).array();
                DatagramPacket requestSizePacket = new DatagramPacket(requestSize, requestSize.length, serverAddress, serverPort);
                ds.send(requestSizePacket);
                //Теперь отправляется сам запрос
                DatagramPacket dp = new DatagramPacket(requestArr, requestArr.length, serverAddress, serverPort);
                ds.send(dp);

                System.out.println(request + " sent to server at: " + serverAddress);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getResult() {

        try {
            //Создание датаграммы для приема ответа от сервера
            byte[] resultArr = new byte[4096];
            DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, InetAddress.getByName("localhost"), clientPort);
            ds.receive(resultPacket);
            //Распаковка полученного ответа от сервера из датаграммы
            String resultString = new String(resultPacket.getData(), 0, resultPacket.getLength());
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
                System.out.println("command is " + command);
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