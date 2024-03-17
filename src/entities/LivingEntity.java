package entities;

import main.Game;

import java.awt.image.BufferedImage;

import static utils.Constants.Directions.LEFT;

public class LivingEntity extends Entity{
    BufferedImage[] sprite;

    protected int aniIndex;
    protected int aniTick, aniSpeed = 50;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public LivingEntity(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

}
