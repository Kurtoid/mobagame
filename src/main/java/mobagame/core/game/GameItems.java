package mobagame.core.game;

import java.util.Arrays;
import java.util.List;

public class GameItems {
	public final static Item empty = new Item("empty", "resources/Items/emptySlot.png", 0, ItemType.NULL, 0, false);
	public final static Item speedBow = new Item("Speed Bow", "resources/Items/item1.png", 300, ItemType.Speed, 100,false);
	public final static Item manaSword = new Item("Mana Sword", "resources/Items/item2.png", 200, ItemType.Mana, 50,false);
	public final static Item healthNumchucks = new Item("Health Numchucks", "resources/Items/item3.png", 85,ItemType.Health, 30, false);
	public final static Item armorBow = new Item("Armor Bow", "resources/Items/item4.png", 25, ItemType.Armor, 10,false);
	public final static Item powerKnife = new Item("Power Knife", "resources/Items/knife.png", 150, ItemType.PhysicalPower,75, false);
	public final static Item scythe = new Item("Scythe", "resources/Items/scythe.png", 300, ItemType.AbilityPower, 100, false);
	public final static Item healingBerry = new Item("Healing Berry", "resources/Items/strawberry.png", 50,ItemType.Health, 50, true);
	public final static Item manaPizza = new Item("Mana Pizza", "resources/Items/pizza.png", 50, ItemType.Mana, 50,true);
	public final static Item protectiveFeather = new Item("Protective Feather", "resources/Items/feather.png", 250,ItemType.Armor, 100, true);

	public static final Item[] allGameItems = { empty, speedBow, armorBow, manaSword, healthNumchucks, armorBow,
			powerKnife, scythe, healingBerry, manaPizza, protectiveFeather };
	public static final List<Item> allGameItemsLookup = Arrays.asList(allGameItems);

}
