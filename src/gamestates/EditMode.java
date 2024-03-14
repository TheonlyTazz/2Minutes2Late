package gamestates;

import editor.Object;
import editor.ObjectManager;
import levels.LevelManager;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import ui.Button;
import ui.buttons.ObjectButton;
import utils.LoadSave;


public class EditMode extends State implements Statemethods{
    private ObjectManager objectManager;
    private ObjectButton[] buttons;
    private LevelManager levelManager;
    private Level menu;
    private BufferedImage[] menuSprite;
    private BufferedImage backgroundImg, backgroundBorder, backgroundCorner;
    private BufferedImage pickedTile;



    public EditMode(Game game) {
        super(game);
        initClasses();

    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);

        menu = new Level(LoadSave.GetMenuData());
        importOutsideSprites();
        loadButtons();
    }

    private void loadButtons(){
        buttons = objectManager.getObjectContainer().getButtons();
    }
    @Override
    public void update() {
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
                    g.drawImage(menuSprite[index], Game.TILES_SIZE * width, Game.TILES_SIZE * height, Game.TILES_SIZE, Game.TILES_SIZE, null);
                }

    }


    @Override
    public void draw(Graphics g) {
        loadBackground(g);
        objectManager.drawContainer(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(ObjectButton ob : buttons){
            if(isIn(e, ob)){
                System.out.println("Pressed Button: " + Arrays.stream(buttons).toList().indexOf(ob));
                ob.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(ObjectButton ob : buttons){
            if(isIn(e, ob)){
                System.out.println("Hovering over: " + Arrays.stream(buttons).toList().indexOf(ob));
                ob.setMouseOver(true);
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
}
