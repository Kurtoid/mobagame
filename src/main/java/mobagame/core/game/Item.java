package mobagame.core.game;

import mobagame.launcher.GameScreen;

public class Item {
	private String name;
	private String imageLocation;
	private int price;
	private int[] effectPoints;
	private boolean isConsumable;
	private ItemType[] type;

	public Item(String name, String imageLocation, int price, ItemType[] type, int[] effectPoints,
			boolean isConsumable) {
		this.name = name;
		this.imageLocation = imageLocation;
		this.price = price;
		this.type = type;
		this.effectPoints = effectPoints;
		this.isConsumable = isConsumable;
	}

	public Item(String name, String imageLocation, int price, int effectPoints, boolean isConsumable) {
		this(name, imageLocation, price, makeItemTypeArray(ItemType.Health), makeIntArray(effectPoints), isConsumable);

		int[] effectPointsArray = { effectPoints };
	}

	private static ItemType[] makeItemTypeArray(ItemType type) {
		ItemType[] array = {type};
		return array;
	}

	private static int[] makeIntArray(int type) {
		int[] array = {type};
		return array;
	}
	
	public void buy(InGamePlayer user) {
		int gold = user.getGoldAmount();
		for (int y = 0; y < user.inventory.length; y++) {
			for (int x = 0; x < user.inventory[y].length; x++) {
				if (user.inventory[y][x] == GameScreen.empty) {
					if (gold >= price) {
						gold -= price;
						user.inventory[y][x] = this;
						if (!isConsumable) {
							for (int z = 0; z < type.length; z++) {
								switch (type[z]) {
								case Health:
									 user.setMaxHealth(user.getMaxHealth() + effectPoints[z]);
									break;
								case Mana:
									 user.setMaxMana(user.getMaxMana() + effectPoints[z]);
									break;
								case PhysicalPower:
									 user.setMaxHealth(user.getMaxHealth() + effectPoints[z]);
									break;
								case AbilityPower:
									 user.setPhyPow(user.getPhyPow() + effectPoints[z]);
									break;
								case Speed:
									 user.setSpeed(user.getSpeed() + effectPoints[z]);
									break;
								case AttackSpecial:
									 user.setAbiPow(user.getAbiPow() + effectPoints[z]);
									break;
								case Armor:
									 user.setArmor(user.getArmor() + effectPoints[z]);
									break;
								case MagicResistance:
									 user.setMagicResist(user.getMagicResist() + effectPoints[z]);
									break;
								default:
									System.out.println("ERROR: Unknown item type");
									break;
								}
							}
						}
						return;
					} else {
						System.out.println("Not enought gold to buy " + this.name);
					}
				}
			}
		}
		System.out.println("No space in inventory to buy " + this.name);
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Item use(InGamePlayer user) {
		if (isConsumable) {
			// do stuff
			for (int x = 0; x < type.length; x++) {
				switch (type[x]) {
				case Health:
					user.setCurrentHealth(user.getCurrentHealth() + effectPoints[x]);
					break;
				case Mana:
					user.setCurrentMana(user.getCurrentMana() + effectPoints[x]);
					break;
				default:
					System.out.println("ERROR: Not a valid consumable item");
					break;
				}
			}
			return null;
		}
		return this;
	}
}