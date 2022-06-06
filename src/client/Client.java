package client;

import mid.ServerRequest;
import mid.route.Route;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private static Scanner scanner = new Scanner(System.in);
    private static Scanner loginScanner = new Scanner(System.in);
    private static DatagramSocket ds;
    private static InetAddress serverAddress;
    private static String serverName = "localhost";
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

        login();
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
                } catch (NumberFormatException e){
                    System.out.println("invalid id");
                }
                argument = routeForUpdating;
            }
        }
        if (command.equals("add")) {
            argument = new Route();
        }
        if (command.equals("login")) {
            login();
        }
        if (command.equals("register")) {
            register();
        }
    }

    private static void sendRequest() throws IOException {

        byte[] requestArr;

        //Создание потока вывода
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        baos.flush();
        oos.flush();

        //Запись команды, аргумента и данных о пользователе в этот поток
        ServerRequest request = new ServerRequest(command, argument, login, password);
        oos.writeObject(request);
        oos.flush();
        oos.close();
        requestArr = baos.toByteArray();

        //Упаковка команды в датаграмму
        DatagramPacket dp = new DatagramPacket(requestArr, requestArr.length, serverAddress, 1234);
        //Отправка на сервер в порт 1234
        ds.send(dp);

        System.out.println(request + " sent to server at: " + serverAddress);

    }

    private static void getResult() throws IOException {

        //Создание датаграммы для приема ответа от сервера
        byte[] resultArr = new byte[4096];
        DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, InetAddress.getByName("localhost"), 0);
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
    }

    private static void login() {
        System.out.println("login: ");
        login = loginScanner.next();
        System.out.println("password: ");
        password = loginScanner.next();
        go();
    }

    private static boolean register() {
        System.out.println("login: ");
        login = loginScanner.next();
        System.out.println("password: ");
        password = loginScanner.next();
        System.out.println("repeat password: ");
        String passwordRepeated = loginScanner.next();
        while (!password.equals(passwordRepeated)) {
            System.out.println("passwords don't match");
            passwordRepeated = loginScanner.next();
            if (passwordRepeated.equals("exit"))
                return false;
        }
        return true;
    }

    private static void go() {
        try {
            while (true) {
                processInput();
                sendRequest();
                getResult();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}