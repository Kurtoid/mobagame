/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import mobagame.core.game.Character;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.game.PlayerMover;
import mobagame.core.game.maps.MainMap;
import mobagame.launcher.game.ClientGame;
import mobagame.server.database.PlayerAccount;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener, MouseListener, Runnable {

	public final Dimension SCREEN_SIZE = getToolkit().getScreenSize();
	private final String chatWrap = "<html><body style='width: " + SCREEN_SIZE.getWidth() / 16 * 3 + "px'>";

	private Font menuFont = new Font("Old English Text MT", Font.PLAIN, (int) (SCREEN_SIZE.getWidth() / 100 * 9 / 5));
	private Font gameFont = new Font("Times New Roman", Font.BOLD, (int) (SCREEN_SIZE.getWidth() / 100));
	private Font chatFont = new Font("Times New Roman", Font.PLAIN, (int) (SCREEN_SIZE.getWidth() / 100 * 3 / 2));
	// I think in game font should be TNR since it is easy to read at a smaller
	// print

	private static boolean testing = false;
	private static boolean usePadAndBar = false;
	private static boolean lefty = false;

	private InGamePlayer user = new InGamePlayer(new Character(300, 300), 300, 300);

	private int goldPerSecond = 3;
	private JButton gold;

	// icons
	public static String placeHolderImage = ("resources/Black.png");
	public static String backgroundImage = ("resources/Untitled.png");

	// abilities
	private String[] abilities = { (placeHolderImage), (placeHolderImage), (placeHolderImage), (placeHolderImage) };

	private static String gameName = Menu.gameName;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private JPanel inventory;

	private JFrame controllingFrame; // needed for dialogs

	MainMap gameMap;
	ClientGame game;
	private MyCanvas[][] inventoryCanvase;

	// open menu window for playerName
	public GameScreen(int gameID, PlayerAccount player, int playerID) {
		super(gameName);
		System.out.println(gameID);
		ClientGame g = new ClientGame(gameID);
		InGamePlayer p = new InGamePlayer(playerID);
		g.setPlayerPlayer(p);
		g.getPlayerPlayer().mover = new PlayerMover(g.map, g.getPlayerPlayer());
		g.players.add(p);
		g.map = new MainMap();
		g.map.setSize(SCREEN_SIZE.width, SCREEN_SIZE.height);
		g.map.makeMap();
		this.game = g;
		user= p;
	inventoryCanvase = new MyCanvas[][]{
			{ MyCanvas.load(p.inventory[0][0].getImageLocation(), SCREEN_SIZE.width / 40),
					 MyCanvas.load(p.inventory[0][1].getImageLocation(), SCREEN_SIZE.width / 40),
					MyCanvas.load(p.inventory[0][2].getImageLocation(), SCREEN_SIZE.width / 40),
					MyCanvas.load(p.inventory[0][3].getImageLocation(), SCREEN_SIZE.width / 40) },
			{ MyCanvas.load(p.inventory[1][0].getImageLocation(), SCREEN_SIZE.width / 40),
					MyCanvas.load(p.inventory[1][1].getImageLocation(), SCREEN_SIZE.width / 40),
					 MyCanvas.load(p.inventory[1][2].getImageLocation(), SCREEN_SIZE.width / 40),
					 MyCanvas.load(p.inventory[1][3].getImageLocation(), SCREEN_SIZE.width / 40) } };

		UIManager.put("OptionPane.messageFont", chatFont);
		UIManager.put("OptionPane.buttonFont", menuFont);
		gameMap = new MainMap();
		gameMap.setSize(SCREEN_SIZE.width, SCREEN_SIZE.height);
		gameMap.makeMap();

		// set up things

		user.setGoldAmount(0);

		// listeners
		this.addKeyListener(this);
		this.addMouseListener(this);

		// create
		gold = new JButton("$" + user.getGoldAmount());
		gold.setActionCommand(SHOP);
		gold.addActionListener(this);

		String mapImage = (placeHolderImage);
		JLabel chatLabel = new JLabel(chatWrap + "Welcome to " + gameName + "\n");
		chatLabel.setBounds(0, SCREEN_SIZE.height / 2, SCREEN_SIZE.width / 4, SCREEN_SIZE.height / 2);
		JPanel health = new JPanel();
		JPanel mana = new JPanel();

		// make border
		Border red = BorderFactory.createLineBorder(Color.RED, 1);
		Border orange = BorderFactory.createLineBorder(Color.ORANGE, 1);
		Border yellow = BorderFactory.createLineBorder(Color.YELLOW, 1);
		Border green = BorderFactory.createLineBorder(Color.GREEN, 1);
		Border blue = BorderFactory.createLineBorder(Color.BLUE, 1);
		Border purple = BorderFactory.createLineBorder(Color.MAGENTA, 1);
		Border raisedBevel = BorderFactory.createRaisedBevelBorder();
		Border loweredBevel = BorderFactory.createLoweredBevelBorder();
		Border frame = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
		// health & mana borders
		TitledBorder healthBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Health: ",
				TitledBorder.CENTER, TitledBorder.TOP, gameFont);
		health.setBorder(healthBorder);
		TitledBorder manaBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mana: ",
				TitledBorder.CENTER, TitledBorder.TOP, gameFont);
		mana.setBorder(manaBorder);

		// make layouts
		GridBagLayout gbl = new GridBagLayout();

		// make panels
		Dimension d = new Dimension();
		JLayeredPane layered = new JLayeredPane();
		layered.setSize(SCREEN_SIZE);
		JPanel front = new JPanel(gbl);
		front.setSize(SCREEN_SIZE);
		JScrollPane chat = new JScrollPane(chatLabel);
		chat.setSize((int) SCREEN_SIZE.width / 4, (int) SCREEN_SIZE.height / 2);
		JPanel stats = new JPanel(gbl);
		d.setSize((int) (SCREEN_SIZE.width / 4), (int) (SCREEN_SIZE.height));
		stats.setMaximumSize(d);
		inventory = new JPanel(gbl);
		inventory.setSize((int) SCREEN_SIZE.width / 5, (int) SCREEN_SIZE.height / 10);
		JPanel map = new JPanel(gbl);
		map.setSize((int) (SCREEN_SIZE.width / 5), (int) (SCREEN_SIZE.width / 5));

		GridBagConstraints c = new GridBagConstraints();

		// set layout
