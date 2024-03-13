package utils;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;

import static utils.Constants.EnemyConstants.CRABBY;
import static utils.Constants.EnemyConstants.CRABBY_COLOR;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Player/player_sprites.png";
    public static final String LEVEL_ATLAS = "Level/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "Level/level_one_data_long.png";
    public static final String MENU_BUTTONS = "UI/button_atlas.png";
    public static final String MENU_BACKGROUND = "UI/menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "UI/background_menu.png";
    public static final String PAUSE_BACKGROUND = "UI/pause_menu.png";
    public static final String SOUND_BUTTONS = "UI/sound_button.png";
    public static final String URM_BUTTONS = "UI/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "UI/volume_buttons.png";
    public static final String PLAYING_BG_IMG = "Level/playing_bg_img.png";
    public static final String EDIT_BG_IMG = "UI/background_tile.png";
    public static final String BIG_CLOUDS = "Level/big_clouds.png";
    public static final String SMALL_CLOUDS = "Level/small_clouds.png";
    public static final String ENEMY_CRABBY = "Enemies/crabby_sprite.png";
    public static final String STATUS_BAR = "UI/health_power_bar.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            assert is != null;
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Crabby> GetCrabs() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crabby> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                if(color.equals(CRABBY_COLOR))
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;

    }

    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;

    }
}