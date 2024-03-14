package ui.buttons;

import editor.Object;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectButton extends EditorButton{
    private Object object;
    private BufferedImage img;
    private Boolean mouseOver, mousePressed;
    private Boolean isActive = false;
    private int rowIndex, colIndex;


    public ObjectButton(int x, int y, int width, int height, Object object) {
        super(x, y, width, height);
        this.object = object;
        loadObjectImg();

    }

    private void loadObjectImg() {
        img = this.object.getSprite();
    }

    public void update(){
        if(isActive) rowIndex = 1;
        else rowIndex = 0;

        colIndex = 0;
        if(mouseOver) colIndex = 1;
        if(mousePressed) colIndex = 2;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g){
        g.drawImage(img, x, y, width, height, null);
    }

    public boolean isMouseOver(){return mouseOver;}
    public boolean isMousePressed(){return mousePressed;}
    public void setMouseOver(boolean mouseOver){this.mouseOver = mouseOver;}
    public void setMousePressed(boolean mousePressed){this.mousePressed = mousePressed;}
    public void setObject(Object object){this.object = object;}
    public Object getObject(){return object;}


}
