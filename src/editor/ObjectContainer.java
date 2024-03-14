package editor;

import main.Game;

import java.awt.*;
import java.util.ArrayList;

public class ObjectContainer {
    private ArrayList<Object> objects;
    public ObjectContainer(ArrayList<Object> objects) {
        this.objects = objects;
    }



    public void draw(Graphics g){
        int x = 3;
        int y = 1;
        int counter = 0;
        for(Object o : objects){

//            System.out.println("X: " + x + " Y: " + y + " ObjectIndex: " + counter);
            g.drawImage(o.getSprite(),
                    x * Game.TILES_SIZE + Game.TILES_SIZE*19,
                    y * Game.TILES_SIZE,
                    Game.TILES_DEFAULT_SIZE,
                    Game.TILES_DEFAULT_SIZE,
                    null);

            x++;
            counter++;
            if(x % 3 == 0) {
                y++;
                x = 3;
            }
        }
    }
    private void update(){

    }
}
