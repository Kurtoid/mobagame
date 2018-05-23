package mobagame.launcher;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import mobagame.core.game.Item;
import mobagame.core.game.ItemType;

public interface MobaGameLauncher {

	public final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	public final int WINDOW_HEIGHT = SCREEN_SIZE.height * 4 / 7;
	public final int WINDOW_WIDTH = (int) (WINDOW_HEIGHT * 1.875);

	public final static String GAME_NAME = "[INSERT AWESOME GAME NAME HERE]";

	// set fonts
	public final int FONT_SIZE = (int) ((WINDOW_WIDTH / 90) * 1.5);
	public final static String FONT = "Old English Text MT";
	public final Font MENU_FONT = new Font(FONT, Font.PLAIN, FONT_SIZE);
	public final Font GAME_FONT = new Font("Times New Roman", Font.BOLD, (int) (SCREEN_SIZE.getWidth() / 100));
	public final Font CHAT_FONT = new Font("Times New Roman", Font.PLAIN, (int) (SCREEN_SIZE.getWidth() / 100 * 3 / 2));

	// items
	
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
}
