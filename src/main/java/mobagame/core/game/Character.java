package mobagame.core.game;

import java.awt.geom.Rectangle2D;

public class Character {

	private String charName;
	private int maxHealth;
	private int maxMana;

	// abilitys
	// 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
	private Ability[] abilitys = { new Ability(), new Ability(), new Ability(), new Ability(), new Ability() };

	private int range; // Measured in custom units ru
	private int basePhyPow;
	private int baseAbiPow;
	private int phyPowScale;
	private int abiPowScale;
	private int speed;
	private int baseArmor;
	private int armorScale;
	private int baseMagicResist;
	private int magicResistScale;
	private int maxHealthScale;
	private int maxManaScale;
	// Need to fix these
	private Ability abiq = new Ability();
	private Ability abiw = new Ability();
	private Ability abie = new Ability();
	private Ability abir = new Ability();
	public int width = 10;
	public int height = 10;

	public int getMaxHealthScale() {
		return maxHealthScale;
	}

	public void setMaxHealthScale(int maxHealthScale) {
		this.maxHealthScale = maxHealthScale;
	}

	public int getMaxManaScale() {
		return maxManaScale;
	}

	public void setMaxManaScale(int maxManaScale) {
		this.maxManaScale = maxManaScale;
	}

	public int getBasePhyPow() {
		return basePhyPow;
	}

	public void setBasePhyPow(int basePhyPow) {
		this.basePhyPow = basePhyPow;
	}

	public int getBaseAbiPow() {
		return baseAbiPow;
	}

	public void setBaseAbiPow(int baseAbiPow) {
		this.baseAbiPow = baseAbiPow;
	}

	public int getBaseArmor() {
		return baseArmor;
	}

	public Ability getAbiq() {
		return abiq;
	}

	public void setAbiq(Ability abiq) {
		this.abiq = abiq;
	}

	public Ability getAbiw() {
		return abiw;
	}

	public void setAbiw(Ability abiw) {
		this.abiw = abiw;
	}

	public Ability getAbie() {
		return abie;
	}

	public void setAbie(Ability abie) {
		this.abie = abie;
	}

	public Ability getAbir() {
		return abir;
	}

	public void setAbir(Ability abir) {
		this.abir = abir;
	}

	public void setBaseArmor(int baseArmor) {
		this.baseArmor = baseArmor;
	}

	public int getArmorScale() {
		return armorScale;
	}

	public void setArmorScale(int armorScale) {
		this.armorScale = armorScale;
	}

	public int getBaseMagicResist() {
		return baseMagicResist;
	}

	public void setBaseMagicResist(int baseMagicResist) {
		this.baseMagicResist = baseMagicResist;
	}

	public int getMagicResistScale() {
		return magicResistScale;
	}

	public void setMagicResistScale(int magicResistScale) {
		this.magicResistScale = magicResistScale;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public void setAbilitys(Ability[] abilitys) {
		this.abilitys = abilitys;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setPhyPowScale(int phyPowScale) {
		this.phyPowScale = phyPowScale;
	}

	public void setAbiPowScale(int abiPowScale) {
		this.abiPowScale = abiPowScale;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	private String imageLocation;

	// testing
	public Character(int maxHealth, int maxMana) {
		this.maxHealth = maxHealth;
		this.maxMana = maxMana;
	}

	public Character(String imageLocation) {
		for (int x = 0; x < abilitys.length; x++) {
			abilitys[x] = new Ability();
		}
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

	public String getCharName() {
		return charName;
	}

	// 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
	public Ability[] getAbilitys() {
		return abilitys;
	}

	public Ability getAbilityAt(int index) {
		if (index >= 0 || index < 5) {
			return abilitys[index];
		}
		return null;
	}

	public int getRange() {
		return range;
	}

	public int getPhyPow() {
		return basePhyPow;
	}

	public int getAbiPow() {
		return baseAbiPow;
	}

	public int getPhyPowScale() {
		return phyPowScale;
	}

	public int getAbiPowScale() {
		return abiPowScale;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
