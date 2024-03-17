package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import entities.livingentities.Player_old;
import gamestates.Playing;

public class EnemyManager {

	private Playing playing;

	public EnemyManager(Playing playing) {
		this.playing = playing;

		addEnemies();
	}

	private void addEnemies() {
	}

	public void update(int[][] lvlData, Player_old playerOld) {

	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
	}

	public void resetAllEnemies() {

	}

}
