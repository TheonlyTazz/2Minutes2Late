package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Entity {

	protected boolean debug = false;
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected void drawHitbox(Graphics g, int xLvlOffset, int yLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y - yLvlOffset, (int) hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}

	protected void setDebug(boolean debug) {
		this.debug = debug;
	}

	protected void showDebugInfo(Graphics g, int xOffset, int yOffset){
		if(!debug) return;

		ArrayList<String> debugInfo = new ArrayList<>();
		debugInfo.add("X: " + hitbox.x);
		debugInfo.add("Y: " + hitbox.y);
		debugInfo.add("Width: " + hitbox.width);
		debugInfo.add("Height: " + hitbox.height);
		debugInfo.add("Type: " + this.getClass().getSimpleName());
		int offset = 20;
		int totalHeight = debugInfo.size() * offset + offset;

		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, 100, totalHeight);
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, 100, totalHeight);

		g.setColor(Color.RED);
		for(String s : debugInfo){
			g.drawString(s, 20, offset);
			offset += 20;
		}


	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

}