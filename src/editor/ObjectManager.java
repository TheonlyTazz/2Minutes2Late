package editor;

import gamestates.EditMode;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectManager {
    private EditMode editMode;
    private BufferedImage[] tilesArr;
    private ArrayList<Object> tilesList;

    public ObjectManager(EditMode editMode) {
        this.editMode = editMode;
        loadTiles();
        createTilesList();

    }
    private void createTilesList() {
        tilesList = new ArrayList<>();
        Color color = Color.red;
        for(BufferedImage tile : tilesArr) {
            tilesList.add(new Object(tile, color));
        }

    }
    private void loadTiles() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        int width = temp.getWidth() / Game.TILES_DEFAULT_SIZE;
        int height = temp.getHeight() / Game.TILES_DEFAULT_SIZE;
        tilesArr = new BufferedImage[width * height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tilesArr[j + (i*j)] = temp.getSubimage(Game.TILES_DEFAULT_SIZE*i, Game.TILES_DEFAULT_SIZE*j, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
            }
        }
    }
    public ArrayList<Object> getTilesList() {
        return tilesList;
    }
}
