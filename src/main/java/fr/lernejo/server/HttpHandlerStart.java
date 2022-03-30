package fr.lernejo.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HttpHandlerStart implements HttpHandler {

    public final ObjectMapper something = new ObjectMapper();

    // cf voir comment envoyer une reponse depuis le server avec httpexchange
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            VerifyBody(exchange); // si VerifyBody == TRUE alors envoyer réponse 202 avec comme message ACCEPTED, sinon envoyer code réponse/ statut 400 avec le messafge BAD REQUEST
            exchange.sendResponseHeaders(202, "ACCEPTED".length());
            exchange.getResponseBody().write("ACCEPTED".getBytes());
            exchange.close();
        } else {
            exchange.sendResponseHeaders(404, "NOT FOUND".length());
            exchange.getResponseBody().write("NOT FOUND".getBytes());
            exchange.close(); // envoyer code statut 404 avec pour message NOT FOUND
        }
    }

    public boolean VerifyBody(HttpExchange exchange) throws IOException {
        List<String> filesKeys = jsonNote(null); // 1 - lire fichier jason et stocker dans une variable
        List<String> bodyKeys = jsonNote(exchange); // 2- lire le contenue du body et stocker dans une variable

        if (filesKeys.equals(bodyKeys)) { return true; }// 3 - les comparer (les listes) et stocker dans une variable, return true si oui
        else {
            exchange.sendResponseHeaders(400, "BAD REQUEST".length());
            exchange.getResponseBody().write("BAD REQUEST".getBytes());
            return false;
        } // 3.2 si false return false
        // cf voir comment récupérer les clé d'un json avec jacson en java
    }

    public List<String> jsonNote(HttpExchange exchange) throws IOException {
        JsonNode json;
        if(!Objects.isNull(exchange))
            json = this.something.readTree(exchange.getRequestBody());
        else
            json = this.something.readTree(new File("src/VerifyBody.json"));
        Iterator<String> fld = json.fieldNames();
        List<String> keys = new ArrayList<String>();
        fld.forEachRemaining(e -> keys.add(e));
        return keys;
    }
}


