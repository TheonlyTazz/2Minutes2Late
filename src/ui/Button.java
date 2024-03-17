package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Button {
    protected int x,y,width,height;
    protected Rectangle bounds;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public abstract void onClick(MouseEvent e);
}
