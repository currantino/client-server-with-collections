package server;

import mid.ServerRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestProcessor implements Runnable {

    private final ExecutorService pool;

    private ServerRequest request;

    public RequestProcessor() {
        this.pool = Executors.newCachedThreadPool();
    }


    @Override
    public void run() {
         Future<String> future = pool.submit(new Handler(request));
    }
}