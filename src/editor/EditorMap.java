package editor;

import main.Game;
import utils.LoadSave;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class EditorMap {
    private ObjectManager objectManager;
    private Object[] objects;

    private int[][] map;
    private int width = 57;
    private int height = 36;
    private float zoom = 1f;
    private float minZoom = 1f, maxZoom = 8.0f;
    private int xOff = (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE);
    private int yOff = (int) (Game.TILES_DEFAULT_SIZE * Game.SCALE);
    private Rectangle rect;
    private int backgroundX1 = 0, backgroundX2 = Game.GAME_WIDTH;
    private BufferedImage[] backgroundImg;


    public EditorMap() {

        map = new int[width][height];
                rect = new Rectangle(xOff, yOff, (int) (width*Game.TILES_DEFAULT_SIZE*zoom), (int) (height*Game.TILES_DEFAULT_SIZE*zoom));
        initClasses();
        System.out.println("rectangle: " + rect.toString());
        backgroundImg = ResourceLoader.loadBackground(LoadSave.PLAYING_BG_DIR);
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
    private void drawBackground(Graphics g) {
        for (BufferedImage background : backgroundImg) {

            g.drawImage(background, xOff, yOff, (int)(width * Game.TILES_DEFAULT_SIZE*zoom), (int)(height * Game.TILES_DEFAULT_SIZE*zoom), null);
            g.drawImage(background, xOff, yOff, (int)(width * Game.TILES_DEFAULT_SIZE*zoom), (int)(height * Game.TILES_DEFAULT_SIZE*zoom), null);
        }
    }
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width * Game.TILES_DEFAULT_SIZE && y >= 0 && y < height * Game.TILES_DEFAULT_SIZE;
    }
    public void draw(Graphics g){
        drawBackground(g);
        Color c = new Color(0,0, 0, 70);

        for(int x = 0; x < ((width * Game.TILES_DEFAULT_SIZE)*zoom); x += (int) (Game.TILES_DEFAULT_SIZE*zoom)){
            for(int y = 0; y < ((height * Game.TILES_DEFAULT_SIZE)*zoom); y += (int) (Game.TILES_DEFAULT_SIZE*zoom)){
                g.setColor(c);
                g.setColor(Color.gray);

                if(inBounds(x, y)){
                    g.drawRect(x+xOff, y+yOff, (int) (Game.TILES_DEFAULT_SIZE*zoom), (int) (Game.TILES_DEFAULT_SIZE*zoom));

                    int mapIndexX = (int) ((x / Game.TILES_DEFAULT_SIZE/zoom));
                    int mapIndexY = (int) ((y / Game.TILES_DEFAULT_SIZE/zoom));
                    // tiles
                    if(map[mapIndexX][mapIndexY] != 0){
                        Color c2 = new Color(map[mapIndexX][mapIndexY]);
                        for(Object o: getObjects(c2)){
                            if(o != null){
                                g.drawImage(o.getSprite(),x+xOff, y+yOff, (int) (o.getSprite().getWidth()*zoom), (int) (o.getSprite().getHeight()*zoom), null);

                            }

                        }

                    }
                }



            }
                // entities
                // objects
        }
        g.setColor(Color.WHITE);
        g.drawRect(rect.x, rect.y, (int) (rect.width/zoom), (int) (rect.height/zoom));
    }

    public Rectangle getBounds() {
        return rect;
    }

    public int[] getTile(int x, int y){
        int[] intIndex = new int[2];
        int xIndex = (int) ((x-xOff) / Game.TILES_DEFAULT_SIZE/zoom);
        int yIndex = (int) ((y-yOff) / Game.TILES_DEFAULT_SIZE/zoom);
        intIndex[0] = xIndex;
        intIndex[1] = yIndex;
        System.out.println("X: " + x + ", Y: " + y);
        System.out.println("intIndex[0]: " + intIndex[0] + ", intIndex[1]: " + intIndex[1]);
        return intIndex;


    }

    public void setZoom(float zoom){
        if(zoom > this.maxZoom+0.5f) zoom = this.maxZoom;
        else if(zoom < this.minZoom-0.5f) zoom = this.minZoom;
        else this.zoom = zoom;

        System.out.println("Zoom: " + zoom);
        rect.width = (int) (width*Game.TILES_DEFAULT_SIZE*zoom);
        rect.height = (int) (height*Game.TILES_DEFAULT_SIZE*zoom);
    }
    public void adjustZoom(float zoom){
        setZoom(this.zoom + zoom);
    }
    public float getZoom(){
        return this.zoom;
    }


}
