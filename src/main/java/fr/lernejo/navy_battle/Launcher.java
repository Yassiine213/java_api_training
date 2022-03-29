package fr.lernejo.navy_battle;

import fr.lernejo.server.MyServer;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        MyServer server = new MyServer();
        server.begin();
    }
}
