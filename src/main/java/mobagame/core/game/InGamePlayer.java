package mobagame.core.game;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import mobagame.core.game.maps.MainMap;
import mobagame.launcher.GameScreen;

public class InGamePlayer {
	private int playerID;
	private int x;
	private int y;
	private Character character;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;
	public PlayerMover mover;
	public Item[][] inventory = { { (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) },
			{ (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) } };

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

	public int getX() {
		return x;
	}

	public int getY() {
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

	public InGamePlayer(int playerID) {
		this.playerID = playerID;
	}

	public Shape getPlayerShape() {
		return playerShape;
	}

	public void setPlayerShape(Shape playerShape) {
		this.playerShape = playerShape;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}


}