//		user.setInventory(inventory);

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

		// map
		map.add(new MyCanvas(mapImage, SCREEN_SIZE.width / 10), c);

		// chat
		chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// stats
		c.gridy = 0;
		for (int x = 0; x < abilities.length; x++) {
			c.gridx = x;
			stats.add(new MyCanvas(abilities[x], SCREEN_SIZE.width / 40), c);
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

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		/// *
		// map & inventory
		if (lefty) {
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridy = 0;
			c.gridx = 0;
			front.add(inventory, c);
			inventory.setBorder(frame);
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridx = 2;
			front.add(map, c);
			map.setBorder(green);
			map.setBounds(0, 0, (int) (SCREEN_SIZE.width / 5), (int) (SCREEN_SIZE.width / 5));
		} else {
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridy = 0;
			c.gridx = 2;
			front.add(inventory, c);
			inventory.setBorder(frame);
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridx = 0;
			front.add(map, c);
			map.setBorder(green);
			map.setBounds(0, 0, (int) (SCREEN_SIZE.width / 5), (int) (SCREEN_SIZE.width / 5));
		}
		// */
		front.setBorder(frame);
		front.setSize(SCREEN_SIZE);
		front.setOpaque(false);
		front.setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);
		layered.add(front, new Integer(1), 0);

		MapPanel background = new MapPanel(game);
		background.setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height);
		background.resetPanAndZoom();
		layered.add(background, new Integer(0), 0);
		add(layered);
		Thread t = new Thread(background);
		t.start();

		System.out.println("visisble");
		setVisible(true);
		changeFontRecursive(this, menuFont);
		chatLabel.setFont(chatFont);
		// next line to be deleted when fixed
		// JOptionPane.showMessageDialog(controllingFrame, "Pressing tab breaks
		// everything", "Warning", JOptionPane.WARNING_MESSAGE);
		requestFocus();
		start();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / goldPerSecond);
			} catch (InterruptedException e) {
			}
			user.setGoldAmount(user.getGoldAmount() + 1);
			gold.setText("$" + user.getGoldAmount());
			user.setCurrentMana((int) Math.random() * 300);
			user.setCurrentHealth((int) Math.random() * 300);
