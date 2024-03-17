package editor;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {
    private BufferedImage sprite;
    private Color color;

    public Object(BufferedImage sprite, Color color) {
        this.sprite = sprite;
        this.color = color;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {

        this.sprite = sprite;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
