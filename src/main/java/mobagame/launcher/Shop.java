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
	private JPanel display;

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


		JPanel shop = new JPanel(new GridLayout(1, 2));
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemList = new JPanel(new GridLayout(0, 3));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			Button temp = new Button(items.get(x).toString());
			temp.setActionCommand("d" + items.get(x).getName());
			temp.addActionListener(this);
			itemList.add(temp);
		}

		itemList.setSize(SCREEN_SIZE.width / 3, SCREEN_SIZE.height * 2 / 3);
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridwidth = 0;
		c.fill = 0;
		shop.add(list, c);
		f.setResizable(false);

		f.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 2, SCREEN_SIZE.height * 2 / 3);
		f.add(list);

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
				} else if (action == 's') {
					items.get(x).sell(user);
				}
			} else if (action == 'd') {
				displayItem(items.get(x));
			}
			return;
		}
		System.out.println("Error: Not valid item");

	}

	private void displayItem(Item item) {
		
	}

	public static void main(String[] args) {
		InGamePlayer x = new InGamePlayer(846512);
		new Shop(x);
		x.setGoldAmount(50000);
	}
}
