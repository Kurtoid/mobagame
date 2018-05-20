package mobagame.launcher;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;

@SuppressWarnings("serial")
public class Shop extends JFrame implements ActionListener {
	// DNW
	private ArrayList<Item> items = new ArrayList<Item>();

	// items
	public static Item empty = new Item("empty", "resources/Items/emptySlot.png", 0, 0, false);
	public static Item item1 = new Item("item1", "resources/Items/item1.png", 100, 0, false);
	public static Item item2 = new Item("item2", "resources/Items/item2.png", 50, 0, false);
	public static Item item3 = new Item("item3", "resources/Items/item3.png", 30, 0, false);
	public static Item item4 = new Item("item4", "resources/Items/item4.png", 10, 0, false);
	public static Item knife = new Item("knife", "resources/Items/knife.png", 500, 0, false);
	public static Item berry = new Item("berry", "resources/Items/berry.png", 5, 0, false);

	private Dimension SCREEN_SIZE = getToolkit().getScreenSize();
	private InGamePlayer user;

	public Shop() {
		this(new InGamePlayer(0));
	}

	public Shop(InGamePlayer user) {
		super("Shop");

		this.user = user;

		// add items to list
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(knife);
		items.add(berry);

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

		setSize(500, 1000);
		add(list);

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
