package editor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {
    private BufferedImage sprite;
    private Color color;

    public Object(BufferedImage sprite, Color color) {
        this.sprite = sprite;
        this.color = color;
    }
}
