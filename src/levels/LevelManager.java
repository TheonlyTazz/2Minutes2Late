package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;


    public LevelManager(Game game){
        this.game = game;
        //levelSprite = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());

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

    public void draw(Graphics g, int xLvlOffset) {
        for(int height = 0; height < Game.TILES_IN_HEIGHT; height++)
            for(int width = 0; width < levelOne.getLevelData()[0].length; width++) {
                int index = levelOne.getSpriteIndex(height, width);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * width - xLvlOffset, Game.TILES_SIZE * height, Game.TILES_SIZE, Game.TILES_SIZE, null);

            }

    }
    public void update(){

    }
    public Level getCurrentLevel(){
        return levelOne;
    }
}
