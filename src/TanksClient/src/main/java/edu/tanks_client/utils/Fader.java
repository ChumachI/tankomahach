package edu.tanks_client.utils;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Fader {
    public static void fadeRotate(ImageView body, int angle) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(.5));
        rotateTransition.setToAngle(angle);
        rotateTransition.setNode(body);
        rotateTransition.play();

    }

    public static void fadeTranslate(Pane tank, double dist) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(tank);
        translateTransition.setDuration(Duration.seconds(.06));
        translateTransition.setByX(dist);
        translateTransition.play();
    }

    public static void fadeHide(Node node, double duration) {
        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(duration), new KeyValue(node.opacityProperty(), 0.0))
        );

        fadeOutTimeline.play();
    }
    public static void fadeDisplay(Node node, int duration) {
        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(duration), new KeyValue(node.opacityProperty(), 1.0))
        );
        fadeOutTimeline.play();
    }
}

