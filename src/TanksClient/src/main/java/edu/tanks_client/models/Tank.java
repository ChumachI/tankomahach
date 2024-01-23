package edu.tanks_client.models;
import edu.tanks_client.scenes.Battle;
import edu.tanks_client.utils.Fader;
import edu.tanks_client.utils.Music;
import edu.tanks_client.utils.Params;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ProgressBar;

public class Tank {
    private  ImageView turret;
    private  ImageView fire;
    private  ImageView body;
    private  Pane tank;
    private  ProgressBar healthBar;
    private boolean isFiring = false;
    private static final int TANK_SIZE = Params.TANK_SIZE;
    private static final int SHELL_SIZE = Params.SHELL_SIZE;
    private static final int WINDOW_WIDTH = Params.WINDOW_WIDTH;

    private int shoots = 0;
    private int hits = 0;

    public Tank(String color) {
        initializeTank(color);
    }

    private void initializeTank(String color) {
        turret = new ImageView(new Image("images/" + color + "_turret.png"));
        fire = new ImageView(new Image("gifs/" + color + "_turret_fire.gif"));
        body = new ImageView(new Image("images/" + color + "_body_tracks.png"));
        healthBar = new ProgressBar();
        healthBar.setProgress(1);
        healthBar.setPrefSize(130, 10);
        fire.setOpacity(0);
        tank = new Pane();
        tank.setPrefSize(TANK_SIZE, TANK_SIZE + SHELL_SIZE);
        tank.setLayoutX((WINDOW_WIDTH - TANK_SIZE) / 2.0);
        tank.getChildren().addAll(body, turret, fire, healthBar);
        body.setLayoutY(SHELL_SIZE);
        turret.setLayoutY(SHELL_SIZE);
        fire.setLayoutY(SHELL_SIZE);
    }

    public Pane getPane() {
        return tank;
    }

    public void setLayoutY(int y) {
        tank.setLayoutY(y);
    }

    public void rotateTank(int angle) {
        tank.setRotate(angle);
    }

    public boolean turnAndMoveLeft() {
        if (body.getRotate() > -90) {
            Fader.fadeRotate(body, -90);
        } else if (tank.getTranslateX() > -(WINDOW_WIDTH - TANK_SIZE) / 2.0) {
            Fader.fadeTranslate(tank, -20);
        } else {
            return false;
        }
        return true;
    }

    public boolean turnAndMoveRight() {
        if (body.getRotate() < 90) {
            Fader.fadeRotate(body, 90);
        } else if (tank.getTranslateX() < (WINDOW_WIDTH - TANK_SIZE) / 2.0) {
            Fader.fadeTranslate(tank, 20);
        } else {
            return false;
        }
        return true;
    }

    public void turnLeftAndMoveRight() {
        if (body.getRotate() > -90) {
            Fader.fadeRotate(body, -90);
        } else if (tank.getTranslateX() < (WINDOW_WIDTH - TANK_SIZE) / 2.0) {
            Fader.fadeTranslate(tank, 20);
        }
    }

    public boolean fire() {
        if (!isFiring) {
            new Thread(() -> {
                try {
                    isFiring = true;
                    fire.setOpacity(1);
                    turret.setOpacity(0);
                    Platform.runLater(Music::shoot);
                    Thread.sleep(500);
                    fire.setOpacity(0);
                    turret.setOpacity(1);
                    shoots++;
                } catch (Exception e) {
                    System.out.println("interrupted exception in fire method");
                    fire.setOpacity(0);
                    turret.setOpacity(1);
                } finally {
                    isFiring = false;
                }
            }).start();
            return true;
        }
        return false;
    }

    public void turnRightAndMoveLeft() {
        if (body.getRotate() < 90) {
            Fader.fadeRotate(body, 90);
        } else if (tank.getTranslateX() > -(WINDOW_WIDTH - TANK_SIZE) / 2.0) {
            Fader.fadeTranslate(tank, -20);
        }
    }

    public void setDamage() {
        double newHealth = healthBar.getProgress() - 0.05;
        hits++;
        if (newHealth <= 1e-7) {
            Battle.gameOver(this);
        }
        healthBar.setProgress(newHealth);
    }

    public int getShoots() {
        return shoots;
    }

    public int getHits() {
        return hits;
    }
}

