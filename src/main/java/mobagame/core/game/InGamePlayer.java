package mobagame.core.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mobagame.core.game.maps.MainMap;
import mobagame.launcher.Shop;
import mobagame.launcher.MyCanvas;
import mobagame.launcher.GameScreen;

public class InGamePlayer {

	private int playerID;
	private double yPos;
	private double xPos;
	private Character character;
	private int phyPow;
	private int abiPow;
	private int maxHealth;
	private int maxMana;
	private int speed;
	private int armor;
	private int magicResist;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;
	private int playerLevel = 1;
	public PlayerMover mover;

	
	//0 = Q, 1 = W, 2 = E, 3 = R
	public int[] abilityLevels = {25, 25, 25, 25};

	public Item[] inventory = {  (GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty) ,
			 (GameItems.empty), (GameItems.empty), (GameItems.empty), (GameItems.empty) };

	public Character getCharacter() {
		return character;
	}
	
	private void setCharacter(Character character) {
		this.character = character;
		this.currentHealth = maxHealth = character.getBaseMaxHealth();
		this.currentMana = maxMana = character.getBaseMaxMana();
		this.phyPow = character.getBasePhyPow();
		this.abiPow = character.getBaseAbiPow();
		this.speed = character.getSpeed();
		this.armor = character.getBaseArmor();
		this.magicResist = character.getBaseMagicResist();
	}

	public int getPhyPow() {
		return phyPow;
	}

	public void setPhyPow(int phyPow) {
		this.phyPow = phyPow;
	}

	public int getAbiPow() {
		return abiPow;
	}

	public void setAbiPow(int abiPow) {
		this.abiPow = abiPow;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getMagicResist() {
		return magicResist;
	}

	public void setMagicResist(int magicResist) {
		this.magicResist = magicResist;
	}

	public int getLevel() {
		return playerLevel;
	}

	public void setLevel(int level) {
		this.playerLevel = level;
	}

	public PlayerMover getMover() {
		return mover;
	}

	public void setMover(PlayerMover mover) {
		this.mover = mover;
	}

	public Item[] getInventory() {
		return inventory;
	}

	public void setItem(int index, Item item) {
		inventory[index] = item;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public InGamePlayer() {
		speed = 100;
	}
	
	public InGamePlayer(int playerID, Character chara) {
		this.playerID = playerID;
		this.setCharacter(chara);
	}

	public int getGoldAmount() {
		return goldAmount;
	}

	public void setGoldAmount(int goldAmount) {
		this.goldAmount = goldAmount;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public int getPlayerID() {
		return playerID;
	}

	@Override
	public String toString() {
		return "phyPow = " + phyPow + ", abiPow = " + abiPow + ", maxHealth = " + maxHealth + ", maxMana = " + maxMana
				+ ", speed = " + speed + ", armor = " + armor + ", magicResist = " + magicResist + ", currentHealth = "
				+ currentHealth + ", currentMana = " + currentMana;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	public InGamePlayer(Character character, int currentHealth, int currentMana) {
		this.setCharacter(character);
		this.currentHealth = currentHealth;
		this.currentMana = currentMana;
	}

	public void setX(double x) {
		this.xPos = x;
	}

	public void setY(double y) {
		this.yPos = y;
	}

	public void levelUp() {
		maxHealth += character.getMaxHealthScale() * (playerLevel);
		maxMana += character.getMaxManaScale() * (playerLevel);
		abiPow += character.getAbiPowScale() * (playerLevel);
		phyPow += character.getPhyPowScale() * (playerLevel);
		armor += character.getArmorScale() * (playerLevel);
		magicResist += character.getMagicResistScale() * (playerLevel);
		this.abilityUpgrade();
		playerLevel++;
	}

	private void abilityUpgrade() {
		// TODO Auto-generated method stub
		
	}

	public void recieveDamage(Ability a) {
		if (a.getDamageType() == Ability.DamageType.PHYSICAL) {
			currentHealth = a.getDamage() - this.armor;
		} else {
			currentHealth = a.getDamage() - this.magicResist;
		}
	}

	public int getAbiLevel(Ability ability) {
		//Need to finish, it is to help with baseAbiDamage
		return 0;
	}
}
