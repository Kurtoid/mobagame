/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import mobagame.core.game.Ability;
import mobagame.core.game.Character;
import mobagame.core.game.GameCharcters;
import mobagame.core.game.GameItems;
import mobagame.core.game.GameTeams;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.ObjectMover;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.*;
import mobagame.launcher.game.ClientGame;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;
import mobagame.server.database.PlayerAccount;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

@SuppressWarnings("serial")
public class GameScreen implements ActionListener, KeyListener, MouseListener, Runnable, MobaGameLauncher {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public final String chatWrap = "<html><body style='width: " + SCREEN_SIZE.getWidth() / 16 * 3 + "px'>";

	public boolean testing = false;
	private boolean usePadAndBar = false;
	private boolean lefty = false;

	private InGamePlayer user;

	private int goldPerSecond = 3;
	private JButton gold;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private JPanel inventory;
	private JScrollPane chat;
	JPanel chatText = new JPanel(new GridLayout(0, 1));
	private JLabel health = new JLabel("Loading health");
	private JLabel mana = new JLabel("Loading mana");

	private JFrame f = new JFrame(MobaGameLauncher.GAME_NAME);

	ClientGame game;
	private MyCanvas[] inventoryCanvas;

	// open menu window for playerName
	public GameScreen(int gameID, PlayerAccount player, InGamePlayer p, Character character,
			ArrayList<InGamePlayer> players) {
		System.out.println(gameID);
		ClientGame g = new ClientGame(gameID);
		g.players = players;
		g.setPlayerPlayer(p);
		g.getPlayerPlayer().mover = new ObjectMover(g.map, g.getPlayerPlayer());
		if (!g.players.contains(p))
			g.players.add(p);
		System.out.println("I am " + p.getPlayerID());
		g.map = new MainMap();
		g.map.setSize(SCREEN_SIZE.width, SCREEN_SIZE.height);
		g.map.makeMap();
		this.game = g;
		user = p;

		inventoryCanvas = new MyCanvas[] { MyCanvas.load(p.inventory[0].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[1].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[2].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[3].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[4].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[5].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[6].getImageLocation(), SCREEN_SIZE.width / 40),
				MyCanvas.load(p.inventory[7].getImageLocation(), SCREEN_SIZE.width / 40) };

		UIManager.put("OptionPane.messageFont", CHAT_FONT);
		UIManager.put("OptionPane.buttonFont", MENU_FONT);

		// listeners
		f.addKeyListener(this);
		f.addMouseListener(this);

		// create
		gold = new JButton("$" + user.getGoldAmount());
		gold.setActionCommand(SHOP);
		gold.addActionListener(this);

		chatText.setBounds(0, SCREEN_SIZE.height / 2, SCREEN_SIZE.width / 4, SCREEN_SIZE.height / 2);

		// health & mana borders
		TitledBorder healthBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Health: ",
				TitledBorder.CENTER, TitledBorder.TOP, GAME_FONT);
		health.setBorder(healthBorder);
		health.setHorizontalAlignment((int) JPanel.CENTER_ALIGNMENT);
		TitledBorder manaBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mana: ",
				TitledBorder.CENTER, TitledBorder.TOP, GAME_FONT);
		mana.setBorder(manaBorder);
		mana.setHorizontalAlignment((int) JPanel.CENTER_ALIGNMENT);

		// make layouts
		GridBagLayout gbl = new GridBagLayout();

		// make panels
		Dimension d = new Dimension();
		JLayeredPane layered = new JLayeredPane();
		layered.setSize(SCREEN_SIZE);
		JPanel front = new JPanel(gbl);
		front.setSize(SCREEN_SIZE);
		chat = new JScrollPane(chatText);
		chat.setSize(SCREEN_SIZE.width / 4, SCREEN_SIZE.height / 2);
		JPanel stats = new JPanel(gbl);
		d.setSize((SCREEN_SIZE.width / 4), (SCREEN_SIZE.height));
		stats.setMaximumSize(d);
		inventory = new JPanel(gbl);
		inventory.setSize(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 10);
		JPanel mapPanel = new JPanel(gbl);
		mapPanel.setSize((int) SCREEN_SIZE.getWidth() / 10, (int) SCREEN_SIZE.getWidth() / 10);

		GridBagConstraints c = new GridBagConstraints();

		// set layout
		setInventory();

		// inventory
		c.gridwidth = 1;
		c.weighty = 1;
		c.weightx = 1;
		c.gridy = 0;
		c.gridx = 0;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.gridy = 2;
		c.gridx = 0;
		inventory.add(gold, c);
		c.fill = 0;
		c.gridwidth = 1;
		c.gridy = 0;
		c.gridx = 0;

		// mapPanel
		g = new ClientGame(gameID);
		g.map = new MainMap();
		g.map.setSize(mapPanel.getWidth(), mapPanel.getHeight());
		g.map.makeMap();
		MapPanel miniMap = new MapPanel(g);
		miniMap.setPreferredSize(mapPanel.getSize());
		miniMap.removeMouseListener(miniMap.getMouseListeners()[0]);
		miniMap.removeMouseMotionListener(miniMap.getMouseMotionListeners()[0]);
		miniMap.removeMouseWheelListener(miniMap.getMouseWheelListeners()[0]);
		miniMap.removeKeyListener(miniMap.getKeyListeners()[0]);
		miniMap.marker.height = 0;
		mapPanel.add(miniMap, c);

		// chat
		addToChat(user.toString());
		chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// stats
		c.gridy = 0;
		Ability[] abilities = { (user.getCharacter().getAbiq()), (user.getCharacter().getAbiw()),
				(user.getCharacter().getAbie()), (user.getCharacter().getAbir()) };

		for (int x = 0; x < abilities.length; x++) {
			c.gridx = x;
			stats.add(new MyCanvas(abilities[x].getImageLocation(), SCREEN_SIZE.width / 40), c);
		}
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 4;
		c.gridy = 1;
		stats.add(mana, c);
		c.gridy = 2;
		stats.add(health, c);
		c.gridwidth = 0;
		c.fill = 0;

		// Draw Rectangles DNW yet
		// RectangleDrawing manaBar = new RectangleDrawing(0, 0, SCREEN_SIZE.height,
		// SCREEN_SIZE.width, Color.GREEN, true);
		// mana.add(manaBar);

		f.setSize(SCREEN_SIZE);
		f.setUndecorated(true);
		if (testing) {
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.SOUTHWEST;
		front.add(chat, c);
		chat.setBorder(yellow);
		c.gridx = 1;
		c.anchor = GridBagConstraints.SOUTH;
		front.add(stats, c);
		stats.setBorder(frame);

		// mapPanel & inventory
		if (lefty) {
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridy = 0;
			c.gridx = 0;
			front.add(inventory, c);
			inventory.setBorder(frame);
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridx = 2;
			front.add(mapPanel, c);
			mapPanel.setBorder(blue);
			mapPanel.setBounds(0, 0, mapPanel.getWidth(), mapPanel.getHeight());
		} else {
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridy = 0;
			c.gridx = 2;
			front.add(inventory, c);
			inventory.setBorder(frame);
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridx = 0;
			front.add(mapPanel, c);
			mapPanel.setBorder(blue);
			mapPanel.setBounds(0, 0, mapPanel.getWidth(), mapPanel.getHeight());
		}

		front.setBorder(frame);
		front.setSize(SCREEN_SIZE);
		front.setOpaque(false);
		front.setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);
		layered.add(front, new Integer(1), 0);

		MapPanel background = new MapPanel(game);
		background.setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);
		background.resetPanAndZoom();
		layered.add(background, new Integer(0), 0);
		f.add(layered);
		Thread t = new Thread(background);
		t.start();

		f.setVisible(true);
		changeFontRecursive(f, GAME_FONT);
		gold.setFont(MENU_FONT);
		changeFontRecursive(chat, CHAT_FONT);
		f.setFocusable(true);
		start();
	}

	public void addToChat(String text) {
		JLabel temp = new JLabel(chatWrap + "" + text);
		temp.setFont(CHAT_FONT);
		chatText.add(temp);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / goldPerSecond);
			} catch (InterruptedException e) {
			}
			// user.setGoldAmount(user.getGoldAmount() + 1);
			gold.setText("$" + user.getGoldAmount());
			chat.repaint();
			health.setText(user.getCurrentHealth() + " / " + user.getMaxHealth());
			f.requestFocus();
			mana.setText(user.getCurrentMana() + " / " + user.getMaxMana());
			refreshInventory();
		}
	}

	public void changeFontRecursive(Container root, Font font) {
		for (Component c : root.getComponents()) {
			c.setFont(font);
			if (c instanceof Container) {
				changeFontRecursive((Container) c, font);
			}
		}
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	public void setInventory() {
		GridBagConstraints c = new GridBagConstraints();
		for (int y = 0; y < user.getInventory().length; y++) {
			c.gridy = y / 4;
			c.gridx = y % 4;
			inventoryCanvas[y].addMouseListener(this);
			inventory.add(inventoryCanvas[y], c);
		}
		logger.log(Level.INFO, "Inventory set");
	}

	public void refreshInventory() {

		for (int y = 0; y < user.getInventory().length; y++) {
			inventoryCanvas[y].setImageLocation(user.getInventory()[y].getImageLocation());
		}
		inventory.repaint();
		logger.log(Level.INFO, " Inventory repainted");
	}

	public void keyPressed(KeyEvent ke) {
		int pressed = ke.getKeyCode();
		logger.log(Level.INFO, "KEY PRESSED: " + pressed);
		if (pressed >= 97 && pressed <= 105 && usePadAndBar) {
			pressed -= 48;
		}
		switch (pressed) { // TODO Make keys do proper things
		case KeyEvent.VK_TAB:
			// TAB???
			logger.log(Level.INFO, "TAB pressed???");
			break;
		case KeyEvent.VK_P:
			// GOTO Shop
			new Shop(user);
			break;
		case KeyEvent.VK_M:
			// GOTO In-Game
			logger.log(Level.INFO, "GOTO In-Game");
			JOptionPane.showMessageDialog(f, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
			break;
		case KeyEvent.VK_Q:
			// USE Q ability
			logger.log(Level.INFO, "USE Q ability");
			// charater.UseAbility(Q);
			break;
		case KeyEvent.VK_W:
			// USE W ability
			logger.log(Level.INFO, "USE W ability");
			// charater.UseAbility(W);
			break;
		case KeyEvent.VK_E:
			// USE E ability
			logger.log(Level.INFO, "USE E ability");
			// charater.UseAbility(E);
			break;
		case KeyEvent.VK_R:
			// USE R ability
			logger.log(Level.INFO, "USE R ability");
			// charater.UseAbility(R);
			break;
		case KeyEvent.VK_D:
			// USE D ability
			logger.log(Level.INFO, "USE D ability");
			// charater.UseAbility(D);
			break;
		case KeyEvent.VK_F:
			// USE F ability
			logger.log(Level.INFO, "USE F ability");
			// charater.UseAbility(F);
			break;
		case KeyEvent.VK_1:
			// USE inventory slot 1
			logger.log(Level.INFO, "USE inventory slot 1");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[0]));
			break;
		case KeyEvent.VK_2:
			// USE inventory slot 2
			logger.log(Level.INFO, "USE inventory slot 2");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[1]));
			break;
		case KeyEvent.VK_3:
			// USE inventory slot 3
			logger.log(Level.INFO, "USE inventory slot 3");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[2]));
			break;
		case KeyEvent.VK_4:
			// USE inventory slot 4
			logger.log(Level.INFO, "USE inventory slot 4");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[3]));
			break;
		case KeyEvent.VK_5:
			// USE inventory slot 5
			logger.log(Level.INFO, "USE inventory slot 5");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[4]));
			break;
		case KeyEvent.VK_6:
			// USE inventory slot 6
			logger.log(Level.INFO, "USE inventory slot 6");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[5]));
			break;
		case KeyEvent.VK_7:
			// USE inventory slot 7
			logger.log(Level.INFO, "USE inventory slot 7");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[6]));
			break;
		case KeyEvent.VK_8:
			// USE inventory slot 8
			logger.log(Level.INFO, "USE inventory slot 8");
			reportUseItem(GameItems.allGameItemsLookup.indexOf(user.inventory[7]));
			break;
		case KeyEvent.VK_ESCAPE:
			// Escape pressed
			logger.log(Level.INFO, "Escape pressed");
			System.exit(0);
			break;
		}
	}

	private void reportUseItem(int itemID) {
		PlayerUseItemRequestPacket pkt = new PlayerUseItemRequestPacket();
		pkt.itemID = itemID;
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseClicked(MouseEvent me) { // TODO Click to move
		logger.log(Level.INFO, "Mouse Point: (" + me.getX() + ", " + me.getY() + ")");
	}

	public void actionPerformed(ActionEvent ae) { // TODO Send to appropriate windows
		String cmd = ae.getActionCommand();

		if (SHOP.equals(cmd)) { // GOTO Shop
			new Shop(user);
		} else if (MENU.equals(cmd)) { // GOTO In-Game
			JOptionPane.showMessageDialog(f, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(f, "Something went wrong", "Error Message", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		Login.fakeLogin();
		RspHandler.getInstance().waitForResponse(); // wait for one (maybe two) packets, or three seconds
		PublicPlayerDataPacket playerData = (PublicPlayerDataPacket) RspHandler.getInstance()
				.getResponse(PublicPlayerDataPacket.class);
		PlayerAccount p = playerData.player;
		DEBUG_JustJoinToAGame req = new DEBUG_JustJoinToAGame(p.id);
		System.out.println("just join a game " + p.id);
		try {
			ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(req.getBytes().array());
			RspHandler.getInstance().waitForResponse();
			RequestEnterGameResponsePacket game = (RequestEnterGameResponsePacket) RspHandler.getInstance()
					.getResponse(RequestEnterGameResponsePacket.class);
			System.out.println("playerid is " + game.playerID);
			InGamePlayer plr = new InGamePlayer(p.id, GameCharcters.jack);
			plr.team = GameTeams.lowTeam;
			plr.pos = plr.team.spawnPoint;
			GameScreen s = new GameScreen(game.gameID, new PlayerAccount(p.username), plr, GameCharcters.reaper,
					new ArrayList<InGamePlayer>());
			s.testing = true;
			s.game.getPlayerPlayer().setGoldAmount(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Not used interface methods
	public void keyTyped(KeyEvent ke) {
		// char pressed = ke.getKeyChar();
		// logger.log(Level.INFO, "KEY TYPED: " + pressed);
	}

	public void keyReleased(KeyEvent ke) {
		// char released = ke.getKeyChar();
		// logger.log(Level.INFO, "KEY RELEASED: " + released);
	}

	public void mousePressed(MouseEvent me) {
		// logger.log(Level.INFO, "Mouse pressed; # of clicks: "
		// + me.getClickCount());
	}

	public void mouseReleased(MouseEvent me) {
		// logger.log(Level.INFO, "Mouse released; # of clicks: "
		// + me.getClickCount());
	}

	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
	}

}