package server;

import mid.ServerRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import static server.NetworkManager.LOGGER;
import static server.NetworkManager.channel;

public class RequestReceiver implements Runnable {
    SocketAddress clientAddress;
    private ByteBuffer requestBuffer;
    private ByteBuffer sizeBuffer;

    public RequestReceiver(ByteBuffer sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
    }

    @Override
    public void run() {
        LOGGER.info(this.getClass().getSimpleName() + " thread started");
        sizeBuffer.flip();
        int size = sizeBuffer.getInt();
        requestBuffer = ByteBuffer.allocate(size);
        try {
            synchronized (channel) {
                clientAddress = channel.receive(requestBuffer);
                channel.notify();
            }

            byte[] arr = requestBuffer.array();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(arr)) {
                try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                    ServerRequest request = (ServerRequest) ois.readObject();//TODO Сначала получать датаграмму с длиной датаграммы запроса и создавать подходящий буфер
                    request.setSenderAddress(clientAddress);
                    LOGGER.info(request + " received");
                    NetworkManager.pool.submit(new RequestProcessor(request));
                }
            }
            LOGGER.info(this.getClass().getSimpleName() + " thread completed");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
