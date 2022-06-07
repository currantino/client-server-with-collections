package server;

import mid.ServerRequest;
import server.commands.types.Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class RequestReceiver implements Runnable {
    private SocketAddress address;
    private ByteBuffer buffer;

    public RequestReceiver(SocketAddress address, ByteBuffer buffer) {
        this.address = address;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            //Создание байтбуффера для приема запроса от клиента
            byte[] arr = buffer.array();

            //Создаем поток ввода для считывания запроса
            ByteArrayInputStream bais = new ByteArrayInputStream(arr);
            ObjectInputStream ois = new ObjectInputStream(bais);

            ServerRequest request = (ServerRequest) ois.readObject();
            request.setSenderAddress(address);

            NetworkManager.pool.submit(new RequestProcessor(request));
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
