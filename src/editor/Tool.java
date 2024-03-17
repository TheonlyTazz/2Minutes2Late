package editor;

import editor.Object;
import ui.Button;
import ui.buttons.SoundButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tool {
    protected String name;
    protected SoundButton button;
    private Object object;
    protected BufferedImage[] cursor;
    private Boolean active = false;
    private int x,y;

    public Tool(String name){
        this.name = name;

    }
    public String getName(){
        return name;
    }

    public void setButton(SoundButton button) {
        this.button = button;
    }
    public Button getButton() {
        return button;
    }

    public void setActive(Boolean active){
        this.active = active;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }

    public void draw(Graphics g){
        button.draw(g);
        if(object != null && active){
            g.drawImage(object.getSprite(), x-2, y-2, null);
        }
//        g.drawRect(button.getBounds().x, button.getBounds().y, button.getBounds().width, button.getBounds().height);
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

    public void update() {
        button.update();
    }
}
