package mobagame.core.game;

import java.awt.Component;
import java.io.IOException;

import mobagame.core.networking.packets.RequestPlayerBuyItemPacket;
import mobagame.launcher.GameScreen;
import mobagame.launcher.MobaGameLauncher;
import mobagame.launcher.MyCanvas;
import mobagame.launcher.Shop;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

public class Item implements MobaGameLauncher {
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

	public Item(String name, String imageLocation, int price, ItemType type, int effectPoints, boolean isConsumable) {
		this(name, imageLocation, price, makeItemTypeArray(type), makeIntArray(effectPoints), isConsumable);
	}

	public Item(String name, String imageLocation, int price, int effectPoints, boolean isConsumable) {
		this(name, imageLocation, price, makeItemTypeArray(ItemType.Health), makeIntArray(effectPoints), isConsumable);
	}

	private static ItemType[] makeItemTypeArray(ItemType type) {
		ItemType[] array = { type };
		return array;
	}

	private static int[] makeIntArray(int type) {
		int[] array = { type };
		return array;
	}

	public void buy(InGamePlayer user) {
		for (int y = 0; y < user.inventory.length; y++) {
			for (int x = 0; x < user.inventory[y].length; x++) {
				if (user.inventory[y][x] == GameItems.empty) {
					if (user.getGoldAmount() >= price) {
						user.setGoldAmount(user.getGoldAmount() - price);
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
						System.out.println("You bought a " + this.name);
						return;
					} else {
						System.out.println("Not enought gold to buy " + this.name + "\n\t" + "You need "
								+ (price - user.getGoldAmount()) + " more gold");
						return;
					}
				}
			}
			RequestPlayerBuyItemPacket pkt = new RequestPlayerBuyItemPacket();
			pkt.itemID = GameItems.allGameItemsLookup.indexOf(this);
			try {
				ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sell(InGamePlayer user) {
		for (int y = 0; y < user.inventory.length; y++) {
			for (int x = 0; x < user.inventory[y].length; x++) {
				if (user.inventory[y][x] == this) {
					if (user.inventory[y][x] != GameItems.empty) {
						user.setGoldAmount(user.getGoldAmount() + price);
						user.inventory[y][x] = GameItems.empty;
						if (!isConsumable) {
							for (int z = 0; z < type.length; z++) {
								switch (type[z]) {
								case Health:
									user.setMaxHealth(user.getMaxHealth() - effectPoints[z]);
									break;
								case Mana:
									user.setMaxMana(user.getMaxMana() - effectPoints[z]);
									break;
								case PhysicalPower:
									user.setMaxHealth(user.getMaxHealth() - effectPoints[z]);
									break;
								case AbilityPower:
									user.setPhyPow(user.getPhyPow() - effectPoints[z]);
									break;
								case Speed:
									user.setSpeed(user.getSpeed() - effectPoints[z]);
									break;
								case AttackSpecial:
									user.setAbiPow(user.getAbiPow() - effectPoints[z]);
									break;
								case Armor:
									user.setArmor(user.getArmor() - effectPoints[z]);
									break;
								case MagicResistance:
									user.setMagicResist(user.getMagicResist() - effectPoints[z]);
									break;
								default:
									System.out.println("ERROR: Unknown item type");
									break;
								}
							}
						}
						System.out.println("You sold a " + this.name);
						return;
					} else {
						System.out.println("You cannot sell nothing");
						return;
					}
				}
			}
		}
		System.out.println("Error: Item not in inventory");
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Item use(InGamePlayer user) {
		if (isConsumable) {
			// do stuff
			for (int x = 0; x < getType().length; x++) {
				switch (getType()[x]) {
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
			return GameItems.empty;
		}
		return this;
	}

	public String toString() {
		return name + ": " + "$" + price;
	}

	public static void main(String[] args) {
		Item knife = new Item("knife", "resources/Items/knife.png", 500, 10, false);
		InGamePlayer user = new InGamePlayer(0);
		user.setGoldAmount(100);
		knife.buy(user);
		user.setGoldAmount(500);
		knife.buy(user);
	}

	public boolean isConsumable() {
		return isConsumable;
	}

	public ItemType[] getType() {
		return type;
	}

	public int[] getEffectPoints() {
		return effectPoints;
	}

}