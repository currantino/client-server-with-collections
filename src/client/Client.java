package client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static Scanner scanner = new Scanner(System.in);
    private static DatagramSocket ds;
    private static InetAddress serverAddress;
    private static String clientName =  "localhost";
    private static int clientPort = 4321;
    private static String[] inputArr;
    private static String command;
    private static String argument;



    public static void main(String[] args) throws Exception {
        serverAddress = InetAddress.getByName(clientName);
        //Создание сокета для отправки команд
        ds = new DatagramSocket(clientPort);

        while (true) {

            //Обработка пользовательского ввода
            inputArr = scanner.nextLine().split(" ");
            command = inputArr[0];
            if (inputArr.length == 2) {
                argument = inputArr[2];
            }
            sendRequest();
            getResult();
        }
    }

    private static void getResult() throws Exception {

        //Создание пакета для приема ответа от сервера
        byte[] resultArr = new byte[4096];
        DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, InetAddress.getByName("localhost"), 0);
        ds.receive(resultPacket);

        //Распаковка полученного ответа от сервера из датаграммы
        String resultString = new String(resultPacket.getData(), 0, resultPacket.getLength());
        System.out.println("result from server " + serverAddress + ":\n" + resultString);

        if (resultString.equals("exit")) {
            ds.close();
            System.exit(0);
        }
    }

    static void sendRequest() throws Exception {

        byte[] requestArr;




        //Создание потока вывода
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        baos.flush();
        oos.flush();

        //Запись команды и аргумента в этот поток
        oos.writeObject(inputArr);
        oos.flush();
        oos.close();
        requestArr = baos.toByteArray();

        //Упаковка команды в датаграмму
        DatagramPacket dp = new DatagramPacket(requestArr, requestArr.length, serverAddress, 1234);
        //Отправка на сервер в порт 1234
        ds.send(dp);
        System.out.println(Arrays.toString(inputArr) + " sent to server at: " + serverAddress);

    }
}