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
		super("Jack", "resources/Character/Jack.png", 65, 60, 20, 525, 30, 320, 7, 64, 0, 0, 5, 5, 7, 7);
}
Passive agileKiller = new Passive(this, 20, StatEffectType.StatRatio, StatRatioType.SpeedtoPhyPow);
Ability quickEscape = new Ability(AbilityType.Dash, DamageType.NULL, 40, 0, 0, 0, 0, 10, 1, 80);
Ability daggerThrow = new Ability(AbilityType.Damage, DamageType.PHYSICAL, 50, 50, 0, 80, 20, 6, 0, 85);
Ability swiftAssault = new Ability(AbilityType.StatEffect, DamageType.NULL, 40, 0, 0, 0, 0, 12, 5, 0);
Ability swiftEnd = new Ability(AbilityType.DamageDash, DamageType.PHYSICAL, 80, 300, 0, 100, 75, 40, 3, 100);
}
