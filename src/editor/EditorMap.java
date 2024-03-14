package editor;

import main.Game;

import java.awt.*;

public class Map {
    private int[][] map;
    private int width;
    private int height;
    private int xOff = Game.TILES_DEFAULT_SIZE;
    private int yOff = Game.TILES_DEFAULT_SIZE*2;
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new int[width][height];
    }
    public int[][] getMap() {
        return map;
    }
    public void setTile(Color color, int x, int y) {
        int red, green, blue;
        if(color.getRed() == 0) red = Color.decode(""+map[x][y]).getRed();
        if(color.getGreen() == 0) green = Color.decode(""+map[x][y]).getGreen();
        if(color.getBlue() == 0) blue = Color.decode(""+map[x][y]).getBlue();


        map[x][y] = color.getRGB();
    }

    public void draw(Graphics g){
        Color c = new Color(0,0, 0, 120);
        for(int x = 0; x < width * Game.TILES_DEFAULT_SIZE; x += Game.TILES_DEFAULT_SIZE){
            for(int y = 0; y < height * Game.TILES_DEFAULT_SIZE; y += Game.TILES_DEFAULT_SIZE){
                g.setColor(c);

                g.fillRect(x+xOff, y+yOff, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);

                g.setColor(Color.gray);
                g.drawRect(x+xOff, y+yOff, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);

            }
        }
    }
}
