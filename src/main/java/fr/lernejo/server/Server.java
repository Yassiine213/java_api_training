package fr.lernejo.server;

import com.sun.net.httpserver.HttpServer;

public class Server {

    public final int port;
    public final String url;

    public Server (int port, String url) {
        this.port = port;
        this.url = url;
    }
}
