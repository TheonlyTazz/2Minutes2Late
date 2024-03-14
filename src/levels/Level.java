package levels;

public class Level {

    public int[][] lvlData;
    private int levelHeight;
    private int levelWidth;
    private int playerStartX, playerStartY;



    public Level(int[][] lvlData){
        this.lvlData = lvlData;
        this.levelHeight = lvlData.length;
        this.levelWidth = lvlData[0].length;
        this.setPlayerStartTile();
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
    }
    public int[] getPlayerStart(){
        return new int[]{playerStartX, playerStartY};
    }

    public void setPlayerStartTile() {
        for(int y = 0; y < lvlData.length; y++)
            for(int x = 0; x < lvlData[0].length; x++) {
                if (lvlData[y][x] == 240) {
//                    System.out.println("x: " + x + ", y: " + y);
                    playerStartX = x;
                    playerStartY = y;
                }
            }

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
