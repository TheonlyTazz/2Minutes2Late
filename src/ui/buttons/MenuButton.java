package ui.buttons;

import gamestates.Gamestate;
import ui.Button;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.Buttons.*;

public class MenuButton extends Button {
    private int rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int x, int y, int rowIndex, Gamestate state){
        super(x,y,B_WIDTH,B_HEIGHT);
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();



    }

    private void initBounds() {
        bounds = new Rectangle(x -xOffsetCenter, y, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i* B_WIDTH_DEFAULT, rowIndex* B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    public void draw(Graphics g){
        g.drawImage(imgs[index], x - xOffsetCenter, y, B_WIDTH, B_HEIGHT, null);
    }

    public void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public Rectangle getBounds(){
        return bounds;
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}
