package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import entities.EnemyManager;
import entities.livingentities.Npc;
import entities.livingentities.Player_old;
import entities.livingentities.Player;
import levels.LevelManager;
import main.Game;
import ui.overlays.GameOverOverlay;
import ui.overlays.PauseOverlay;
import ui.overlays.WonOverlay;
import utils.Constants;
import utils.LoadSave;
import utils.ResourceLoader;

public class Playing extends State implements Statemethods {
    private Player_old playerOld;
    private Player player;
    private ArrayList<Npc> npcs = new ArrayList<>();
    private Npc npc;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private WonOverlay wonOverlay;
    private boolean paused = false;

    private int xLvlOffset;
    private int yLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int topBorder = (int) (0.8 * Game.GAME_HEIGHT);
    private int bottomBorder = (int) (0.2 * Game.GAME_HEIGHT);

    private int backgroundX1 = 0, backgroundX2 = Game.GAME_WIDTH, backgroundx3 = -Game.GAME_WIDTH;
    private int[] backgroundParallax = new int[]{1, 2, 2, 3, 1};
    private int backgroundOffset = 0;
    private float backgroundRepeat;


    private BufferedImage bigCloud, smallCloud;
    private BufferedImage[] backgroundImg;

    private final Random RANDOM_WALK_SPEED = new Random();


    private boolean gameOver;
    private boolean won = false;

    public Playing(Game game, ResourceLoader resourceLoader) {
        super(game, resourceLoader);
        levelManager = new LevelManager(game);
        initClasses();

        // backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        backgroundImg = ResourceLoader.loadBackground(LoadSave.PLAYING_BG_DIR);
    }

    private void initClasses() {
        levelManager.loadLevel(LoadSave.LEVEL_DATA + levelManager.getPlayedLevel() + ".png");
        int startX = levelManager.getCurrentLevel().getPlayerStart()[0] * Game.TILES_SIZE;
        int startY = levelManager.getCurrentLevel().getPlayerStart()[1] * Game.TILES_SIZE;
        enemyManager = new EnemyManager(this);
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        wonOverlay = new WonOverlay(this);

        playerOld = new Player_old(startX, startY, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this, levelManager);
        playerOld.setSpawn();

        player = new Player(startX, startY, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), "Player/3 Cyborg/Cyborg", this, levelManager);

        ArrayList<int[]> start = levelManager.getCurrentLevel().getNpcStartTiles(LoadSave.LEVEL_DATA + levelManager.getPlayedLevel() + ".png");

