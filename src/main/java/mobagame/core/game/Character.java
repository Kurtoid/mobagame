package mobagame.core.game;

public class Character {

	private String charName;
	private int maxHealth;
	private int maxMana;

	// abilitys
	private Ability[] abilitys = new Ability[5];

	private int range; // Measured in custom units ru
	private int phyPow;
	private int abiPow;
	private int phyPowScale;
	private int abiPowScale;

	private String imageLocation;

	// testing item
	public Character(int maxHealth, int maxMana) {
		this.maxHealth = maxHealth;
		this.maxMana = maxMana;
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

	// make array 0 = Passive, 1 = Q, 2 = W, 3 = E, 4 = R
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
		return phyPow;
	}

	public int getAbiPow() {
		return abiPow;
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

}
