package mobagame.core.game;

import mobagame.launcher.GameScreen;

public class Item {
	private String name;
	private String imageLocation;
	private int price;
	private int[] effectPoints;
	private boolean isConsumable;
	private ItemType[] type;

	public Item(String name, String imageLocation, int price,ItemType[] type, int[] effectPoints, boolean isConsumable) {
		this.name = name;
		this.imageLocation = imageLocation;
		this.price = price;
		this.type = type;
		this.effectPoints = effectPoints;
		this.isConsumable = isConsumable;
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
//									user.setMaxHealth(user.getMaxHealth() + effectPoints[z]);
									break;
								case Mana:
									break;
								case PhysicalPower:
									break;
								case AbilityPower:
									break;
								case Speed:
									break;
								case Attack:
									break;
								case AttackSpecial:
									break;
								case Armor:
									break;
								case MagicResistance:
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
			user.setCurrentHealth(user.getCurrentHealth() + effectPoints);
			user.setCurrentMana(user.getCurrentMana() + effectPoints);
			return null;
		}
		return this;
	}
}