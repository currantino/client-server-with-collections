package Client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatagramSocket ds;
        InetAddress serverAddress;
        byte[] buffer;

        while (true) {
            //Считываем пользовательский ввод
            String[] inputArr = scanner.nextLine().split(" ");
            String command = inputArr[0];

            if (inputArr.length == 2) {
                String argument = inputArr[2];
            }

            try {
                //Запускаем сервер на локалхосте
                serverAddress = InetAddress.getByName("localhost");
                ds = new DatagramSocket();

                //Создаем поток вывода
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.flush();
                //Записываем в этот поток команду пользователя
                oos.writeObject(command);
                oos.flush();
                oos.close();
                buffer = baos.toByteArray();
                //Упаковываем команду в пакет и отправлякм на сервер в порт 1234
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverAddress, 1234);
                ds.send(dp);
                System.out.println(command + " sent to server at: " + serverAddress);
                //Создаем пакет для приема ответа от сервера
                byte[] resultArr = new byte[1024];
                DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, InetAddress.getByName("localhost"), 0);
                ds.receive(resultPacket);
                //Достаем из полученного пакета ответ от сервера
                String resultString = new String(resultPacket.getData(), 0, resultPacket.getLength());
                System.out.println("result from server " + serverAddress + ":\n" + resultString);
                if (resultString.equals("exit")) {
                    ds.close();
                    System.exit(0);
                }
                ds.close();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
//Route route = new Route("home to itmo", 1.0F, 1, 1F, "home", 1, 1, 1, "itmo", 3, 3, 3);
//System.out.println(route);
//        while (true) {
//            try {
//                serverAddress = InetAddress.getByName("localhost");
//                ds = new DatagramSocket();
//                // String messageToSend = scanner.nextLine();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(baos);
//                oos.flush();
//                oos.writeObject(route);
//                oos.flush();
//                oos.close();
//                buffer = baos.toByteArray();
//                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//                ObjectInputStream ois = new ObjectInputStream(bais);
//                Route testy = (Route) ois.readObject();
//                //System.out.println(testy);
//                DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverAddress, 1234);
//                ds.send(dp);
//                System.out.println(route + " sent to server at: " + serverAddress);
//                System.out.println(buffer.length);
//                break;
////                System.out.println("sent to server at " + serverAddress + ": " + messageToSend);
////                byte[] resultArr = new byte[1024];
////                DatagramPacket resultPacket = new DatagramPacket(resultArr, resultArr.length, InetAddress.getByName("localhost"), 0);
////                ds.receive(resultPacket);
////                String resultString = new String(resultPacket.getData(), 0, resultPacket.getLength());
////                System.out.println("result from server " + serverAddress + ": " + resultString);
////                if (resultString.equals("exit")) {
//                    ds.close();
//                    System.exit(0);
//                }
//                ds.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }


