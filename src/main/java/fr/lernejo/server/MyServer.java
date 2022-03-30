package fr.lernejo.server;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MyServer {

    public void begin() throws IOException {
        int port = 9876;
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new HttpHandlerHomeMade());
        server.createContext("/api/game/start", new HttpHandlerStart());
        server.start();
    }
    /*public final int port;
    public final String url;

    public Server (int port, String url) {
        this.port = port;
        this.url = url;
    }

    public void beg() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.url, this.port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new MyHttpHandler());
        server.createContext("/api/game/start", new StartMyHttpHandler());
        server.start();
        System.out.println("Server  at port : " + this.port);
    }*/

}
