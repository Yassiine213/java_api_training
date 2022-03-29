package fr.lernejo.navy_battle;

import fr.lernejo.server.Server;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Launcher {
    public static void main(String[] args) throws IOException {

        if (args.length > 0) {
            Server s1 = new Server(Integer.parseInt(args[0]), "localhost");
            s1.beg();
            if (args.length == 2)
            {
                Client client = new Client(Integer.parseInt(args[0]));
                HttpResponse resp = client.sendPostRequest(Integer.parseInt(args[0]), args[1]);
            }
        }

    }
}
