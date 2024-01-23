package edu.tanks.app;

import edu.tanks.server.Server;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(8081);
    }
}
