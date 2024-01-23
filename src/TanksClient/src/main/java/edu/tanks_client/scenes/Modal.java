package edu.tanks_client.scenes;

import edu.tanks_client.connection.Connection;
import edu.tanks_client.models.Tank;
import edu.tanks_client.utils.Fader;
import edu.tanks_client.utils.Music;
import edu.tanks_client.utils.Params;
import edu.tanks_client.utils.TextGenerator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Modal {
    private static Pane header;
    private static Pane statistics;
    private static Tank playerTank;
    private static Tank enemyTank;

    public static void showVictory(Stage stage, Tank playerTank, Tank enemyTank) {
        Modal.playerTank = playerTank;
        Modal.enemyTank = enemyTank;
        showScene(stage, true);
    }

    public static void showDefeat(Stage stage, Tank playerTank, Tank enemyTank) {
        Modal.playerTank = playerTank;
        Modal.enemyTank = enemyTank;
        showScene(stage, false);
    }

    public static void showScene(Stage stage, boolean isVictory) {
        String backgroundFile;
        String headerText;

        if (isVictory) {
            backgroundFile = "images/victory.png";
            headerText = "ПОБЕДА";
            Music.playVictory();
        } else {
            backgroundFile = "images/defeat.png";
            headerText = "ПОРАЖЕНИЕ";
            Music.playDefeat();
        }
        Stage modal = new Stage();
        modal.setMaxWidth(stage.getMaxWidth() / 2.0);
        modal.setMinWidth(stage.getMaxWidth() / 2.0);
        modal.setMaxHeight(stage.getMaxWidth() / 2.0);
        modal.setMinHeight(stage.getMaxWidth() / 2.0);
        double parentCenterX = stage.getX() + stage.getWidth() / 2.0;
        double parentCenterY = stage.getY() + stage.getHeight() / 2.0;
        modal.setX(parentCenterX - modal.getMaxWidth() / 2.0);
        modal.setY(parentCenterY - modal.getMaxHeight() / 2.0);
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundImage(new Image(backgroundFile),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT, false, false, false, true)
        )));
        modal.setOnCloseRequest(windowEvent -> {
            Connection.sendToServer("results " + playerTank.getShoots() + " " + playerTank.getHits() + " " + enemyTank.getShoots() +" "+ enemyTank.getHits());
            Connection.sendToServer("exit");
            System.exit(0);
        });
        header = getHeader(headerText);
        statistics = getStatistics();
        pane.getChildren().add(header);
        pane.getChildren().add(statistics);
        modal.setScene(new Scene(pane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT));
        displayElements();
        modal.show();
    }

    private static Pane getStatistics() {
        Pane statistics = new Pane();
        statistics.setBackground(new Background(new BackgroundFill(new Color(.2, .2, .2, .5), null, null)));
        statistics.setPrefSize(Params.WINDOW_WIDTH / 2.0, Params.WINDOW_HEIGHT / 3.0);
        statistics.setLayoutY(Params.WINDOW_HEIGHT / 100.0 * 25.0);
        Pane playerStats = new Pane();

        playerStats.getChildren().add(getText("Статистика боя", Params.WINDOW_WIDTH / 7.0, Params.WINDOW_HEIGHT / 130.0 * 4, 20));
        playerStats.getChildren().add(getText("Игрок", Params.WINDOW_WIDTH / 10.0, Params.WINDOW_HEIGHT / 13.0, 15));
        playerStats.getChildren().add(getText("Противник", Params.WINDOW_WIDTH / 10.0 + 270, Params.WINDOW_HEIGHT / 13.0, 15));
        playerStats.getChildren().add(getText("Выстрелы", Params.WINDOW_WIDTH / 5.0, Params.WINDOW_HEIGHT / 130.0 * 15, 15));
        playerStats.getChildren().add(getText(String.valueOf(playerTank.getShoots()), Params.WINDOW_WIDTH / 10.0, Params.WINDOW_HEIGHT / 130.0 * 20, 15));
        playerStats.getChildren().add(getText(String.valueOf(enemyTank.getShoots()), Params.WINDOW_WIDTH / 10.0 + 400, Params.WINDOW_HEIGHT / 130.0 * 20, 15));
        playerStats.getChildren().add(getText("Попадания", Params.WINDOW_WIDTH / 5.0, Params.WINDOW_HEIGHT / 130.0 * 25, 15));
        playerStats.getChildren().add(getText(String.valueOf(enemyTank.getHits()), Params.WINDOW_WIDTH / 10.0, Params.WINDOW_HEIGHT / 130.0 * 30, 15));
        playerStats.getChildren().add(getText(String.valueOf(playerTank.getHits()), Params.WINDOW_WIDTH / 10.0 + 400, Params.WINDOW_HEIGHT / 130.0 * 30, 15));
        playerStats.getChildren().add(getText("Промахи", Params.WINDOW_WIDTH / 5.0, Params.WINDOW_HEIGHT / 130.0 * 35, 15));
        playerStats.getChildren().add(getText(String.valueOf(playerTank.getShoots() - enemyTank.getHits()), Params.WINDOW_WIDTH / 10.0, Params.WINDOW_HEIGHT / 130.0 * 40, 15));
        playerStats.getChildren().add(getText(String.valueOf(enemyTank.getShoots() - playerTank.getHits()), Params.WINDOW_WIDTH / 10.0 + 400, Params.WINDOW_HEIGHT / 130.0 * 40, 15));
        statistics.getChildren().add(playerStats);
        return statistics;
    }

    private static Node getText(String text, double layoutX, double layoutY, double fontSize) {
        Text enemyStatsHeader = TextGenerator.generateText(text, fontSize);
        enemyStatsHeader.setLayoutX(layoutX);
        enemyStatsHeader.setLayoutY(layoutY);
        return enemyStatsHeader;
    }

    private static Pane getHeader(String headerText) {
        Pane header = new Pane();
        header.setBackground(new Background(new BackgroundFill(new Color(.2, .2, .2, .5), null, null)));
        header.setPrefSize(Params.WINDOW_WIDTH / 2.0, Params.WINDOW_HEIGHT / 8.0);
        header.setLayoutY(Params.WINDOW_HEIGHT / 200.0 * 10.0);
        Text text = TextGenerator.generateText(headerText, 50);
        header.setPadding(new Insets(0, 0, 0, 0));
        header.setOpaqueInsets(new Insets(0, 0, 0, 0));
        text.setLayoutX((Params.WINDOW_WIDTH / 2.0 - text.getBoundsInLocal().getWidth()) / 2.0);
        text.setLayoutY(Params.WINDOW_HEIGHT / 130.0 * 11.5);
        header.getChildren().add(text);
        return header;
    }

    private static void displayElements() {
        Fader.fadeDisplay(header, 2);
        Fader.fadeDisplay(header.getChildren().getFirst(), 2);
        Fader.fadeDisplay(statistics, 2);
        Fader.fadeDisplay(statistics.getChildren().getFirst(), 2);
        Pane playerStats = (Pane) statistics.getChildren().getFirst();
        for (Node node : playerStats.getChildren()) {
            Fader.fadeDisplay(node, 2);
        }
    }

}
