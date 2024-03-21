package editor.tools;

import editor.EditorMap;
import editor.Tool;
import main.Game;

public class Move extends Tool {
    private int oldX, oldY;
    private int moveSpeed = (int) (Game.SCALE * Game.TILES_DEFAULT_SIZE);
    public Move(String name) {
        super(name);
    }

    public void moveMap(int x, int y, EditorMap map) {

        if(x > oldX){
            map.setxOff(-moveSpeed);
        }
        if(x < oldX){
            map.setxOff(moveSpeed);
        }
        if(y > oldY){
            map.setyOff(-moveSpeed);
        }
        if(y < oldY){
            map.setyOff(moveSpeed);
        }
        oldX = x;
        oldY = y;


    }
}
