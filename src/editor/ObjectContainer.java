package editor;

import main.Game;
import ui.buttons.ObjectButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectContainer {
    private int x = (int)(Game.GAME_WIDTH - Game.TILES_DEFAULT_SIZE*3.85 *Game.SCALE);
    private int y = 0;//(int)(Game.TILES_DEFAULT_SIZE * 3);
    private int width = (int) (Game.TILES_DEFAULT_SIZE * 3 * Game.SCALE);
    private int height = (int) (Game.TILES_DEFAULT_SIZE * 15 * Game.SCALE);
    private int scroll = 0;
    private int rowAmount  = 26;
    private int maxScroll = (int)-(Game.TILES_DEFAULT_SIZE * Game.SCALE * (rowAmount + 0.5));
    private int minScroll = (int)(Game.TILES_DEFAULT_SIZE * Game.SCALE * 1.2);
    private BufferedImage border;
    private ArrayList<Object> objects;
    private ObjectButton[] buttons;
    private final Rectangle bounds;

    public ObjectContainer(ArrayList<Object> objects) {
        this.objects = objects;
        loadButtons(objects);
//        System.out.println(buttons.length);
        bounds = new Rectangle(x,y,width,height);
//        System.out.println(bounds);
        loadBorder();

    }
    public Rectangle getBounds() {
        return bounds;
    }
    private void loadBorder(){
        border = LoadSave.GetSpriteAtlas(LoadSave.OBJECT_BORDER);
    }

    public void loadButtons(ArrayList<Object> objects) {
        buttons = new ObjectButton[objects.size()];
        int buttonXOff = (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE * 0.1);
        int buttonX = x + buttonXOff;
        int buttonY = y;
        for(Object o : objects){
            buttons[objects.indexOf(o)] = new ObjectButton(
                    buttonX,
                    buttonY,
                    (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE),
                    (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE),
                    o);
            if(objects.indexOf(o) % 2 == 1){
                buttonX = x + buttonXOff;
                buttonY += (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE * 1.5);
            } else{
                buttonX += (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE *1.5);
            }
        }

    }


    public void draw(Graphics g){
        for(ObjectButton b : buttons){

            if(isIn(b)){
                b.draw(g);
            }
        }
        Color c = new Color(0, 0, 0, 75);
        g.setColor(c);
        g.fillRect(bounds.x-16, bounds.y-16, bounds.width+32, bounds.height+32);
//        g.drawImage(border, bounds.x-16, bounds.y-16, bounds.width+32, bounds.height+32, null);
//        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    public void update(){
        for(ObjectButton b : buttons){
            b.setScroll(scroll);
            b.update();

        }
    }

    public boolean isIn(ObjectButton b){
        return bounds.contains(b.getBounds());
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

    public void mouseWheelMoved(MouseWheelEvent e) {
        if(bounds.contains(e.getPoint())){
            int notches = e.getWheelRotation() * -1;

            if(notches > 0){
//                System.out.println("scroll UP: " + scroll + "minScroll: " + minScroll + "maxScroll: " + maxScroll);

                if(scroll < minScroll) scroll += notches * Game.TILES_DEFAULT_SIZE;
            } else if(notches < 0){
//                System.out.println("scroll DOWN: " + scroll + "minScroll: " + minScroll + "maxScroll: " + maxScroll);
                if(scroll > maxScroll)  scroll += notches * Game.TILES_DEFAULT_SIZE;

            }
        }
    }
}
