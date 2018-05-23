package mobagame.launcher;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;

@SuppressWarnings("serial")
public class Shop extends JFrame implements ActionListener, MobaGameLauncher {
	// DNW
	private ArrayList<Item> items = new ArrayList<Item>();

	// items

	private InGamePlayer user;

	public Shop(InGamePlayer user) {
		super("Shop");

		this.user = user;

		// add items to list
		items.add(GameItems.speedBow);
		items.add(GameItems.manaSword);
		items.add(GameItems.healthNumchucks);
		items.add(GameItems.armorBow);
		items.add(GameItems.powerKnife);
		items.add(GameItems.healingBerry);
		items.add(GameItems.attackingReaper);

		JPanel shop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemList = new JPanel(new GridLayout(0, 3));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			Button temp = new Button(items.get(x) + "");
			temp.setActionCommand(items.get(x).getName());
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
		setResizable(false);

		setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 2, SCREEN_SIZE.height * 2 / 3);
		add(list);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
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
		InGamePlayer x = new InGamePlayer(846512);
		Shop t = new Shop(x);
		x.setGoldAmount(500);
	}
}
