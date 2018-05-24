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
public class Shop implements MobaGameLauncher {

	// Item Array
	public final static ArrayList<Item> items = new ArrayList<Item>();

	private InGamePlayer user;
	private GridBagConstraints c = new GridBagConstraints();
	private JPanel display = new JPanel(new GridBagLayout());

	private JButton buy = new JButton("Buy");
	private JButton sell = new JButton("Sell");

	private static boolean testing = false;
	int finalX = 0;

	public Shop(InGamePlayer user) {

		JFrame f = new JFrame("Shop");

		this.user = user;

		// add items to list
		for (Item item : GameItems.allGameItems) {
			items.add(item);
		}
		items.remove(GameItems.empty);

		JPanel shop = new JPanel(new GridLayout(1, 2));
		JPanel itemList = new JPanel(new GridLayout(0, 2));
		JScrollPane list = new JScrollPane(itemList);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int x = 0; x < items.size(); x++) {
			JButton temp = new JButton(items.get(x).toString());
			final int x1 = x;
			temp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					finalX = tmp;
					System.out.println("display action");
					displayItem(items.get(finalX));
					display.repaint();
				}
			});
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
		itemName.setHorizontalAlignment(JLabel.CENTER);
		itemPrice.setHorizontalAlignment(JLabel.CENTER);
		labelEffect.setHorizontalAlignment(JLabel.CENTER);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		display.removeAll();
		c.gridwidth = 2;
		c.gridy = 0;
		display.add(itemName, c);
		c.gridy = 1;
		display.add(itemImage, c);
		c.gridy = 2;
		display.add(itemPrice, c);
		c.gridy = 3;
		display.add(labelEffect, c);
		c.gridy = 4;
		display.add(effectList, c);

		c.fill = GridBagConstraints.NONE;
		c.gridy = 5;
		c.gridx = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				items.get(activeItemID).buy(user);
			}
		});
		display.add(buy, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				items.get(activeItemID).sell(user);
			}
		});
		display.add(sell, c);

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


	private JLabel itemName = new JLabel();
	private MyCanvas itemImage = new MyCanvas("", SCREEN_SIZE.width / 10);
	private JLabel itemPrice = new JLabel();
	private final JLabel labelEffect = new JLabel("Effects:");
	private JPanel effectList = new JPanel(new GridLayout(0, 1));

	private void displayItem(Item item) {
		itemName.setHorizontalAlignment(JLabel.CENTER);
		itemPrice.setHorizontalAlignment(JLabel.CENTER);
		labelEffect.setHorizontalAlignment(JLabel.CENTER);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		effectList.removeAll();
		c.gridwidth = 2;
		c.gridy = 0;
		itemName.setText(item.getName());
		itemImage.setImageLocation(item.getImageLocation());
		itemPrice.setText("$" + item.getPrice());
		effectList.removeAll();
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
		buy.setActionCommand(item.getName());
		display.add(buy, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		sell.setActionCommand(item.getName());
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
