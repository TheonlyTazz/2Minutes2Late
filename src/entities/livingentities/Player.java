package entities.livingentities;

import entities.LivingEntity;
import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import utils.Constants.PlayerConstants2;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants2.*;
import static utils.HelpMethods.*;

public class Player extends LivingEntity {
    private Playing playing;
    private LevelManager levelManager;


    private BufferedImage[][] animations;
    private String playerSkin;
    private int textureWidth = 48;
    private int[][] lvlData;

    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private float xSpeed = 0;
    private int attackCombo = 0, comboTimer = 0;
    private int attack = IDLE;
    private float walkSpeed = 1.0f * Game.SCALE;
    private float xDrawOffset = 26 * Game.SCALE;
    private float yDrawOffset = 16 * Game.SCALE;
    private boolean left, up, right, down, jump;

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


    public Player(float x, float y, int width, int height, String playerSkin, Playing playing, LevelManager levelManager) {
        super(x, y, width, height);
        this.playing = playing;
        this.levelManager = levelManager;
        this.playerSkin = playerSkin;

        setSpawn();
        loadSprites();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (30 * Game.SCALE));
        initAttackBox();
        loadlvlData();
        this.setDebug(true);

    }
    public void update(){

        updateAttackBox();
        updatePos();
        if (attacking)
            checkAttack();
        handleCombo();
        updateAnimationTick();
        setAnimation();
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset) - yLvlOffset, width * flipW, height, null);
//        drawHitbox(g, xLvlOffset, yLvlOffset);
//		drawAttackBox(g, xLvlOffset, yLvlOffset);
        showDebugInfo(g, xLvlOffset, yLvlOffset);
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (24 * Game.SCALE));
    }
    private void drawAttackBox(Graphics g, int lvlOffsetX, int yLvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y - yLvlOffsetX, (int) attackBox.width, (int) attackBox.height);

    }
    private void updateAttackBox() {
        if (right)
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        else if (left)
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

        attackBox.y = hitbox.y + (Game.SCALE * 5);
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
            playerAction = PlayerConstants2.RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = IDLE;
        }

        if (attacking) {

            aniSpeed = 15;
            switch(attackCombo){
                case 1:
                case 2: playerAction = PUNCH;break;
                case 3: playerAction = ATTACK_1;break;
                case 4: playerAction = ATTACK_2;break;
                case 5: playerAction = ATTACK_3;break;
            }
            if(xSpeed != 0){
                playerAction = RUNNING_PUNCH;
            }
            if (startAni != PUNCH && startAni != ATTACK_1 && startAni != ATTACK_2 && startAni != ATTACK_3 && startAni != RUNNING_PUNCH) {
                if(attackCombo < 6){
                    comboTimer = animations[playerAction].length * aniSpeed + 120;
                    attackCombo++;
                }
                aniSpeed = 100;
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
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
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width/2;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
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
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos();
            }

        } else
            updateXPos();
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
        int totalAnimations = PlayerConstants2.class.getDeclaredFields().length;
        animations = new BufferedImage[totalAnimations][];
        try{
            for(Field field: PlayerConstants2.class.getDeclaredFields()){
                if(field.getType().equals(int.class)){
                    int value = field.getInt(null);
                    String file = PlayerConstants2.getFileName(value);
                    BufferedImage[] temp = ResourceLoader.loadSprite(playerSkin + "_" + file, textureWidth);
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
