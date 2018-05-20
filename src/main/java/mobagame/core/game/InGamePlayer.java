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

public class InGamePlayer extends JFrame {

	public final Dimension SCREEN_SIZE = getToolkit().getScreenSize();

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
	// Need to fix these
	private Ability abiq;
	private Ability abiw;
	private Ability abie;
	private Ability abir;
	//
	private int magicResist;
	private int currentHealth;
	private int currentMana;
	private int goldAmount = 0;

	public Item[][] inventory = { { (Shop.empty), (Shop.empty), (Shop.empty), (Shop.empty) },
			{ (Shop.empty), (Shop.empty), (Shop.empty), (Shop.empty) } };
	private MyCanvas[][] inventoryCanvases = {
			{ new MyCanvas(inventory[0][0].getImageLocation(), SCREEN_SIZE.width / 40),
					new MyCanvas(inventory[0][1].getImageLocation(), SCREEN_SIZE.width / 40),
					new MyCanvas(inventory[0][2].getImageLocation(), SCREEN_SIZE.width / 40),
					new MyCanvas(inventory[0][3].getImageLocation(), SCREEN_SIZE.width / 40) },
			{ new MyCanvas(inventory[1][0].getImageLocation(), SCREEN_SIZE.width / 40),
						new MyCanvas(inventory[1][1].getImageLocation(), SCREEN_SIZE.width / 40),
						new MyCanvas(inventory[1][2].getImageLocation(), SCREEN_SIZE.width / 40),
						new MyCanvas(inventory[1][3].getImageLocation(), SCREEN_SIZE.width / 40) } };

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

	private Shape playerShape;

	public InGamePlayer(Character chara) {
		setDefaultShape();
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

	private void setDefaultShape() {
		setPlayerShape(new Rectangle2D.Double(getX(), getY(), MainMap.normalizeWidth(20, 100),
				MainMap.normalizeHeight(20, 100)));
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
		return (int) x;
	}

	public int getY() {
		return (int) y;
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
		} else {
			currentHealth = a.getDamage() - this.magicResist;
		}
	}
	
	public void setInventory(JPanel inventory) {
		GridBagConstraints c = new GridBagConstraints();
		for (int y = 0; y < this.inventory.length; y++) {
			for (int x = 0; x < this.inventory[y].length; x++) {
				c.gridy = y;
				c.gridx = x;
				inventory.add(inventoryCanvases[y][x], c);
			}
		}
		System.out.println("Info: Inventory set");
	}

	public void refreshInventory(JPanel inventory) {
		for (int y = 0; y < this.inventory.length; y++) {
			for (int x = 0; x < this.inventory[y].length; x++) {
				inventoryCanvases[y][x].setImageLocation(this.inventory[y][x].getImageLocation());;
			}
		}
		inventory.repaint();
		System.out.println("Info: Inventory repainted");
	}

}
