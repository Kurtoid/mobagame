package mobagame.IDKWhereToPutThis;

public class Item {
	private String name;
	private String imageLocation;
	private int price;
	private int effectPoints;
	private boolean isConsumable;
//	private ItemType type;

	Item(String name, String imageLocation, int price, int effectPoints, boolean isConsumable) {
		this.name = name;
		this.imageLocation = imageLocation;
		this.price = price;
		this.effectPoints = effectPoints;
		this.isConsumable = isConsumable;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Item use(Character user) {
		if (isConsumable) {
			// do stuff
//			Character.setHealth(Character.getHealth() + effectPoints);
//			Character.setMana(Character.getMana() + effectPoints);
			return null;
		}
		return this;
	}

}