//			user.refreshInventory(inventory);
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

	public void keyPressed(KeyEvent ke) {
		int pressed = ke.getKeyCode();
		System.out.println("KEY PRESSED: " + pressed);
		if (pressed >= 97 && pressed <= 105 && usePadAndBar) {
			pressed -= 48;
		}
		switch (pressed) { // TODO Make keys do proper things
		case KeyEvent.VK_TAB:
			// TAB???
			System.out.println("TAB???");
			break;
		case KeyEvent.VK_P:
			// GOTO Shop
			new Shop(user);
			break;
		case KeyEvent.VK_M:
			// GOTO In-Game
			System.out.println("GOTO In-Game");
			JOptionPane.showMessageDialog(controllingFrame, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
			break;
		case KeyEvent.VK_Q:
			// USE Q ability
			System.out.println("USE Q ability");
			// charater.UseAbility(Q);
			break;
		case KeyEvent.VK_W:
			// USE W ability
			System.out.println("USE W ability");
			// charater.UseAbility(W);
			break;
		case KeyEvent.VK_E:
			// USE E ability
			System.out.println("USE E ability");
			// charater.UseAbility(E);
			break;
		case KeyEvent.VK_R:
			// USE R ability
			System.out.println("USE R ability");
			// charater.UseAbility(R);
			break;
		case KeyEvent.VK_D:
			// USE D ability
			System.out.println("USE D ability");
			// charater.UseAbility(D);
			break;
		case KeyEvent.VK_F:
			// USE F ability
			System.out.println("USE F ability");
			// charater.UseAbility(F);
			break;
		case KeyEvent.VK_1:
			// USE inventory slot 1
			System.out.println("USE inventory slot 1");
			// charater.UseItem(1);
			break;
		case KeyEvent.VK_2:
			// USE inventory slot 2
			System.out.println("USE inventory slot 2");
			// charater.UseItem(2);
			break;
		case KeyEvent.VK_3:
			// USE inventory slot 3
			System.out.println("USE inventory slot 3");
			// charater.UseItem(3);
			break;
		case KeyEvent.VK_4:
			// USE inventory slot 4
			System.out.println("USE inventory slot 4");
			// charater.UseItem(4);
			break;
		case KeyEvent.VK_5:
			// USE inventory slot 5
			System.out.println("USE inventory slot 5");
			// charater.UseItem(5);
			break;
		case KeyEvent.VK_6:
			// USE inventory slot 6
			System.out.println("USE inventory slot 6");
			// charater.UseItem(6);
			break;
		case KeyEvent.VK_7:
			// USE inventory slot 7
			System.out.println("USE inventory slot 7");
			// charater.UseItem(7);
			break;
		case KeyEvent.VK_8:
			// USE inventory slot 8
			System.out.println("USE inventory slot 8");
			// charater.UseItem(8);
			break;
		case KeyEvent.VK_ESCAPE:
			// Escape pressed
			System.out.println("Escape pressed");
			System.exit(0);
			break;
		}
	}

	public void mouseClicked(MouseEvent me) { // TODO Click to move
		System.out.println("Mouse Point (" + me.getX() + ", " + me.getY() + ")");
	}

	public void actionPerformed(ActionEvent ae) { // TODO Send to appropriate windows
		String cmd = ae.getActionCommand();

		if (SHOP.equals(cmd)) { // GOTO Shop
			new Shop(user);
		} else if (MENU.equals(cmd)) { // GOTO In-Game
			JOptionPane.showMessageDialog(controllingFrame, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Something went wrong", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		testing = true;
		Dimension SCREEN_SIZE = new JFrame().getToolkit().getScreenSize();
		new GameScreen(5, new PlayerAccount("gfhedhkjuw"), 1);
	}

	// Not used interface methods
	public void keyTyped(KeyEvent ke) {
		// char pressed = ke.getKeyChar();
		// System.out.println("KEY TYPED: " + pressed);
	}

	public void keyReleased(KeyEvent ke) {
		// char released = ke.getKeyChar();
		// System.out.println("KEY RELEASED: " + released);
	}

	public void mousePressed(MouseEvent me) {
		// System.out.println("Mouse pressed; # of clicks: "
		// + me.getClickCount());
	}

	public void mouseReleased(MouseEvent me) {
		// System.out.println("Mouse released; # of clicks: "
		// + me.getClickCount());
	}

	public void mouseEntered(MouseEvent me) {
		// System.out.println("Mouse entered");
	}

	public void mouseExited(MouseEvent me) {
		// System.out.println("Mouse exited");
	}
}