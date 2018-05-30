package mobagame.core.game;

public class Character {

	private String name;
	private String imageLocation;
	private int range; // Measured in custom units ru
	private double speed; // Measured in

	// 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
	private final Ability[] abilities = { new Passive(this), new Ability(), new Ability(), new Ability(), new Ability() };

	private int maxHealthScale;
	private int baseMaxHealth;

	private int maxManaScale;
	private int baseMaxMana;

	private int basePhyPow;
	private int phyPowScale;

	private int baseAbiPow;
	private int abiPowScale;

	private int baseArmor;
	private int armorScale;

	private int baseMagicResist;
	private int magicResistScale;

	private double autoAttackCooldown = 1000;

	public int width = 10;
	public int height = 10;

	public Character(String name, String imageLocation, int range, int speed, int maxHealthScale, int baseMaxHealth,
			int maxManaScale, int baseMaxMana, int phyPowScale, int basePhyPow, int abiPowScale, int baseAbiPow,
			int armorScale, int baseArmor, int magicResistScale, int baseMagicResist, double autoAttackCooldown) {
		this.name = name;
		this.imageLocation = imageLocation;
		this.range = range;
		this.speed = speed;
		this.maxHealthScale = maxHealthScale;
		this.baseMaxHealth = baseMaxHealth;
		this.maxManaScale = maxManaScale;
		this.baseMaxMana = baseMaxMana;
		this.basePhyPow = basePhyPow;
		this.phyPowScale = phyPowScale;
		this.baseAbiPow = baseAbiPow;
		this.abiPowScale = abiPowScale;
		this.baseArmor = baseArmor;
		this.armorScale = armorScale;
		this.baseMagicResist = baseMagicResist;
		this.magicResistScale = magicResistScale;
	}

	public int getBaseMaxHealth() {
		return baseMaxHealth;
	}

	public int getMaxHealthScale() {
		return maxHealthScale;
	}

	public int getMaxManaScale() {
		return maxManaScale;
	}

	public int getPhyPowScale() {
		return phyPowScale;
	}

	public int getAbiPowScale() {
		return abiPowScale;
	}

	public int getBaseMaxMana() {
		return baseMaxMana;
	}

	public int getBasePhyPow() {
		return basePhyPow;
	}

	public int getBaseAbiPow() {
		return baseAbiPow;
	}

	public int getBaseArmor() {
		return baseArmor;
	}

	public int getArmorScale() {
		return armorScale;
	}

	public int getBaseMagicResist() {
		return baseMagicResist;
	}

	public int getMagicResistScale() {
		return magicResistScale;
	}

	public String getCharName() {
		return name;
	}

	// 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
	public Ability[] getAbilities() {
		return abilities;
	}

	public int getRange() {
		return range;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public double getSpeed() {
		return speed;
	}

	// 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
	public Passive getPass() {
		return (Passive) abilities[0];
	}

	public Ability getAbiq() {
		return abilities[1];
	}

	public Ability getAbiw() {
		return abilities[2];
	}

	public Ability getAbie() {
		return abilities[3];
	}

	public Ability getAbir() {
		return abilities[4];
	}

	// testing
	public Character(int maxHealth, int maxMana) {
		this.baseMaxHealth = maxHealth;
		this.baseMaxMana = maxMana;
	}

	public Character(String imageLocation) {
		for (int x = 0; x < abilities.length; x++) {
			abilities[x] = new Ability();
		}
	}

	public double getAutoAttackCooldown() {
		return autoAttackCooldown;
	}

	public void setAutoAttackCooldown(double autoAttackCooldown) {
		this.autoAttackCooldown = autoAttackCooldown;
	}
}
