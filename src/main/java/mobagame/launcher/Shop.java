package mobagame.launcher;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;

@SuppressWarnings("serial")
public class Shop implements ActionListener, MobaGameLauncher {

	// Item Array
	public final static ArrayList<Item> items = new ArrayList<Item>();

	private InGamePlayer user;
	private GridBagConstraints c = new GridBagConstraints();
	private JPanel display = new JPanel(new GridBagLayout());

	public Shop(InGamePlayer user) {

		JFrame f = new JFrame("Shop");

		this.user = user;

		// add items to list

		items.add(GameItems.speedBow);
		items.add(GameItems.manaSword);
		items.add(GameItems.healthNumchucks);
		items.add(GameItems.armorBow);
		items.add(GameItems.powerKnife);
		items.add(GameItems.healingBerry);
		items.add(GameItems.attackingReaper);
		items.add(GameItems.manaPizza);
		items.add(GameItems.protectiveFeather);

		JPanel shop = new JPanel(new GridBagLayout());
		JPanel itemList = new JPanel(new GridLayout(0, 3));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			Button temp = new Button(items.get(x).toString());
			temp.setActionCommand("d" + items.get(x).getName());
			temp.addActionListener(this);
			itemList.add(temp);
		}

		displayItem(GameItems.healingBerry);

		list.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 2, SCREEN_SIZE.height * 2 / 3);
		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridwidth = 0;
		c.fill = 0;
		shop.add(list, c);
		c.gridx = 1;
		shop.add(display, c);
		c.gridx = 0;
		
		f.setResizable(false);

		f.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 3, SCREEN_SIZE.height * 2 / 3);
		f.add(shop);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setAlwaysOnTop(true);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		String item = ae.getActionCommand().substring(1);
		char action = ae.getActionCommand().charAt(0);
		for (int x = 0; x < items.size(); x++) {
			if (item.equals(items.get(x).getName())) {
				if (action == 'b') {
					items.get(x).buy(user);
					return;
				} else if (action == 's') {
					items.get(x).sell(user);
					return;
				} else if (action == 'd') {
					displayItem(items.get(x));
					return;
				}
			}
		}
		System.out.println("Error: Not valid item");

	}

	private void displayItem(Item item) {
		c.gridy = 0;
		display.add(new JLabel(item.getName()), c);
		c.gridy = 1;
		display.add(new MyCanvas(item.getImageLocation(), SCREEN_SIZE.width / 10), c);
		c.gridy = 2;
		display.add(new JLabel("$" + item.getPrice()), c);
		c.gridy = 3;
		display.add(new JLabel("Effects:"), c);
		c.gridy = 4;
		for (int x = 0; x < item.getType().length; x++) {
			c.gridy = x + 5;
			display.add(new JLabel("+" + item.getEffectPoints()[x] + " " + item.getType()[0]), c);
		}
		c.gridy = 5;
		Button buy = new Button("Buy");
		buy.setActionCommand("b" + item.getName());
		buy.addActionListener(this);
		display.add(buy);
		c.gridy = 6;
		Button sell = new Button("Sell");
		sell.setActionCommand("s" + item.getName());
		sell.addActionListener(this);
		display.add(sell);

		System.out.println("Info: Displaying " + item.getName());
	}

	public static void main(String[] args) {
		InGamePlayer x = new InGamePlayer(846512);
		new Shop(x);
		x.setGoldAmount(50000);
	}
}
