package mobagame.core.game;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import mobagame.core.game.maps.MainMap;
import mobagame.launcher.GameScreen;
import org.w3c.dom.css.Rect;

public class InGamePlayer {
	private int playerID;
	private double x;
	private double y;
	private Character character;
	private int phyPow;
	private int abiPow;
	private int maxHealth;
	private int maxMana;
	private int speed;
	private int armor;
	//Need to fix these
	private Ability abiq;
	private Ability abiw;
	private Ability abie;
	private Ability abir;
	//
	private int magicResist;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;
	public Character getCharacter() {
		return character;
	}

	public Ability getAbiq() {
		return abiq;
	}

	public Ability getAbiw() {
		return abiw;
	}

	public Ability getAbie() {
		return abie;
	}


	public Ability getAbir() {
		return abir;
	}

	public void setCharacter(Character character) {
		this.character = character;
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
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public PlayerMover getMover() {
		return mover;
	}

	public void setMover(PlayerMover mover) {
		this.mover = mover;
	}

	public Item[][] getInventory() {
		return inventory;
	}

	public void setInventory(Item[][] inventory) {
		this.inventory = inventory;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	@SuppressWarnings("unused")
	private int level = 1;
	public PlayerMover mover;
	public Item[][] inventory = { { (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) },
			{ (GameScreen.empty), (GameScreen.empty), (GameScreen.empty), (GameScreen.empty) } };

	public InGamePlayer(Character chara) {
		maxHealth = chara.getMaxHealth();
		maxMana = chara.getMaxMana();
		phyPow = chara.getBasePhyPow();
		abiPow = chara.getBaseAbiPow();
		speed = chara.getSpeed();
		armor = chara.getBaseArmor();
		magicResist = chara.getBaseMagicResist();
		character = chara;
		currentHealth = maxHealth;
		currentMana = maxMana;
		abiq = chara.getAbiq();
		abiw = chara.getAbiw();
		abie = chara.getAbie();
		abir = chara.getAbir();
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

	public InGamePlayer(int playerID) {
		this.playerID = playerID;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	public void levelUp() {
		level = level + 1;
		maxHealth += character.getMaxHealthScale() * (level - 1);
		maxMana += character.getMaxManaScale() * (level - 1);
		abiPow += character.getAbiPowScale() * (level - 1);
		phyPow += character.getPhyPowScale() * (level - 1);
		armor += character.getArmorScale() * (level - 1);
		magicResist += character.getMagicResistScale() * (level - 1);
	}
	public void recieveDamage(Ability a) {
		if(a.getDamageType() == Ability.DamageType.PHYSICAL) {
			currentHealth = a.getDamage() - this.armor;
		}else {
			currentHealth = a.getDamage() - this.magicResist;
		}
	}


}
