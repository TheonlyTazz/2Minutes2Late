package editor;

import java.awt.*;

public class Map {
    private int[][] map;
    private int width;
    private int height;
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

    }
}
