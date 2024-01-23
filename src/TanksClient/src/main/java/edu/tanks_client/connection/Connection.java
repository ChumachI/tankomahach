package edu.tanks_client.connection;

import edu.tanks_client.scenes.Battle;
import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private static Socket socket;
    private static Scanner in;
    private static PrintWriter out;

    public static void setSocket(Socket socket) {
        Connection.socket = socket;
    }

    public static void connect() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            new Thread(() -> {
                while (in.hasNext()) {
                    String income = in.nextLine();
                    if (income.equals("moved left")) {
                        Battle.moveEnemyRight();
                    } else if (income.equals("moved right")) {
                        Battle.moveEnemyLeft();
                    } else if (income.equals("fired")) {
                        Battle.enemyFire();
                    } else if(income.equals("start")){
                        Platform.runLater(Battle::addSceneEventListeners);
                    }
                }
            }).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToServer(String text) {
        out.println(text);
        out.flush();
    }
}
