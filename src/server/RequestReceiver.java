package server;

import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;

public class RequestReceiver implements Runnable {
    private DatagramChannel channel;

    public RequestReceiver(DatagramChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        new RequestReceiverHandler(channel);
    }
}
