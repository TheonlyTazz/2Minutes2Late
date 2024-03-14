package levels;

public class Level {

    public int[][] lvlData;
    private int levelHeight;
    private int levelWidth;


    public Level(int[][] lvlData){
        this.lvlData = lvlData;
        this.levelHeight = lvlData.length;
        this.levelWidth = lvlData[0].length;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
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
