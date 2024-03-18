package editor;

import gamestates.EditMode;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectManager {
    private ArrayList<Object> tilesList;
    private ObjectContainer objectContainer;


    public ObjectManager() {
        loadTiles();
        this.objectContainer = new ObjectContainer(tilesList);
    }



    private void loadTiles() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        tilesList = new ArrayList<>();

        int width = temp.getWidth() / Game.TILES_DEFAULT_SIZE;
        int height = temp.getHeight() / Game.TILES_DEFAULT_SIZE;
        BufferedImage[] tilesArr = new BufferedImage[width * height];
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                int index = i + width * j;
//                System.out.println(index);
                tilesArr[index] = temp.getSubimage(Game.TILES_DEFAULT_SIZE*i, Game.TILES_DEFAULT_SIZE*j, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
                Color color = new Color(index, 0, 0);
                tilesList.add(new Object(tilesArr[index], color));
            }
        }
    }
    public ArrayList<Object> getTilesList() {
        return tilesList;
    }

    private ObjectContainer fillObjectContainer(ArrayList<Object> tilesList) {
        return new ObjectContainer(tilesList);
    }
    public ObjectContainer getObjectContainer() {
        if(objectContainer == null) {
            return null;
        }
        return objectContainer;
    }

    public void draw(Graphics g) {

        objectContainer.draw(g);
    }

    public void update(){
        objectContainer.update();
    }
    public void mousePressed(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        objectContainer.mouseWheelMoved(e);
    }
}
