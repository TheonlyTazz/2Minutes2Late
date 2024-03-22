package ui.overlays;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import ui.buttons.PauseButton;
import ui.buttons.SoundButton;
import ui.buttons.UrmButton;
import ui.buttons.VolumeButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.URMButtons.*;
import static utils.Constants.UI.VolumeButtons.*;

public class WonOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton  unpauseB;
    private final String wonText = "Level Geschafft!";

    public WonOverlay(Playing playing) {
        this.playing = playing;

        loadBackground();
        createURMButton();
    }

    private void createURMButton() {
        int unpauseX = Game.GAME_WIDTH / 2 - URM_SIZE / 2;
        int bY = Game.GAME_HEIGHT / 2 - URM_SIZE / 2;

        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        unpauseB.update();
    }


    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 50));

        FontMetrics fm = g.getFontMetrics();
        int textX = (Game.GAME_WIDTH - fm.stringWidth(wonText)) / 2;
        int textY = ((Game.GAME_HEIGHT - fm.getHeight()) / 3) + fm.getAscent();

        g.drawString(wonText, textX, textY);
        unpauseB.draw(g);
    }


    public void mousePressed(MouseEvent e) {

        if(isIn(e, unpauseB))
            unpauseB.setMousePressed(true);

    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.startNexLevel();
            }
        }
        unpauseB.resetBools();
    }


    public void mouseMoved(MouseEvent e) {
        unpauseB.setMouseOver(false);
        if(isIn(e, unpauseB)){
            unpauseB.setMouseOver(true);
        }
    }
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());

    }
}
