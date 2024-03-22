package levels;

import entities.Entity;
import entities.livingentities.Npc;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.ColorMapConstants.*;

public class Level {

    private int[][] lvlData;
    private int levelHeight;
    private int levelWidth;
    private int playerStartX, playerStartY;
    private ArrayList<Npc> npcs;
    private ArrayList<Entity> entities;

    public Level(int[][] lvlData){
        this.lvlData = lvlData;
        this.levelHeight = lvlData.length;
        this.levelWidth = lvlData[0].length;
        this.setPlayerStartTile();
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
    }
    public int getTileInfo(String type, int x, int y) {
        Color c = new Color(lvlData[x][y]);
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        return switch (type) {
            case TILE -> red;
            case ENTITY -> green;
            case OBJECT -> blue;
            default -> 0;
        };
    }

    public int[] getPlayerStart(){
        return new int[]{playerStartX, playerStartY};
    }

    public void setPlayerStartTile() {
        for(int y = 0; y < lvlData.length; y++)
            for(int x = 0; x < lvlData[0].length; x++) {
                if (lvlData[y][x] == 240) {
                    playerStartX = x;
                    playerStartY = y;
                }
            }
    }

    public ArrayList<int[]> getNpcStartTiles() {
        int[][] lvlDataEntities = LoadSave.GetLevelDataEntities(LoadSave.LEVEL_ONE_DATA);
        ArrayList<int[]> list = new ArrayList<>();
        for (int y = 0; y < lvlDataEntities.length; y++) {
            for (int x = 0; x < lvlDataEntities[0].length; x++) {
                if (lvlDataEntities[y][x] >= Constants.ColorMapConstants.Npc.NPC_SPAWN_RANGE_START && lvlDataEntities[y][x] <= Constants.ColorMapConstants.Npc.NPC_SPAWN_RANGE_END) {
                    list.add(new int[]{x, y, lvlDataEntities[y][x]});
                    System.out.println(x + " " + y);
                }
            }
        }

        return list;
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLevelHeight() {
        return levelHeight;
    }
    public int getLevelWidth() {
        return levelWidth;
    }
    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }
    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }
    public void setLevelDimensions(int levelWidth, int levelHeight) {
        this.setLevelHeight(levelHeight);
        this.setLevelWidth(levelWidth);
    }
}
