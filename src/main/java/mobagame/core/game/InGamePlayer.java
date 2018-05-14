package mobagame.core.game;

import mobagame.launcher.GameScreen;

public class InGamePlayer {
	private int playerID;
	private double x;
	private double y;
	private Character character;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;
	public Item[][] inventory = { { (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) },
			{ (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) } };;

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
}
