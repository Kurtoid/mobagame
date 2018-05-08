/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener, MouseListener, Runnable {

	private static Font menuFont = SignUp.menuFont;

	private static boolean testing = true;
	private static boolean usePadAndBar = false;
	private static boolean lefty = false;

	private int goldAmount = 0;
	private int goldPerSecond = 4;
	private JButton gold;

	public ImageIcon placeHolderImage = new ImageIcon("resources/Black.png");

	private static String gameName = Menu.gameName;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private JFrame controllingFrame; // needed for dialogs

	// open menu window for playerName
	public GameScreen() {
		super(gameName);

		// listeners
		this.addKeyListener(this);
		this.addMouseListener(this);

		// create
		gold = new JButton("$" + goldAmount);
		gold.setActionCommand(SHOP);
		gold.addActionListener(this);

		JLabel title = new JLabel("gameName");
		JLabel mapImage = new JLabel(placeHolderImage);
		JLabel temp2 = new JLabel("chat");
		JLabel temp3 = new JLabel("stats");

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

		// make layouts
		GridBagLayout gbl = new GridBagLayout();

		// make panels
		JLayeredPane layered = new JLayeredPane();
		JPanel pane = new JPanel(gbl);
		JPanel chat = new JPanel(gbl);
		JPanel stats = new JPanel(gbl);
		JPanel inventory = new JPanel(gbl);
		JPanel map = new JPanel(gbl);
		map.setSize(10000,10000);

		GridBagConstraints c = new GridBagConstraints();

		// set layout
		
		// inventory
		c.gridwidth = 1;
		c.weighty = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridy = 0;
		c.gridx = 0;
		inventory.add(gold, c);

		// map
		map.add(mapImage);
		// chat
		chat.add(temp2);
		// stats
		stats.add(temp3, c);
		

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		c.gridy = 1;
//		c.gridx = 0;
//		c.anchor = GridBagConstraints.SOUTHWEST;
//		pane.add(chat, c);
//		chat.setBorder(yellow);
		c.gridx = 1;
//		c.anchor = GridBagConstraints.SOUTHWEST;
		pane.add(stats, c);
		stats.setBorder(red);
		
		// top
		if (lefty) {
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridy = 0;
			c.gridx = 0;
			pane.add(inventory, c);
			inventory.setBorder(blue);
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridx = 2;
			pane.add(map, c);
			map.setBorder(green);
			map.setBounds(0, 0, 100, 100);
		} else {
			c.anchor = GridBagConstraints.NORTHEAST;
			c.gridy = 0;
			c.gridx = 2;
			pane.add(inventory, c);
			inventory.setBorder(blue);
			c.anchor = GridBagConstraints.NORTHWEST;
			c.gridx = 0;
			pane.add(map, c);
			map.setBorder(green);
			map.setBounds(0, 0, 100, 100);
		}
		
		
		pane.setBorder(frame);
		pane.setSize(getToolkit().getScreenSize());
		layered.add(pane, new Integer(1), 0);
		add(layered);

		// System.out.println(getToolkit().getScreenSize());
		setVisible(true);
		changeFontRecursive(this, menuFont);
		// next line to be deleted when fixed
//		JOptionPane.showMessageDialog(controllingFrame, "Pressing tab breaks everything", "Warning",
//				JOptionPane.WARNING_MESSAGE);
		requestFocus();
		start();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / goldPerSecond);
			} catch (InterruptedException e) {				
			}
			goldAmount += 1;
			gold.setText("$" + goldAmount);
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
			System.out.println("GOTO Shop");
			JOptionPane.showMessageDialog(controllingFrame, "TO Shop", "GOTO", JOptionPane.INFORMATION_MESSAGE);
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
		case KeyEvent.VK_9:
			// Key 9 pressed
			System.out.println("Key 9 pressed");
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
			JOptionPane.showMessageDialog(controllingFrame, "TO Shop", "GOTO", JOptionPane.INFORMATION_MESSAGE);
		} else if (MENU.equals(cmd)) { // GOTO In-Game
			JOptionPane.showMessageDialog(controllingFrame, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Something went wrong", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new GameScreen();
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