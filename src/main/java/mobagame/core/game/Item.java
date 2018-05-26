package mobagame.core.game;

import java.awt.Component;
import java.io.IOException;

import mobagame.core.networking.packets.RequestPlayerBuyItemPacket;
import mobagame.core.networking.packets.RequestPlayerSellItemPacket;
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

	private static ItemType[] makeItemTypeArray(ItemType type) {
		ItemType[] array = { type };
		return array;
	}

	private static int[] makeIntArray(int type) {
		int[] array = { type };
		return array;
	}

	public void buy(InGamePlayer user) {
		// buy logic is in server.ResponseWorker
		RequestPlayerBuyItemPacket pkt = new RequestPlayerBuyItemPacket();
		pkt.itemID = GameItems.allGameItemsLookup.indexOf(this);
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sell(InGamePlayer user) {
		RequestPlayerSellItemPacket pkt = new RequestPlayerSellItemPacket();
		pkt.itemID = GameItems.allGameItemsLookup.indexOf(this);
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public boolean use(InGamePlayer user) {
		if (isConsumable) {
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
			return true;
		}
		return false;
	}

	public String toString() {
		return name + ": " + "$" + price;
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