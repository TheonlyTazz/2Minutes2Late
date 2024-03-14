package editor;

import main.Game;
import ui.buttons.ObjectButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ObjectContainer {
    private int x = (int)(Game.GAME_WIDTH - Game.TILES_SIZE*5);
    private int y;
    private int width,height;
    private ArrayList<Object> objects;
    private ObjectButton[] buttons;

    public ObjectContainer(ArrayList<Object> objects) {
        this.objects = objects;
        loadButtons(objects);
        System.out.println(buttons.length);

    }

    public void loadButtons(ArrayList<Object> objects) {
        buttons = new ObjectButton[objects.size()];
        int borderOffset = Game.TILES_DEFAULT_SIZE / 2;
        int buttonX = x + borderOffset;
        int buttonY = y + borderOffset;
        for(Object o : objects){
            if(objects.indexOf(o) % 2 == 0){
                buttonX = x + Game.TILES_SIZE;
                buttonY += (int) (Game.TILES_SIZE * 1.5);
            } else{
                buttonX += Game.TILES_SIZE * 2;
            }
            buttons[objects.indexOf(o)] = new ObjectButton(
                    buttonX,
                    buttonY,
                    Game.TILES_SIZE,
                    Game.TILES_SIZE,
                    o);

        }

    }


    public void draw(Graphics g){
        for(ObjectButton b : buttons){
            b.draw(g);
        }
    }
    private void update(){
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

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    public ObjectButton[] getButtons() {
        return buttons;
    }
}
