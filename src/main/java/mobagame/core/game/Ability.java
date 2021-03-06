package mobagame.core.game;

public class Ability {
	public int getLevel() {
		// TODO: implement me!
		return 0;
	}

	public enum DamageType {
		PHYSICAL, MAGICAL, NULL
	};
	private String abiName;
	private int manaCost;
	private int baseDamage;
	private int abiPowRatio;
	private int phyPowRatio;
	private int damScale;
	private int cooldown;
	private int duration;
	private DamageType dT;
	private AbilityType aT;
	//private AbilityType abiType;
		private int range;
		private String imageLocation;
		private DamageType damageType;
		private InGamePlayer caster;
public boolean active = false;
public long activatedTime;
	public Ability() {
		imageLocation = "resources/Black.png";
	}
	public Ability(AbilityType AT, DamageType DT, int newManaCost, int newBaseDamage, int newAbiPowRatio, int newPhyPowRatio, int newDamScale, int newCooldown, int newDuration, int newRange) {
		aT = AT;
		dT = DT;
		manaCost = newManaCost;
		baseDamage = newBaseDamage;
		abiPowRatio = newAbiPowRatio;
		phyPowRatio = newPhyPowRatio;
		damScale = newDamScale;
		cooldown = newCooldown;
		duration = newDuration;
		range = newRange;
	}

	public int getDamScale() {
		return damScale;
	}

	public void setDamScale(int damScale) {
		this.damScale = damScale;
	}

	public InGamePlayer getCaster() {
		return caster;
	}

	public void setCaster(InGamePlayer caster) {
		this.caster = caster;
	}



	public String getAbiName() {
		return abiName;
	}

	public void setAbiName(String abiName) {
		this.abiName = abiName;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public int getAbiPowRatio() {
		return abiPowRatio;
	}

	public void setAbiPowRatio(int abiPowRatio) {
		this.abiPowRatio = abiPowRatio;
	}

	public int getPhyPowRatio() {
		return phyPowRatio;
	}

	public void setPhyPowRatio(int phyPowRatio) {
		this.phyPowRatio = phyPowRatio;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public int getDamage() {
		int damage;
		if (this.damageType == DamageType.PHYSICAL) {
			damage = caster.getPhyPow() * this.phyPowRatio + this.getBaseDamageBefRat();
		} else {
			damage = caster.getAbiPow() * this.abiPowRatio + this.getBaseDamageBefRat();
		}
		return damage;
	}

	public int getBaseDamageBefRat() {
        for (int x = 1; x < caster.getCharacter().getAbilities().length; x++) {
            if (caster.getCharacter().getAbilities()[x] == this) {
                return (this.baseDamage + (this.damScale + caster.getAbiLevel(x - 1)));
            }
        }
        return -1;
    }
}
