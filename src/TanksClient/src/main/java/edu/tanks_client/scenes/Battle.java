package edu.tanks_client.scenes;

import edu.tanks_client.connection.Connection;
import edu.tanks_client.models.Shell;
import edu.tanks_client.models.Tank;
import edu.tanks_client.utils.Params;
import edu.tanks_client.utils.Music;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Battle {

    private static Stage stage;
    private static Scene scene;
    private static Tank playerTank;
    private static Tank enemyTank;
    private static Pane battleField;
    private static boolean isGameOver = false;
    public static void start(Stage stage) {
        Battle.stage = stage;
        MainMenu.showScene(stage);
    }
    public static void showScene() {
        battleField = new Pane();
        battleField.setBackground(new Background(new BackgroundImage(new Image("images/terrain.png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));
        scene = new Scene(battleField, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        stage.setScene(scene);
        playerTank = new Tank("blue");
        battleField.setPadding(new Insets(0, 0, 0, 0));
        playerTank.setLayoutY(Params.WINDOW_HEIGHT - Params.TANK_SIZE - Params.SHELL_SIZE);
        enemyTank = new Tank("red");
        battleField.getChildren().add(playerTank.getPane());
        battleField.getChildren().add(enemyTank.getPane());
        enemyTank.rotateTank(180);
        enemyTank.setLayoutY(0);
        battleField.setPadding(new Insets(0, 0, 0, 0));

        stage.show();
        Music.playBattleMusic();
    }
    public static void addSceneEventListeners() {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                if (playerTank.turnAndMoveLeft()) {
                    Connection.sendToServer("moved left");
                }
            } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                if (playerTank.turnAndMoveRight()) {
                    Connection.sendToServer("moved right");
                }
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                if (playerTank.fire()) {
                    Connection.sendToServer("fired");
                    starShell(playerTank, enemyTank);
                }

            }
        });
    }
    public static void starShell(Tank from, Tank to) {
        Shell shell = new Shell("images/shell.png", to);
        battleField.getChildren().add(shell);
        shell.setRotate(from.getPane().getRotate());
        shell.setLayoutX(Params.WINDOW_WIDTH / 2.0 + from.getPane().getTranslateX() - Params.SHELL_SIZE);
        if (from.getPane().getLayoutY() > to.getPane().getLayoutY()) {
            shell.setLayoutY(from.getPane().getLayoutY());
        } else {
            shell.setLayoutY(from.getPane().getLayoutY() + Params.TANK_SIZE);
        }

        shell.shellFly();
    }
    public static void moveEnemyRight(){
        enemyTank.turnLeftAndMoveRight();
    }
    public static void moveEnemyLeft() {
        enemyTank.turnRightAndMoveLeft();
    }
    public static void enemyFire() {
        if (enemyTank.fire()) {
            Platform.runLater(() -> {
                Battle.starShell(enemyTank, playerTank);
            });
        }
    }
    private static void showVictory() {
        if(!isGameOver){
            Music.stopBattleMusic();
            Modal.showVictory(stage, playerTank, enemyTank);
            isGameOver = true;
        }
    }
    private static void showDefeat() {
        if(!isGameOver){
            Music.stopBattleMusic();
            Modal.showDefeat(stage, playerTank, enemyTank);
            isGameOver = true;
        }
    }

    public static void gameOver(Tank tank) {
        if(tank.equals(enemyTank)){
            showVictory();
        } else {
            showDefeat();
        }
    }
}
