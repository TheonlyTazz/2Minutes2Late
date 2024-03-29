package ui.buttons;

import ui.Button;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseButton extends Button {


    public PauseButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds();

    }

    private void createBounds() {
        bounds = new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
