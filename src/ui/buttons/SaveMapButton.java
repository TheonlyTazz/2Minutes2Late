package ui.buttons;

import editor.EditorMap;
import utils.LoadSave;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE_DEFAULT;

public class SaveMapButton extends EditorButton{
    private BufferedImage[][] soundImgs;
    private boolean muted;
    private int rowIndex;
    private int colIndex;
    private boolean mouseOver;
    private boolean mousePressed;

    public SaveMapButton(int x, int y, int width, int height) {
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


    public void onClick(MouseEvent e, EditorMap m) {
        super.onClick(e);
        LoadSave.saveMap(m.getMap());
    }
}
