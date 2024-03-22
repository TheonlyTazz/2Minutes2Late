package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level level;
    private int lvlTilesWidth, lvlTilesHeight;
    private int maxXTilesOffset, maxYTilesOffset;
    private int maxLvlOffsetX, maxLvlOffsetY;
    private int playedLevel = 1;

    public LevelManager(Game game){
        this.game = game;
        importOutsideSprites();
        loadLevel(LoadSave.LEVEL_DATA + playedLevel + ".png");
    }


    public void loadLevel(String levelName){
        System.out.println("Loading level " + levelName);
        level = new Level(LoadSave.GetLevelData(levelName));
    }

    public int getLvlTilesWidth() {
        if(level == null) return 0;
        return this.getCurrentLevel().getLevelWidth() * Game.TILES_SIZE;
    }
    public int getLvlTilesHeight() {
        if(level == null) return 0;
        return this.getCurrentLevel().getLevelHeight() * Game.TILES_SIZE;
    }
    public int getMaxXTilesOffset() {
        if(level == null) return 0;
        return this.getLvlTilesWidth() * Game.TILES_IN_WIDTH;
    }
    public int getMaxYTilesOffset() {
        if(level == null) return 0;
        return this.getLvlTilesHeight() * Game.TILES_IN_HEIGHT;
    }
    public int getMaxLvlOffsetX() {
        if(level == null) return 0;
        return this.getMaxXTilesOffset() * Game.TILES_SIZE;
    }
    public int getMaxLvlOffsetY() {
        if(level == null) return 0;
        return this.getMaxYTilesOffset() * Game.TILES_SIZE;
    }

    public int getTileValue(int x, int y){
        if(level == null) return 0;
        return this.getCurrentLevel().getLevelData()[y][x];
    }

    public int getPlayedLevel() {
        return playedLevel;
    }

    public void setPlayedLevel(int playedLevel) {
        this.playedLevel = playedLevel;
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j = 0; j < 4; j++)
            for(int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32 ,32);

            }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        for(int height = 0; height < this.getCurrentLevel().getLevelHeight(); height++)
            for(int width = 0; width < this.getCurrentLevel().getLevelWidth(); width++){
                int index = this.getCurrentLevel().getSpriteIndex(height, width);
                if(index >= 48) index =0;
                if(levelSprite[index] != null && index != 0){
                    g.drawImage(levelSprite[index], Game.TILES_SIZE * width - xLvlOffset, Game.TILES_SIZE * height - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
                }

            }

    }
    public void update(){
    }

    public Level getCurrentLevel(){
        return level;
    }
}
