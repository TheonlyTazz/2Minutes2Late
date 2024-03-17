package ui.buttons;

import ui.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class EditorButton extends Button {
    private BufferedImage img;
    public EditorButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void onClick(MouseEvent e) {

    }
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }
}

