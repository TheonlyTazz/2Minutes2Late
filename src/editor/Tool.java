package editor;

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


    public Tool(String name){
        this.name = name;

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
    public java.lang.Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }

    public void draw(Graphics g){
        button.draw(g);
//        g.drawRect(button.getBounds().x, button.getBounds().y, button.getBounds().width, button.getBounds().height);
    }


    public void update() {
        button.update();
    }
}
