package mobagame.launcher;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;

@SuppressWarnings("serial")
public class Shop extends JFrame implements ActionListener {
	// DNW
	private Item[] items;
	
	private static Item knife = new Item("knife", "resources/Items/knife.png", 500, 10, false);
	
	private Dimension SCREEN_SIZE = getToolkit().getScreenSize();
	private InGamePlayer user = new InGamePlayer(0);

	public Shop() {
		super("Shop");

		int numOfItems = 1;
		JPanel shop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemList = new JPanel(new GridLayout(0, 5));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		items = new Item[numOfItems];
		addItems(knife, numOfItems);
		for (Item w : items) {
			Button temp =  new Button(w + "");
			temp.setActionCommand(w.getName());
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

	private void addItems(Item item, int x) {
		if (x > 0) {
			items[x - 1] = item;
			addItems(item, x - 1);
		}
	}

	public static void main(String[] args) {
		Shop x = new Shop();
		x.user.setGoldAmount(500);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		switch (cmd) {
		case "knife":
			knife.buy(user);
			break;
		default:
			break;
		}
	}

}