        for (int[] position : start) {
            int x = position[0] * Game.TILES_SIZE;
            int y = position[1] * Game.TILES_SIZE;
            String folder = String.valueOf(position[2] - 200);
            System.out.println(folder);
            npcs.add(new Npc(x, y, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), "Entities/townsmen/" + folder + "/", this, levelManager, RANDOM_WALK_SPEED.nextFloat()));
            System.out.println(x + " " + y);
        }

        System.out.println();
    }

    @Override
    public void update() {
        if (!paused && !gameOver && !won) {
            levelManager.update();
            playerOld.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), playerOld);

            for (Npc npc : npcs) {
                npc.update();
            }

            checkCloseToBorder();
            checkDeathZone();

            if (!won) {
                checkWinZone();
            }
        } else if (!gameOver && won) {
            wonOverlay.update();
        } else
            pauseOverlay.update();

    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int playerY = (int) player.getHitbox().y;
        int maxLvlOffsetX = levelManager.getMaxLvlOffsetX();
        int maxLvlOffsetY = levelManager.getMaxLvlOffsetY();
        int xDiff = playerX - xLvlOffset;
        int yDiff = playerY - yLvlOffset;

        if (xDiff > rightBorder)
            xLvlOffset += xDiff - rightBorder;
        else if (xDiff < leftBorder)
            xLvlOffset += xDiff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

        if (yDiff > topBorder)
            yLvlOffset += yDiff - topBorder;
        else if (yDiff < bottomBorder)
            yLvlOffset += yDiff - bottomBorder;

        if (yLvlOffset > maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if (yLvlOffset < 0)
            yLvlOffset = 0;
    }

    private void checkDeathZone() {
        int tileX = (int) playerOld.getHitbox().x / Game.TILES_SIZE;
        int tileY = (int) playerOld.getHitbox().y / Game.TILES_SIZE;
        int value = levelManager.getTileValue(tileX, tileY);

        int DEATH_ZONE = Constants.ColorMapConstants.DeathZone.DEATH_ZONE;
        if (value == DEATH_ZONE) {
            setGameOver(true);
        }
    }

    private void checkWinZone() {
        int tileX = (int) playerOld.getHitbox().x / Game.TILES_SIZE;
        int tileY = (int) playerOld.getHitbox().y / Game.TILES_SIZE;
        int value = levelManager.getTileValue(tileX, tileY);

        int winZone = Constants.ColorMapConstants.WinZone.WIN_ZONE;

        if (value == winZone) {
            won = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);


//        drawClouds(g);

        levelManager.draw(g, xLvlOffset, yLvlOffset);
        player.render(g, xLvlOffset, yLvlOffset);
        enemyManager.draw(g, xLvlOffset, yLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (won) {
            wonOverlay.draw(g);
        }

        if (!won) {
            for (Npc npc : npcs) {
                npc.render(g, xLvlOffset, yLvlOffset);
            }
        }
    }

    private void drawBackground(Graphics g) {


        for (int i = 0; i < backgroundImg.length; i++) {
            g.drawImage(backgroundImg[i], backgroundX1, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
            g.drawImage(backgroundImg[i], backgroundX2, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
            g.drawImage(backgroundImg[i], backgroundx3, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

            backgroundX1 = (-xLvlOffset + backgroundOffset) * backgroundParallax[i];
            backgroundX2 = (-xLvlOffset + Game.GAME_WIDTH + backgroundOffset) * backgroundParallax[i];
            backgroundx3 = (-xLvlOffset - Game.GAME_WIDTH + backgroundOffset) * backgroundParallax[i];
        }

        backgroundRepeat = -player.getHitbox().x + xLvlOffset;

        if (backgroundX1 < backgroundRepeat - leftBorder && player.getFlipX() == 0) {
            backgroundOffset += Game.GAME_WIDTH;
        } else if (backgroundx3 > backgroundRepeat + leftBorder && player.getFlipX() != 0) {
            backgroundOffset -= Game.GAME_WIDTH;
        }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        for (Npc npc : npcs) {
            npc.resetAll();
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
//                player.setAttacking(true);
                player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playerOld.setLeft(true);
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    playerOld.setRight(true);
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    playerOld.setJump(true);
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_2:
                    levelManager.loadLevel(LoadSave.LEVEL_TWO_DATA);
                    int startX = levelManager.getCurrentLevel().getPlayerStart()[0] * Game.TILES_SIZE;
                    int startY = levelManager.getCurrentLevel().getPlayerStart()[1] * Game.TILES_SIZE;
                    player = new Player(startX, startY, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), "Player/2 Punk/Punk", this, levelManager);

                    this.resetAll();
                    break;
                case KeyEvent.VK_1:
                    levelManager.loadLevel(LoadSave.LEVEL_ONE_DATA);
                    startX = levelManager.getCurrentLevel().getPlayerStart()[0] * Game.TILES_SIZE;
                    startY = levelManager.getCurrentLevel().getPlayerStart()[1] * Game.TILES_SIZE;
                    player = new Player(startX, startY, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), "Player/1 Biker/Biker", this, levelManager);
                    break;
                case KeyEvent.VK_3:
                    startX = levelManager.getCurrentLevel().getPlayerStart()[0] * Game.TILES_SIZE;
                    startY = levelManager.getCurrentLevel().getPlayerStart()[1] * Game.TILES_SIZE;
                    player = new Player(startX, startY, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), "Player/3 Cyborg/Cyborg", this, levelManager);

//                    this.resetAll();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    playerOld.setLeft(false);
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    playerOld.setRight(false);
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    playerOld.setJump(false);
                    player.setJump(false);
                    break;
            }

    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);

            if (won)
                wonOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);

            if (won)
                wonOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (won) {
                pauseOverlay.mouseMoved(e);
            }
        }
    }

    public void unpauseGame() {
        paused = false;
    }

    public void startNexLevel() {
        System.out.println("pressed");
        levelManager.setPlayedLevel(levelManager.getPlayedLevel() + 1);
        npcs = new ArrayList<>();
        resetAll();
        initClasses();
        won = false;
    }

    public void windowFocusLost() {
        playerOld.resetDirBooleans();
    }

    public Player_old getPlayer() {
        return playerOld;
    }

}