package mobagame.core.game;

public class Ability {
	public enum DamageType {
		PHYSICAL, MAGICAL
	};

	private String abiName;
	private int manaCost;
	private int baseDamage;
	private int abiPowRatio;
	private int phyPowRatio;
	private int damScale;

	public Ability() {
		imageLocation = "resources/Black.png";
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

//private AbilityType abiType;
	private int range;
	private String imageLocation;
	private DamageType damageType;
	private InGamePlayer caster;

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
		return (this.baseDamage + (this.damScale + caster.getAbiLevel(this)));
	}
}
