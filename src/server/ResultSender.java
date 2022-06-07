package server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class ResultSender implements Runnable {
    private byte[] resultArr;
    private SocketAddress destinationAddress;
    private DatagramChannel channel;

    public ResultSender(byte[] resultArr, SocketAddress destinationAddress, DatagramChannel channel) {
        this.resultArr = resultArr;
        this.destinationAddress = destinationAddress;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            ByteBuffer resultBuffer = ByteBuffer.wrap(resultArr);
            channel.send(resultBuffer, destinationAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
