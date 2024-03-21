package utils;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.livingentities.Crabby;
import main.Game;

import static utils.Constants.EnemyConstants.CRABBY_COLOR;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Player/player_sprites.png";
    public static final String LEVEL_ATLAS = "Level/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "Level/level_one_data_long.png";
    public static final String LEVEL_TWO_DATA = "Level/level_one_data_long2.png";
    public static final String MENU_BUTTONS = "UI/button_atlas.png";
    public static final String MENU_BACKGROUND = "UI/menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "UI/background_menu.png";
    public static final String PAUSE_BACKGROUND = "UI/pause_menu.png";
    public static final String SOUND_BUTTONS = "UI/sound_button.png";
    public static final String URM_BUTTONS = "UI/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "UI/volume_buttons.png";
    public static final String PLAYING_BG_IMG = "Level/city.png";
    public static final String PLAYING_BG_DIR = "res/UI/backgrounds/city-backgrounds-pixel-art/Backgrounds/1/Night";
    public static final String EDIT_BG_IMG = "UI/level_editor_background.png";
    public static final String EDIT_BG_map = "UI/level_designer/map.png";
    public static final String OBJECT_BORDER = "UI/frame.png";
    public static final String BIG_CLOUDS = "Level/big_clouds.png";
    public static final String SMALL_CLOUDS = "Level/small_clouds.png";
    public static final String ENEMY_CRABBY = "Enemies/crabby_sprite.png";
    public static final String STATUS_BAR = "UI/health_power_bar.png";
    public static final int DEATH_ZONE = Constants.ColorMapConstants.DeathZone.DEATH_ZONE;

    public static BufferedImage GetSpriteAtlas(String fileName) {
//        System.out.println(fileName);
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
    public static void saveMap(int[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, new Color(0,0,0).getRGB());

                if(pixels[x][y] == 0){
                    image.setRGB(x, y, new Color(0,0,0).getRGB());
                } else{
                    image.setRGB(x, y, new Color(pixels[x][y]).getRGB());
                }
            }
        }
        File outputFile = new File("image.png");
        try{
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }


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

    public static int[][] GetMenuData() {
        BufferedImage img = GetSpriteAtlas(EDIT_BG_map);
        int multiplier = 25;
        int[][] data = new int[img.getHeight()][img.getWidth()];
        for(int i = 0; i < img.getHeight(); i++)
            for(int j = 0; j < img.getWidth(); j++){
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value > 25*3*3){
                    data[i][j] = 5; //default middle
                } else{
                    data[i][j] = value / multiplier;
                }
            }
        return data;
    }
    public static int[][] GetLevelData(String level) {
        BufferedImage img = GetSpriteAtlas(level);

        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48 && value < 200)
                    value = 0;

                lvlData[j][i] = value;
            }
        return lvlData;

    }
}