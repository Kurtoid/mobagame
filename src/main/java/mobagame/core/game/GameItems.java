package mobagame.core.game;

import java.util.Arrays;
import java.util.List;

public class GameItems {
	public final static Item empty = new Item("empty", "resources/Items/emptySlot.png", 0, 0, false);
	public final static Item speedBow = new Item("Speed Bow", "resources/Items/item1.png", 1, ItemType.Speed, 100,
			false);
	public final static Item manaSword = new Item("Mana Sword", "resources/Items/item2.png", 1, ItemType.Mana, 50,
			false);
	public final static Item healthNumchucks = new Item("Health Numchucks", "resources/Items/item3.png", 1,
			ItemType.Health, 30, false);
	public final static Item armorBow = new Item("Armor Bow", "resources/Items/item4.png", 1, ItemType.Armor, 10,
			false);
	public final static Item powerKnife = new Item("Power Knife", "resources/Items/knife.png", 1,
			ItemType.PhysicalPower, 100, false);
	public final static Item healingBerry = new Item("Healing Berry", "resources/Items/strawberry.png", 1,
			ItemType.Health, 100, true);
	public final static Item attackingReaper = new Item("Reaper", "resources/Reaper.png", 1, ItemType.AttackSpecial, 0,
			false);
	public final static Item manaPizza = new Item("Mana Pizza", "resources/Items/pizza.png", 1, ItemType.Mana, 100,
			true);
	public final static Item protectiveFeather = new Item("Protective Feather", "resources/Items/feather.png", 1, ItemType.Armor, 100,
			true);

	public static final Item[] allGameItems = { empty, speedBow, armorBow, manaSword, healthNumchucks, armorBow,
			powerKnife, healingBerry, attackingReaper, manaPizza, protectiveFeather };
	public static final List<Item> allGameItemsLookup = Arrays.asList(allGameItems);

}
