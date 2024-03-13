package gamestates;

import levels.LevelManager;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Player;
import utils.Constants;
import utils.LoadSave;

import static utils.Constants.Environment.*;
import static utils.Constants.UI.*;

public class EditMode extends State implements Statemethods{
    private LevelManager levelManager;


    private BufferedImage backgroundImg;
    private BufferedImage pickedTile;

    public EditMode(Game game) {
        super(game);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.EDIT_BG_IMG);

    }

    private void initClasses() {
        levelManager = new LevelManager(game);
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        for(int y = 0; y < Game.TILES_IN_HEIGHT; y++){
            for(int x = 0; x < Game.TILES_IN_WIDTH; x++){
                System.out.println("x: " + x*Background.BG_DEFAULT_SIZE  + ", y: " + (y*Background.BG_DEFAULT_SIZE));
                g.drawImage(backgroundImg,
                        x*Background.BG_DEFAULT_SIZE / (int) Game.SCALE,
                        (y*Background.BG_DEFAULT_SIZE) / (int) Game.SCALE,
                        32,
                        32,
                        null);
            }

        }
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
