package mobagame.core.game;

public class Passive {
	private PassiveType pass;
	private StatEffectType SET;
	private StatRatioType SRT;
	private int statRatio;

7	public Passive() {
		if (pass == PassiveType.StatEffect) {
			switch (SET) {
			case StatRatio:
				switch (SRT) {
				case MaxHealthtoMaxMana:
					//MaxMana = MaxManaBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case MaxManatoMaxHealth:
					//MaxHealth = MaxHealthBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case MaxHealthtoPhyPow:
					//PhyPow = PhyPowBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case PhyPowtoMaxHealth:
					//MaxHealth = MaxHealthBeforePassive(includes items) + (PhyPow * statRatio);
					break;
				case AbiPowtoMaxMana:
					//MaxMana = MaxManaBeforePassive(includes items) + (AbiPow * statRatio);
					break;
				case MaxManatoAbiPow:
					//AbiPow = AbiPowBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case PhyPowtoMaxMana:
					//MaxMana = MaxManaBeforePassive(includes items) + (PhyPow * statRatio);
					break;
				case MaxManatoPhyPow:
					//PhyPow = PhyPowBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case MaxHealthtoAbiPow:
					//AbiPow = AbiPowBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case AbiPowtoMaxHealth:
					//MaxHealth = MaxHealthBeforePassive(includes items) + (AbiPow * statRatio);
					break;
				default:
					break;
				}
				break;
			case BonusArmor:
				break;
			case BonusMagicResist:
				break;
			case BonusMaxHealth:
				break;
			case BonusPhyPow:
				break;
			case BonusAbiPow:
				break;
			default:
				break;
			}
		} else {

		}
	}
}
