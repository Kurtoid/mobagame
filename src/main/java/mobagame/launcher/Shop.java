package mobagame.launcher;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
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
	
	private static boolean testing = false;

	public Shop(InGamePlayer user) {

		JFrame f = new JFrame("Shop");

		this.user = user;

		// add items to list
		for (Item item: GameItems.allGameItems) {
			items.add(item);
		}
		items.remove(GameItems.empty);

		JPanel shop = new JPanel(new GridLayout(1, 2));
		JPanel itemList = new JPanel(new GridLayout(0, 2));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			JButton temp = new JButton(items.get(x).toString());
			temp.setActionCommand("d" + items.get(x).getName());
			temp.addActionListener(this);
			itemList.add(temp);
		}

		displayItem(GameItems.healingBerry);

		list.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 2, SCREEN_SIZE.height * 2 / 3);
		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 0;
		c.fill = 0;
		shop.add(list, c);
		c.gridx = 1;
		display.setBorder(blue);
		shop.add(display, c);
		c.gridx = 0;

		f.setResizable(false);

		f.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 3, SCREEN_SIZE.height * 2 / 3);
		f.add(shop);

		changeFontRecursive(f, GAME_FONT);
		if (testing) {
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		f.setAlwaysOnTop(true);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		String item = ae.getActionCommand().substring(1, ae.getActionCommand().length());
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

	private JLabel itemName = new JLabel();
	private MyCanvas itemImage = new MyCanvas("", SCREEN_SIZE.width / 10);
	private JLabel itemPrice = new JLabel();
	private final JLabel labelEffect = new JLabel("Effects:");
	private JPanel effectList = new JPanel(new GridLayout(0,1));

	private void displayItem(Item item) {
		JButton buy = new JButton("Buy");
		JButton sell = new JButton("Sell");
		itemName.setHorizontalAlignment(JLabel.CENTER);
		itemPrice.setHorizontalAlignment(JLabel.CENTER);
		labelEffect.setHorizontalAlignment(JLabel.CENTER);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		effectList.removeAll();
		c.gridwidth = 2;
		c.gridy = 0;
		itemName.setText(item.getName());
		display.add(itemName, c);
		c.gridy = 1;
		itemImage.setImageLocation(item.getImageLocation());
		display.add(itemImage, c);
		c.gridy = 2;
		itemPrice.setText("$" + item.getPrice());
		display.add(itemPrice, c);
		c.gridy = 3;
		display.add(labelEffect, c);
		c.gridy = 4;
		for (int x = 0; x < item.getType().length; x++) {
			JLabel label = new JLabel("+" + item.getEffectPoints()[x] + " " + item.getType()[x]);
		    label.setHorizontalAlignment(JLabel.CENTER);
			effectList.add(label);			
		}
		display.add(effectList, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 5;
		c.gridx = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		buy.setActionCommand("b" + item.getName());
		buy.addActionListener(this);
		display.add(buy, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		sell.setActionCommand("s" + item.getName());
		sell.addActionListener(this);
		display.add(sell, c);
		display.repaint();

		System.out.println("Info: Displaying " + item.getName());
	}
	
	public void changeFontRecursive(Container root, Font font) {
		for (Component c : root.getComponents()) {
			c.setFont(font);
			if (c instanceof Container) {
				changeFontRecursive((Container) c, font);
			}
		}
	}

	public static void main(String[] args) {
		testing = true;
		InGamePlayer x = new InGamePlayer(846512);
		new Shop(x);
		x.setGoldAmount(50000);
	}
}
