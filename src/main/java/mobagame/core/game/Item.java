package mobagame.core.game;

import java.awt.Component;
import java.io.IOException;

import mobagame.core.networking.packets.RequestPlayerBuyItemPacket;
import mobagame.launcher.GameScreen;
import mobagame.launcher.MyCanvas;
import mobagame.launcher.Shop;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

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
		this.type =type;
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
		RequestPlayerBuyItemPacket pkt = new RequestPlayerBuyItemPacket();
		pkt.itemID = GameItems.allGameItemsLookup.indexOf(this);
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		// String temp = "Effects";
		// for (int x = 0; x < type.length; x++) {
		// temp += "\n\t +" + effectPoints[x] + "" + type[x];
		// }
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