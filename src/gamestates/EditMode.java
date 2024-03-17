package gamestates;

import editor.EditorMap;
import editor.ObjectManager;
import editor.Tool;
import editor.tools.Bucket;
import editor.tools.Eraser;
import editor.tools.Pencil;
import editor.tools.Picker;
import levels.LevelManager;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import ui.buttons.ObjectButton;
import ui.buttons.SaveMapButton;
import ui.buttons.SoundButton;
import utils.LoadSave;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.PauseButtons.SOUND_SIZE_DEFAULT;


public class EditMode extends State implements Statemethods{
    private ObjectManager objectManager;
    private ObjectButton[] buttons;
    private SaveMapButton saveMapButton;
    private LevelManager levelManager;
    private Level menu;
    private EditorMap editorMap;
    private Tool[] tools;
    private Tool activeTool;
    private BufferedImage[] menuSprite;
    private BufferedImage backgroundImg, backgroundBorder, backgroundCorner;
    private BufferedImage pickedTile;



    public EditMode(Game game) {
        super(game);
        initClasses();

    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager();
        menu = new Level(LoadSave.GetMenuData());
        editorMap = new EditorMap(38, 24);
        importOutsideSprites();
        loadButtons();
        loadTools();
        activeTool = tools[1];
        saveMapButton = new SaveMapButton(32 + SOUND_SIZE * 4 +48, 26, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);

    }

    private void loadButtons(){
        buttons = objectManager.getObjectContainer().getButtons();
    }
    private void loadTools(){
        tools = new Tool[4];
        tools[0] = new Picker("Picker");
        tools[0].setButton(new SoundButton(32, 16, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT));
        tools[1] = new Pencil("Pencil");
        tools[1].setButton(new SoundButton(32 + SOUND_SIZE + 16, 16, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT));
        tools[2] = new Bucket("Bucket");
        tools[2].setButton(new SoundButton(32 + SOUND_SIZE*2 + 32, 16, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT));
        tools[3] = new Eraser("Eraser");
        tools[3].setButton(new SoundButton(32 + SOUND_SIZE*3 + 48, 16, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT));
    }
    @Override
    public void update() {
        for(Tool t : tools){
            t.update();
        }
        saveMapButton.update();
        objectManager.update();
    }
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BG_IMG);
        menuSprite = new BufferedImage[9];
        for(int j = 0; j < 3; j++)
            for(int i = 0; i < 3; i++){
                int index = j*3 + i;
                menuSprite[index] = img.getSubimage(i*32, j*32, 32 ,32);

            }
    }
    public void loadBackground(Graphics g) {
        int[][] menuData = menu.getLevelData();
            for(int height = 0; height < Game.TILES_IN_HEIGHT; height++)
                for(int width = 0; width < menu.getLevelData()[0].length; width++) {
                    int index = menu.getSpriteIndex(height, width) - 1;
                    if(index != 4){
                        g.drawImage(menuSprite[index], Game.TILES_SIZE * width, Game.TILES_SIZE * height, Game.TILES_SIZE, Game.TILES_SIZE, null);
                    }
                    if(width > 20){
                        g.drawImage(menuSprite[index], Game.TILES_SIZE * width, Game.TILES_SIZE * height, Game.TILES_SIZE, Game.TILES_SIZE, null);
                    }
                }

    }


    @Override
    public void draw(Graphics g) {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        g.drawImage(img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        loadBackground(g);
        objectManager.draw(g);
        editorMap.draw(g);
        for(Tool tool : tools){
            tool.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(objectManager.getObjectContainer().getBounds().contains(e.getX(), e.getY())){
            if(activeTool.getObject() != null){
                activeTool.setObject(null);
            }
        }
        for(ObjectButton ob : buttons){
            if(isIn(e, ob)){
                System.out.println("Pressed Button: " + Arrays.stream(buttons).toList().indexOf(ob));
                ob.setMousePressed(true);
                activeTool.setObject(ob.getObject());
            }
        }
        for(Tool t : tools){
            if(t.getButton().getBounds().contains(e.getX(), e.getY())){
                activeTool = t;
                for(Tool t2: tools){
                    t2.setActive(false);
                }
                t.setActive(true);
                System.out.println("Active Tool:" + t.getName());


            }

        }
        if(editorMap.getBounds().contains(e.getX(), e.getY())){

            if(activeTool.getObject() != null){
                int[] tileIndex = new int[2];
                tileIndex = editorMap.getTile(e.getX(), e.getY());

                if(activeTool instanceof Picker){
                    editorMap.setTile(activeTool.getObject().getColor(), tileIndex[0], tileIndex[1]);
                    activeTool.setObject(null);
                } else if(activeTool instanceof Pencil){
                    editorMap.setTile(activeTool.getObject().getColor(), tileIndex[0], tileIndex[1]);
                }


            }


        }
        if(saveMapButton.getBounds().contains(e.getX(), e.getY())){

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        activeTool.setX(e.getX());
        activeTool.setY(e.getY());
        for(ObjectButton ob : buttons){
            if(isIn(e, ob)){
                ob.setMouseOver(true);
            }
        }
        if(objectManager.getObjectContainer().getBounds().contains(e.getX(), e.getY())){
//            System.out.println("Hovering over: objectContainer");
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(editorMap.getBounds().contains(e.getX(), e.getY())){
            int[] tileIndex = new int[2];
            tileIndex = editorMap.getTile(e.getX(), e.getY());
            if(activeTool instanceof Eraser){
                editorMap.clearTile(tileIndex[0], tileIndex[1]);
            }
            if(activeTool.getObject() != null){


                int objectColor = activeTool.getObject().getColor().getRGB();
                int tileColor = editorMap.getMap()[tileIndex[0]][tileIndex[1]];

                if(activeTool instanceof Pencil && objectColor != tileColor){
                    editorMap.setTile(activeTool.getObject().getColor(), tileIndex[0], tileIndex[1]);
                }


            }


        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("Key pressed");

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        objectManager.mouseWheelMoved(e);
    }
}
