package utils;

import java.awt.image.BufferedImage;

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

}
