package mobagame.launcher;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.GameCharcters;
import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.networking.packets.DEBUG_JustJoinToAGame;
import mobagame.core.networking.packets.PublicPlayerDataPacket;
import mobagame.core.networking.packets.RequestEnterGamePacket;
import mobagame.core.networking.packets.RequestEnterGameResponsePacket;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;
import mobagame.server.database.PlayerAccount;

@SuppressWarnings("serial")
public class Shop implements MobaGameLauncher {

	// Item Array
	public final ArrayList<Item> items = new ArrayList<Item>();

	Logger logger = Logger.getLogger(this.getClass().getName());

	private InGamePlayer user;
	private GridBagConstraints c = new GridBagConstraints();
	private JPanel display = new JPanel(new GridBagLayout());

	private JButton buy = new JButton("Buy");
	private JButton sell = new JButton("Sell");

	private static boolean testing = false;
	int activeItemID = 0;

	public Shop(InGamePlayer user) {

		JFrame f = new JFrame("Shop");
		f.setResizable(false);
		f.setSize((SCREEN_SIZE.width / 9 + SCREEN_SIZE.width / 15) * 3, SCREEN_SIZE.height * 2 / 3);

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
		list.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		for (int x = 0; x < items.size(); x++) {
			JButton displayItemX = new JButton(items.get(x).toString());
			final int finalX = x;
			displayItemX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (activeItemID != finalX) {
						activeItemID = finalX;
						logger.log(Level.INFO, "Now displaying item " + activeItemID);
						displayItem(items.get(activeItemID));
						display.repaint();
					}
					logger.log(Level.INFO, "Already displaying item " + activeItemID);
				}
			});
			itemList.add(displayItemX);
		}

		list.setSize(f.getSize().height, f.getSize().width / 3);
		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 0;
		c.fill = 0;
		shop.add(list, c);
		c.gridx = 1;
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
		c.gridy = 6;
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

		displayItem(GameItems.healingBerry);
		activeItemID = items.indexOf(GameItems.healingBerry);
		logger.log(Level.INFO, " Now displaying item " + activeItemID);

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

		itemName.setText(item.getName());
		itemImage.setImageLocation(item.getImageLocation());
		itemPrice.setText("$" + item.getPrice());

		effectList.removeAll();
		c.gridwidth = 2;
		c.gridy = 4;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;

		for (int x = 0; x < item.getType().length; x++) {
			JLabel label = new JLabel("+" + item.getEffectPoints()[x] + " " + item.getType()[x]);
			label.setFont(GAME_FONT);
			label.setHorizontalAlignment(JLabel.CENTER);
			effectList.add(label);
		}
		display.add(effectList, c);

		buy.setActionCommand(item.getName());
		sell.setActionCommand(item.getName());

		logger.log(Level.INFO, " Displaying " + item.getName());
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
		Login.fakeLogin();
		RspHandler.getInstance().waitForResponse(); // wait for one (maybe two) packets, or three seconds
		PublicPlayerDataPacket playerData = (PublicPlayerDataPacket) RspHandler.getInstance()
				.getResponse(PublicPlayerDataPacket.class);
		PlayerAccount p = playerData.player;
		DEBUG_JustJoinToAGame req = new DEBUG_JustJoinToAGame(p.id);
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(req.getBytes().array());
			RspHandler.getInstance().waitForResponse();
			RequestEnterGameResponsePacket game = (RequestEnterGameResponsePacket) RspHandler.getInstance()
					.getResponse(RequestEnterGameResponsePacket.class);
			System.out.println(game.playerID);

			GameScreen s = new GameScreen(game.gameID, p, game.playerID,  GameCharcters.reaper);
			testing = true;
//		InGamePlayer user = new InGamePlayer(0);
//		Shop s = new Shop(user);
//		user.setGoldAmount(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
