package editor;

import main.Game;

import java.awt.*;
import java.util.Objects;

public class EditorMap {
    private ObjectManager objectManager;
    private Object[] objects;

    private int[][] map;
    private int width;
    private int height;
    private int xOff = Game.TILES_DEFAULT_SIZE*2;
    private int yOff = Game.TILES_DEFAULT_SIZE*2;
    private Rectangle rect;

    public EditorMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new int[width][height];
        rect = new Rectangle(xOff, yOff, width*Game.TILES_DEFAULT_SIZE, height*Game.TILES_SIZE);
        initClasses();
        System.out.println("rectangle: " + rect.toString());
    }
    public void initClasses(){
        objectManager = new ObjectManager();
    }
    public int[][] getMap() {
        return map;
    }
    public void clearTile(int x, int y){
        map[x][y] = 0;
    }
    public void setTile(Color color, int x, int y) {
        int red, green, blue;
        if(color.getRed() == 0) red = Color.decode(""+map[x][y]).getRed();
        if(color.getGreen() == 0) green = Color.decode(""+map[x][y]).getGreen();
        if(color.getBlue() == 0) blue = Color.decode(""+map[x][y]).getBlue();
        map[x][y] = color.getRGB();
    }
    public Object[] getObjects(Color color) {
        objects = new Object[3];
        int red = color.getRed();
        if(red != 0) objects[0] = objectManager.getTilesList().get(red);
        int green = color.getGreen();
        int blue = color.getBlue();

        return objects;

    }

    public void draw(Graphics g){
        Color c = new Color(0,0, 0, 120);
        for(int x = 0; x < width * Game.TILES_DEFAULT_SIZE; x += Game.TILES_DEFAULT_SIZE){
            for(int y = 0; y < height * Game.TILES_DEFAULT_SIZE; y += Game.TILES_DEFAULT_SIZE){
                g.setColor(c);
//                g.fillRect(x+xOff, y+yOff, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
                g.setColor(Color.gray);
                g.drawRect(x+xOff, y+yOff, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);

                int mapIndexX = x / Game.TILES_DEFAULT_SIZE;
                int mapIndexY = y / Game.TILES_DEFAULT_SIZE;
                // tiles
                if(map[mapIndexX][mapIndexY] != 0){
                    Color c2 = new Color(map[mapIndexX][mapIndexY]);
                    for(Object o: getObjects(c2)){
                        if(o != null){
                            g.drawImage(o.getSprite(), x+xOff, y+yOff, null);

                        }

                    }

                }

                // entities

                // objects

            }
        }


    }

    public Rectangle getBounds() {
        return rect;
    }

    public int[] getTile(int x, int y){
        int[] intIndex = new int[2];
        int xIndex = x / Game.TILES_DEFAULT_SIZE - 2;
        int yIndex = y / Game.TILES_DEFAULT_SIZE - 2;
        intIndex[0] = xIndex;
        intIndex[1] = yIndex;
        return intIndex;


    }


}
