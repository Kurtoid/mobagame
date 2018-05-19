package mobagame.launcher;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.Item;

public class Shop extends JFrame {
	// DNW
	private Item[] items;

	private Dimension SCREEN_SIZE = getToolkit().getScreenSize();

	public Shop() {
		super("Shop");

		int numOfItems = 234;
		JPanel shop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemList = new JPanel(new GridLayout(0, 5));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		items = new Item[numOfItems];
		addItems(new Item("knife", "resources/Items/knife.png", 500, 10, false), numOfItems);
		for (Item w : items) {
			itemList.add(new Button("" + w.getPrice()));
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addItems(Item item, int x) {
		if (x > 0) {
			items[x - 1] = item;
			addItems(item, x - 1);
		}
	}

	public static void main(String[] args) {
		new Shop();
	}

}
