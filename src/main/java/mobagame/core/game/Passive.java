package mobagame.core.game;

public class Passive extends Ability {
	private PassiveType pass;
	private StatEffectType SET;
	private StatRatioType SRT;
	private int statRatio;

	private int phyPowBeforePassive;
	private int abiPowBeforePassive;
	private int maxHealthBeforePassive;
	private int maxManaBeforePassive;
	private int armorBeforePassive;
	private int magicResistBeforePassive;

	public Passive(Character player) {
		phyPowBeforePassive = player.getBasePhyPow();
		abiPowBeforePassive = player.getBaseAbiPow();
		maxHealthBeforePassive = player.getBaseMaxHealth();
		maxManaBeforePassive = player.getBaseMaxMana();
		armorBeforePassive = player.getBaseArmor();
		magicResistBeforePassive = player.getBaseMagicResist();
	}
	public void preparePassiveUpdate(InGamePlayer player) {
		if (pass == PassiveType.StatEffect) {
			switch (SET) {
				case StatRatio:
					switch (SRT) {
						case MaxHealthtoMaxMana:
							maxManaBeforePassive =  player.getMaxMana() - (player.getMaxHealth() * statRatio);
							break;
						case MaxManatoMaxHealth:
							maxHealthBeforePassive = player.getMaxHealth() + (player.getMaxMana() * statRatio);
							break;
						case MaxHealthtoPhyPow:
							phyPowBeforePassive =  player.getPhyPow() - (player.getMaxHealth() * statRatio);
							break;
						case PhyPowtoMaxHealth:
							maxHealthBeforePassive = player.getMaxHealth() - (player.getPhyPow() * statRatio);
							break;
						case AbiPowtoMaxMana:
							maxManaBeforePassive = player.getMaxMana() - (player.getAbiPow() * statRatio);
							break;
						case MaxManatoAbiPow:
							abiPowBeforePassive = player.getAbiPow() + (player.getMaxMana() * statRatio);
							break;
						case PhyPowtoMaxMana:
							maxManaBeforePassive = player.getMaxMana() - (player.getPhyPow() * statRatio);
							break;
						case MaxManatoPhyPow:
							phyPowBeforePassive = player.getPhyPow() - (player.getMaxMana() * statRatio);
							break;
						case MaxHealthtoAbiPow:
							abiPowBeforePassive = player.getAbiPow() + (player.getMaxHealth() * statRatio);
							break;
						case AbiPowtoMaxHealth:
							maxHealthBeforePassive = player.getMaxHealth() - (player.getAbiPow() * statRatio);
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
					break;
				case BonusArmor:
					armorBeforePassive = player.getArmor() - (player.getArmor() * statRatio);
					break;
				case BonusMagicResist:
					magicResistBeforePassive = player.getMagicResist() - (player.getMagicResist() * statRatio);
					break;
				case BonusMaxHealth:
					maxHealthBeforePassive = player.getMaxHealth() - (player.getMaxHealth() * statRatio);
					break;
				case BonusPhyPow:
					phyPowBeforePassive = player.getPhyPow() - (player.getPhyPow() * statRatio);
					break;
				case BonusAbiPow:
					abiPowBeforePassive = player.getAbiPow() - (player.getAbiPow() * statRatio);
					break;
				case BonusMaxMana:
					maxManaBeforePassive = player.getMaxMana() - (player.getMaxMana() * statRatio);
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
	public void update(InGamePlayer player){
		if (pass == PassiveType.StatEffect) {
			switch (SET) {
				case StatRatio:
					switch (SRT) {
						case MaxHealthtoMaxMana:
							player.setMaxMana(maxManaBeforePassive + (player.getMaxHealth() * statRatio)); 
							break;
						case MaxManatoMaxHealth:
							player.setMaxHealth(maxHealthBeforePassive + (player.getMaxMana() * statRatio));
							break;
						case MaxHealthtoPhyPow:
							player.setPhyPow(phyPowBeforePassive + (player.getMaxHealth() * statRatio));
							break;
						case PhyPowtoMaxHealth:
							player.setMaxHealth(maxHealthBeforePassive + (player.getPhyPow() * statRatio));
							break;
						case AbiPowtoMaxMana:
							player.setMaxMana(maxManaBeforePassive + (player.getAbiPow() * statRatio));
							break;
						case MaxManatoAbiPow:
							player.setAbiPow(abiPowBeforePassive + (player.getMaxMana() * statRatio));
							break;
						case PhyPowtoMaxMana:
							player.setMaxMana(maxManaBeforePassive + (player.getPhyPow() * statRatio));
							break;
						case MaxManatoPhyPow:
							player.setPhyPow(phyPowBeforePassive + (player.getMaxMana() * statRatio));
							break;
						case MaxHealthtoAbiPow:
							player.setAbiPow(abiPowBeforePassive + (player.getMaxHealth() * statRatio));
							break;
						case AbiPowtoMaxHealth:
							player.setMaxHealth(maxHealthBeforePassive + (player.getAbiPow() * statRatio));
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
					break;
				case BonusArmor:
					player.setArmor(player.getArmor() + (armorBeforePassive * statRatio));
					break;
				case BonusMagicResist:
					player.setMagicResist(player.getMagicResist() + (magicResistBeforePassive * statRatio));
					break;
				case BonusMaxHealth:
					player.setMaxHealth(player.getMaxHealth() + (maxHealthBeforePassive * statRatio));
					break;
				case BonusPhyPow:
					player.setPhyPow(player.getPhyPow() + (phyPowBeforePassive * statRatio));
					break;
				case BonusAbiPow:
					player.setAbiPow(player.getAbiPow() + (abiPowBeforePassive * statRatio));
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
