package fr.lernejo.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String check = "OK";
        exchange.sendResponseHeaders(200, check.length());

        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(check.getBytes());
        }

        exchange.close();

    }

}
