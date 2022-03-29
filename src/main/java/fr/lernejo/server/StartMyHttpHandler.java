package fr.lernejo.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StartMyHttpHandler implements HttpHandler {

    public final ObjectMapper MyObj = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("POST")) {
            isValidRequest(exchange);
            exchange.close();
        }
        else {
            exchange.sendResponseHeaders(404, "NOT FOUND".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("NOT FOUND".getBytes());
            }
            exchange.close();
        }
    }

    public void isValidRequest(HttpExchange exchange) throws IOException {

        String json = "{\"id\":\"1\", \"url\":\"http://localhost:" + exchange.getLocalAddress().getPort() + "\", \"message\":\"hello\"}";
        List<String> keysFromFile = loadJson(null);
        List<String> keysFromHttpBody = loadJson(exchange);
        if (keysFromFile.equals(keysFromHttpBody)) {
            exchange.sendResponseHeaders(202, json.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        }
        else {
            exchange.sendResponseHeaders(400, "BAD REQUEST".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("BAD REQUEST".getBytes());
            }
        }
    }

    private List<String> loadJson(HttpExchange exchange) throws IOException {

        JsonNode json;
        if(!Objects.isNull(exchange))
            json = this.MyObj.readTree(exchange.getRequestBody());
        else
            json = this.MyObj.readTree(new File("src/ressources/json/startGame.json"));

        Iterator<String> it = json.fieldNames();
        List<String> keys = new ArrayList<String>();
        it.forEachRemaining(e -> keys.add(e));
        return keys;
    }
}
