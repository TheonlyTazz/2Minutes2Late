package editor.tools;

import editor.EditorMap;
import editor.Tool;
import main.Game;

public class Move extends Tool {
    private int oldX, oldY;
    private int x = 0; private int y = 0;
    private int moveSpeed = (int) (Game.SCALE * Game.TILES_DEFAULT_SIZE);
    public Move(String name) {
        super(name);
    }

    public void moveMap(int mouseX, int mouseY, EditorMap map) {
        System.out.println("Move map " + x + ", " + y);
        if(mouseX < oldX){
            x += moveSpeed;
            map.setxOff(x);
        }else if(mouseX > oldX){
            x -= moveSpeed;
            map.setxOff(x);
        }
        if(mouseY > oldY){
            y += moveSpeed;
            map.setyOff(y);
        } else if(mouseY < oldY){
            y -= moveSpeed;
            map.setyOff(y);
        }
        oldX = x;
        oldY = y;


    }
}
