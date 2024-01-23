package edu.tanks_client.app;

import edu.tanks_client.scenes.Battle;
import edu.tanks_client.utils.Music;
import edu.tanks_client.utils.Params;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setMaxWidth(Params.WINDOW_WIDTH);
        stage.setMinWidth(Params.WINDOW_WIDTH);
        Battle.start(stage);
        stage.setOnCloseRequest(windowEvent -> {
            Music.stop();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}