package edu.tanks_client.models;

import edu.tanks_client.utils.Fader;
import edu.tanks_client.utils.Params;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class Shell extends ImageView {
    private final Tank enemyTank;

    boolean isCollision = false;
    public Shell(String s, Tank enemyTank) {
        super(s);
        this.enemyTank = enemyTank;

    }
    public void move() {
        if(enemyTank.getPane().getLayoutY() > 0){
            setLayoutY(getLayoutY() + Params.SHELL_SPEED);
        } else {
            setLayoutY(getLayoutY() - Params.SHELL_SPEED);
        }
    }
    public void shellFly() {
        // Запустите анимацию для движения снаряда
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
                checkCollision();
            }
        };
        timer.start();
    }

    private void checkCollision(){
        if(!isCollision){
            Bounds shellBound = this.getBoundsInParent();
            Bounds enemyTankBounds = enemyTank.getPane().getBoundsInParent();
            if(shellBound.intersects(enemyTankBounds)){
                enemyTank.setDamage();
                Fader.fadeHide(this, .1);
                isCollision = true;
            }

        }
    }
}
