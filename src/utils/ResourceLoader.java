package utils;

import com.sun.tools.jconsole.JConsoleContext;

import java.awt.image.BufferedImage;
import java.io.File;

public class ResourceLoader {
    private static BufferedImage[] LevelTileSet;
    private static BufferedImage[][] LevelData;
    private static BufferedImage[][] Entities;
    private static BufferedImage[][] Objects;


    public static BufferedImage[] loadSprite(String filename, int width) {
        return loadTileSet(filename, width, 0);
    }

    public static BufferedImage[] loadTileSet(String filename, int width, int height) {
        BufferedImage image = LoadSave.GetSpriteAtlas(filename);
        return loadTileSet(image, width, height);
    }

    public static BufferedImage[] loadTileSet(BufferedImage spriteAtlas, int width, int height) {
        if (spriteAtlas == null) {
            return null;
        }
        int w = spriteAtlas.getWidth();
        int h = spriteAtlas.getHeight();

        if (height == 0) {
            height = h;
        }
        BufferedImage[] sprites = new BufferedImage[w / width];
        for (int y = 0; y < h / height; y++) {
            for (int x = 0; x < w / width; x++) {
                sprites[x + (y * x) + y] = spriteAtlas.getSubimage(x * width, y * height, width, h);
            }
        }
        return sprites;
    }

    public static BufferedImage[] loadBackground(String folder) {
        File directory = new File(folder);

        File[] listOfFiles = directory.listFiles();

        BufferedImage[] background = new BufferedImage[0];
        if (listOfFiles != null) {
            background = new BufferedImage[listOfFiles.length];
        }

        for (int i = 0; i < background.length; i++) {
            if (listOfFiles[i].isFile()) {
                String path = listOfFiles[i].getPath();
                String newPath = path.replace("res\\", "");

                background[i] = LoadSave.GetSpriteAtlas(newPath);
            }
        }

        return background;
    }

    public static BufferedImage[] getLevelTileSet() {
        return LevelTileSet;
    }

    public static BufferedImage[][] getLevelData() {
        return LevelData;
    }

    public static BufferedImage[][] getEntities() {
        return Entities;
    }

    public static BufferedImage[][] getObjects() {
        return Objects;
    }
}
