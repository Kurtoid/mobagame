package mobagame.core.game;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import mobagame.core.game.maps.MainMap;
import mobagame.launcher.GameScreen;

public class InGamePlayer {
	private int playerID;
	private double x;
	private double y;
	private double targetX;
	private double targetY;
	private Character character;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;
	public Item[][] inventory = { { (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) },
			{ (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) } };;

	private Shape playerShape;

	public InGamePlayer() {
		setDefaultShape();
	}
	
	private void setDefaultShape() {
		setPlayerShape(new Rectangle2D.Double(getX(), getY(), MainMap.normalizeWidth(20, 100), MainMap.normalizeHeight(20, 100)));
	}

	public int getGoldAmount() {
		return goldAmount;
	}

	public void setGoldAmount(int goldAmount) {
		this.goldAmount = goldAmount;
	}

	public Character getCharater() {
		return character;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	public InGamePlayer(Character character, int currentHealth, int currentMana) {
		this.character = character;
		this.currentHealth = currentHealth;
		this.currentMana = currentMana;
	}

	public double getTargetX() {
		return targetX;
	}

	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}

	public double getTargetY() {
		return targetY;
	}

	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}

	public Shape getPlayerShape() {
		return playerShape;
	}

	public void setPlayerShape(Shape playerShape) {
		this.playerShape = playerShape;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
