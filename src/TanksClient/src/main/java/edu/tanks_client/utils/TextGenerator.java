package edu.tanks_client.utils;

import edu.tanks_client.scenes.MainMenu;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.InputStream;

public class TextGenerator {
    public static Text generateText(String text, double size) {
        Text result = new Text(text);
        InputStream fontStream = MainMenu.class.getResourceAsStream("/fonts/tenoture.ttf");
        Font externalFont = Font.loadFont(fontStream, size);
        result.setFill(new Color(1, .7, 1, .8));
        result.setFont(externalFont);
        result.setOpacity(0);
        return result;
    }
}
