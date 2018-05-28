package mobagame.core.game;

public class Passive extends Ability {
	private PassiveType pass;
	private StatEffectType SET;
	private StatRatioType SRT;
	private int statRatio;

	public Passive() {
		if (pass == PassiveType.StatEffect) {
			switch (SET) {
			case StatRatio:
				switch (SRT) {
				case MaxHealthtoMaxMana:
					// MaxMana = MaxManaBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case MaxManatoMaxHealth:
					// MaxHealth = MaxHealthBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case MaxHealthtoPhyPow:
					// PhyPow = PhyPowBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case PhyPowtoMaxHealth:
					// MaxHealth = MaxHealthBeforePassive(includes items) + (PhyPow * statRatio);
					break;
				case AbiPowtoMaxMana:
					// MaxMana = MaxManaBeforePassive(includes items) + (AbiPow * statRatio);
					break;
				case MaxManatoAbiPow:
					// AbiPow = AbiPowBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case PhyPowtoMaxMana:
					// MaxMana = MaxManaBeforePassive(includes items) + (PhyPow * statRatio);
					break;
				case MaxManatoPhyPow:
					// PhyPow = PhyPowBeforePassive(includes items) + (MaxMana * statRatio);
					break;
				case MaxHealthtoAbiPow:
					// AbiPow = AbiPowBeforePassive(includes items) + (MaxHealth * statRatio);
					break;
				case AbiPowtoMaxHealth:
					// MaxHealth = MaxHealthBeforePassive(includes items) + (AbiPow * statRatio);
					break;
				default:
					break;
				}
				break;
			case BonusArmor:
				// armor += ArmorBeforePassive(includes items) * statRatio;
				break;
			case BonusMagicResist:
				// magicResist += magicResistBeforePassive(includes items) * statRatio;
				break;
			case BonusMaxHealth:
				// MaxHealth += MaxHealthBeforePassive(includes items) * statRatio;
				break;
			case BonusPhyPow:
				// phyPow += phyPowBeforePassive(includes items) * statRatio;
				break;
			case BonusAbiPow:
<<<<<<< HEAD
					//abiPow += abiPowBeforePassive(includes items) * statRatio;
=======
				// abiPow += abiPowBeforePassive(includes items) * statRatio;
>>>>>>> branch 'master' of https://WeedMaster27@bitbucket.org/mmwjuniorproject2018/mobagame.git
				break;
			default:
				System.out.println("Something is Wrong");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
				System.exit(0);
				break;
			}
		} else {

		}
	}
}
