package ui;

import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton{
    private BufferedImage[][] soundImgs;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImg();
    }

    private void loadSoundImg() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for(int height = 0; height < soundImgs.length; height++)
            for(int width = 0; width < soundImgs[height].length; width++)
                soundImgs[height][width] = temp.getSubimage(width * SOUND_SIZE_DEFAULT, height * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);

    }
    public void update(){
        if(muted)
            rowIndex = 1;
        else{
            rowIndex = 0;
            
        }
        colIndex = 0;
        if(mouseOver)
            colIndex = 1;
        if(mousePressed)
            colIndex = 2;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
    public void draw(Graphics g){
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
