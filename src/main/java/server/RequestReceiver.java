package server;

import mid.ServerRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import static server.Server.channel;

public class RequestReceiver implements Runnable {
    SocketAddress clientAddress;
    Logger LOGGER = Logger.getLogger("receiver");
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
                    ServerRequest request = (ServerRequest) ois.readObject();
                    request.setSenderAddress(clientAddress);
                    LOGGER.info(request + " received");
                    Server.pool.submit(new RequestProcessor(request));
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
