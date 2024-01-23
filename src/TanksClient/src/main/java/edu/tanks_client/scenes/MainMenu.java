package edu.tanks_client.scenes;

import edu.tanks_client.connection.Connection;
import edu.tanks_client.utils.Fader;
import edu.tanks_client.utils.Music;
import edu.tanks_client.utils.Params;
import edu.tanks_client.utils.TextGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.Socket;

public class MainMenu {
    private static Pane header;
    private static Pane inputServer;

    public static void showScene(Stage stage) {
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundImage(new Image("images/menu.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT, false, false, false, true)
        )));
        header = getHeader();
        inputServer = getInputServerField();
        pane.getChildren().add(header);
        pane.getChildren().add(inputServer);
        stage.setScene(new Scene(pane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT));
        Music.playMenuMusic();
        stage.show();
        displayPanes();
    }

    private static Pane getInputServerField() {
        Pane result = new Pane();
        result.setBackground(new Background(new BackgroundFill(new Color(.2, .2, .2, .8), null, null)));
        result.setPrefSize(500, 120);
        result.setLayoutX(450);
        result.setLayoutY(400);

        Text text = TextGenerator.generateText("Введита адрес сервера", 10);
        text.setLayoutY(20);
        text.setLayoutX(142);

        TextField textField = new TextField();
        textField.setPromptText("127.0.0.1:8081");
        textField.setAlignment(Pos.CENTER);
        textField.setFocusTraversable(false);
        textField.setLayoutX(167);
        textField.setLayoutY(30);

        Text errorText = TextGenerator.generateText("Сервер не найден", 10);
        errorText.setFill(new Color(1, 0, 0, 1));
        errorText.setLayoutX(172);
        errorText.setLayoutY(70);
        errorText.setOpacity(0);

        Button enterButton = getStartGameButton(textField, errorText);
        result.getChildren().add(text);
        result.getChildren().add(textField);
        result.getChildren().add(enterButton);
        result.getChildren().add(errorText);

        result.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                checkServerData(textField, errorText);
            }
        });
        return result;
    }

    private static Button getStartGameButton(TextField textField, Text errorText) {
        Button enterButton = new Button("В БОЙ!");
        enterButton.setOnMouseClicked(mouseEvent -> {
            checkServerData(textField, errorText);
        });
        enterButton.setLayoutX(225);
        enterButton.setLayoutY(80);
        return enterButton;
    }

    private static void checkServerData(TextField textField, Text errorText){
        try {
            String address = textField.getText().split(":")[0];
            String port = textField.getText().split(":")[1];
            Socket socket = new Socket(address, Integer.parseInt(port));
            errorText.setOpacity(0);
            Connection.setSocket(socket);
            Connection.connect();
            Battle.showScene();
            Music.stopMenuMusic();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setOpacity(1);
        }
    }
    private static Pane getHeader() {
        Pane header = new Pane();
        header.setBackground(new Background(new BackgroundFill(new Color(.2, .2, .2, .5), null, null)));
        header.setPrefSize(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT/7.0);
        header.setLayoutY(Params.WINDOW_HEIGHT / 100.0 * 10.0);
        Text gameName = TextGenerator.generateText("ТАНКОМАХАЧ", 100);
        gameName.setLayoutX((header.getPrefWidth() - gameName.getBoundsInLocal().getWidth()) / 2.0);
        header.setPadding(new Insets(0,0,0,0));
        header.setOpaqueInsets(new Insets(0,0,0,0));
        gameName.setLayoutX((Params.WINDOW_WIDTH - gameName.getBoundsInLocal().getWidth())/2.0);
        gameName.setLayoutY(Params.WINDOW_HEIGHT / 100.0 * 11.5);
        header.getChildren().add(gameName);
        return header;
    }




    public static void displayPanes() {
        Fader.fadeDisplay(header, 3);
        Fader.fadeDisplay(header.getChildren().getFirst(), 3);
        Fader.fadeDisplay(inputServer, 3);
        Fader.fadeDisplay(inputServer.getChildren().getFirst(), 3);
    }
}
