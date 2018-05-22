package mobagame.launcher;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.game.ItemType;

@SuppressWarnings("serial")
public class Shop extends JFrame implements ActionListener, MobaGameLauncher {
	// DNW
	private ArrayList<Item> items = new ArrayList<Item>();

	// items
	public static Item empty = new Item("empty", "resources/Items/emptySlot.png", 0, 0, false);
	public static Item speedBow = new Item("Speed Bow", "resources/Items/item1.png", 100, ItemType.Speed, 100, false);
	public static Item manaSword = new Item("Mana Sword", "resources/Items/item2.png", 50, ItemType.Mana, 50, false);
	public static Item healthNumchucks = new Item("Health Numchucks", "resources/Items/item3.png", 30, ItemType.Health, 30, false);
	public static Item armorBow = new Item("Armor Bow", "resources/Items/item4.png", 10, ItemType.Armor, 10, false);
	public static Item powerKnife = new Item("Power Knife", "resources/Items/knife.png", 500, ItemType.PhysicalPower, 100, false);
	public static Item healingBerry = new Item("Healing Berry", "resources/Items/strawberry.png", 5, ItemType.Health, 100, true);
	public static Item attackingReaper = new Item("Reaper", "resources/Reaper.png", 30, ItemType.AttackSpecial, 0, false);

	private InGamePlayer user;

	public Shop() {
		this(new InGamePlayer(0));
	}

	public Shop(InGamePlayer user) {
		super("Shop");

		this.user = user;

		// add items to list
		items.add(speedBow);
		items.add(manaSword);
		items.add(healthNumchucks);
		items.add(armorBow);
		items.add(powerKnife);
		items.add(healingBerry);
		items.add(attackingReaper);

		JPanel shop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemList = new JPanel(new GridLayout(0, 5));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			Button temp = new Button(items.get(x) + "");
			temp.setActionCommand(items.get(x).getName());
			temp.addActionListener(this);
			itemList.add(temp);
		}

		itemList.setSize(SCREEN_SIZE.height - SCREEN_SIZE.height / 100, SCREEN_SIZE.width / 3);
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridwidth = 0;
		c.fill = 0;
		shop.add(list, c);
		setResizable(false);

		setSize(500, 1000);
		add(list);
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent fe) {
				fe.getComponent().setVisible(false);
			}

			@Override
			public void focusGained(FocusEvent fe) {
				// TODO Auto-generated method stub

			}
		});
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		for (int x = 0; x < items.size(); x++) {
			if (cmd.equals(items.get(x).getName())) {
				items.get(x).buy(user);
				return;
			}
		}
		System.out.println("Error: Not valid item");
	}

	public static void main(String[] args) {
		Shop x = new Shop();
		x.user.setGoldAmount(500);
	}
}
