package levels;

public class Level {

    public int[][] lvlData;

    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
    }

    public int[][] getLevelData() {
        return lvlData;
    }
}
