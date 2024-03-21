package entities.livingentities;

import entities.LivingEntity;
import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import utils.Constants;
import utils.Constants.NpcConstants;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.NpcConstants.*;
import static utils.HelpMethods.*;

public class Npc extends LivingEntity {
    private Playing playing;
    private LevelManager levelManager;


    private BufferedImage[][] animations;
    private String npcSkin;
    private int textureWidth = 48;
    private int[][] lvlData;

    private int playerAction = IDLE;
    private boolean moving = true, attacking = false;
    private float xSpeed = 0;
    private int attackCombo = 0, comboTimer = 0;
    private int attack = IDLE;
    private float walkSpeed = .2f * Game.SCALE;
    private float xDrawOffset = 26 * Game.SCALE;
    private float yDrawOffset = 16 * Game.SCALE;
    private boolean left, up, right = true, down, jump;

    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private float maxFallSpeed = 1.5f * Game.SCALE;
    private boolean inAir = false;
    private Rectangle2D.Float attackBox;
    private boolean attackChecked;
    private int flipX = 0;
    private int flipW = 1;


    public Npc(int x, int y, int width, int height, String npcSkin, Playing playing, LevelManager levelManager) {
        super(x, y, width, height);
        this.playing = playing;
        this.levelManager = levelManager;
        this.npcSkin = npcSkin;

        setSpawn();
        loadSprites();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (30 * Game.SCALE));
        loadlvlData();
        this.setDebug(false);
    }
    public void update() {
        updatePos();
        if (attacking)
            checkAttack();
        handleCombo();
        updateAnimationTick();
        setAnimation();
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset) - yLvlOffset, width * flipW, height, null);
        drawHitbox(g, xLvlOffset, yLvlOffset);
//		drawAttackBox(g, xLvlOffset, yLvlOffset);
        showDebugInfo(g, xLvlOffset, yLvlOffset);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations[playerAction].length) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }

        }

    }
    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = Constants.NpcConstants.WALK;
        else
            playerAction = IDLE;

        if (startAni != playerAction)
            resetAniTick();
    }
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    // Spawn
    public void setSpawn(){
        x = levelManager.getCurrentLevel().getPlayerStart()[0] * Game.TILES_SIZE;
        y = levelManager.getCurrentLevel().getPlayerStart()[1] * Game.TILES_SIZE;
    }
    // Movement

    private void updatePos() {
        moving = true;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        if (left) {
            xSpeed = -walkSpeed;
            flipX = width/2;
            flipW = -1;
        }
        if (right) {
            xSpeed = walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelManager.getCurrentLevel().getLevelData())) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                if (airSpeed > maxFallSpeed) {
                    airSpeed = maxFallSpeed;
                }

                updateXPos();
                return;
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos();
            }

        } else {
            if (IsFloor(hitbox, xSpeed, lvlData) && IsFloorRight(hitbox, xSpeed, lvlData)) {
                if (CanMoveHere(hitbox.x + hitbox.width, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelManager.getCurrentLevel().getLevelData())) {

                    updateXPos();
                    return;
                }
            }
        }

        changeWalkDir();
        moving = true;
    }
    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }
    private void updateXPos() {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;}
        else
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
    }
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }
    public void handleCombo(){
        if(comboTimer != 0){
            comboTimer--;
            if(comboTimer == 0 || attackCombo == 6){
                comboTimer = 0;
                attackCombo = 0;
                System.out.println("attackCombo: 0");

            }
        }
    }


    public void loadSprites() {
        int totalAnimations = Constants.NpcConstants.class.getDeclaredFields().length;
        animations = new BufferedImage[totalAnimations][];
        try{
            for(Field field: Constants.NpcConstants.class.getDeclaredFields()){
                if(field.getType().equals(int.class)){
                    int value = field.getInt(null);
                    String file = Constants.NpcConstants.getFileName(value);

                    BufferedImage[] temp = ResourceLoader.loadSprite(npcSkin + "_" + file, textureWidth);
                    animations[value] = temp;

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void loadlvlData() {
        this.lvlData = levelManager.getCurrentLevel().getLevelData();
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void changeWalkDir() {
        left = !left;
        right = !right;
    }

    // Sets
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public int getFlipW() {
        return this.flipW;
    }

    public int getFlipX() {
        return this.flipX;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;
        setSpawn();
        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        this.loadlvlData();
    }


}
