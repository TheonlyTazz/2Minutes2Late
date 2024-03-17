package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;

import entities.livingentities.Player_old;
import main.Game;

public abstract class Enemy extends LivingEntity {
	protected int enemyState, enemyType;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;

	}

	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}

	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
		}
	}

	protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}

	protected void turnTowardsPlayer(Player_old playerOld) {
		if (playerOld.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	protected boolean canSeePlayer(int[][] lvlData, Player_old playerOld) {
		int playerTileY = (int) (playerOld.getHitbox().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(playerOld)) {
				if (IsSightClear(lvlData, hitbox, playerOld.hitbox, tileY))
					return true;
			}

		return false;
	}

	protected boolean isPlayerInRange(Player_old playerOld) {
		int absValue = (int) Math.abs(playerOld.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}

	protected boolean isPlayerCloseForAttack(Player_old playerOld) {
		int absValue = (int) Math.abs(playerOld.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}

	// Changed the name from "checkEnemyHit" to checkPlayerHit
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player_old playerOld) {
		if (attackBox.intersects(playerOld.hitbox))
			playerOld.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;

	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;

				switch (enemyState) {
					case ATTACK, HIT -> enemyState = IDLE;
					case DEAD -> active = false;
				}
			}
		}
	}

	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;

	}

	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getEnemyState() {
		return enemyState;
	}

	public boolean isActive() {
		return active;
	}

}