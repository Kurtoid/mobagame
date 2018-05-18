package mobagame.core.game;

public class Ability {
private String abiName;
private int manaCost;
private int baseDamage;
private int abiPowRatio;
private int phyPowRatio;
private int damScale;
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
private String damageType;
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
public String getDamageType() {
	return damageType;
}
public void setDamageType(String damageType) {
	this.damageType = damageType;
}
public int getDamage() {
	int damage;
	if(this.damageType == "Physical") {
		damage = caster.getPhyPow() * this.phyPowRatio + this.baseDamage;
	}else{
		damage = caster.getAbiPow() * this.abiPowRatio + this.baseDamage;
	}
	return damage;
}
}
