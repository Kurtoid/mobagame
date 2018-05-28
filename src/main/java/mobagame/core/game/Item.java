package mobagame.core.game;

import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mobagame.core.networking.packets.RequestPlayerBuyItemPacket;
import mobagame.core.networking.packets.RequestPlayerSellItemPacket;
import mobagame.launcher.GameScreen;
import mobagame.launcher.MobaGameLauncher;
import mobagame.launcher.MyCanvas;
import mobagame.launcher.Shop;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

public class Item implements MobaGameLauncher {

	Logger logger = Logger.getLogger(this.getClass().getName());

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

		logger.log(Level.WARNING, "Item not in inventory");
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

	public byte use(InGamePlayer user) {
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
					logger.log(Level.WARNING, "Not a valid consumable item");
					break;
				}
			}
			return 1;
		}
		return 0;
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