package mobagame.core.game.characters;

import mobagame.core.game.Ability;
import mobagame.core.game.Ability.DamageType;
import mobagame.core.game.AbilityType;
import mobagame.core.game.Character;
import mobagame.core.game.Passive;
import mobagame.core.game.StatEffectType;
import mobagame.core.game.StatRatioType;

public class Jack extends Character{
public Jack() {
		super("Jack", "resources/Character/Jack.png", 65, 60, 20, 525, 30, 320, 7, 64, 0, 0, 5, 5, 7, 7, 3, 5, (1/0.73));
}
double speedChange;
double autoAttackCooldownChange;
Passive agileKiller = new Passive(this, 20, StatEffectType.StatRatio, StatRatioType.SpeedtoPhyPow);
Ability quickEscape = new Ability(AbilityType.Dash, DamageType.NULL, 40, 0, 0, 0, 0, 10, 1, 80){
	void use(){
		//Dash can move through characters
	}
};
Ability daggerThrow = new Ability(AbilityType.Damage, DamageType.PHYSICAL, 50, 50, 0, 80, 20, 6, 0, 85) {
	void use() {
		//It just creates a projectile in the direction of the cursor after the click
	}
};
Ability swiftAssault = new Ability(AbilityType.StatEffect, DamageType.NULL, 40, 0, 0, 0, 0, 12, 5, 0){
	void use(){
		if(!this.active) {
			activatedTime = System.currentTimeMillis();
			speedChange = getSpeed() * (((swiftAssault.getLevel() - 1) * 0.05) + 0.25);
			autoAttackCooldownChange = getAutoAttackCooldown() * ((swiftAssault.getLevel() * 0.05 + 0.35));
			setSpeed(getSpeed() + speedChange);
			setAutoAttackCooldown(getAutoAttackCooldown() + autoAttackCooldownChange);
			active = true;
		}else if(activatedTime + 5 <= System.currentTimeMillis()){
			setSpeed(getSpeed() - speedChange);
			setAutoAttackCooldown(getAutoAttackCooldown() + autoAttackCooldownChange);
		}
		
	}
};
Ability swiftEnd = new Ability(AbilityType.DamageDash, DamageType.PHYSICAL, 80, 300, 0, 100, 75, 40, 5/* dash lasts 1 seconds*/ , 100) {
	void use() {
		//Dashes lasts 1 second then after 4 seconds it teleports back to the starting position <--Dash can move through characters
	}
};
}