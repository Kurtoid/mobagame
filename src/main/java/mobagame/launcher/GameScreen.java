/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener, MouseListener, Runnable{

	public static int windowHeight = 800; // 800
	public static int windowWidth = (int) (windowHeight * 1.875); // 1500
	private static Font menuFont = SignUp.menuFont;

	private static boolean testing = false;
	private static boolean usePadAndBar = false;

	private int goldAmount = 0;
	private int goldPerSecond = 4;
	private JLabel gold = new JLabel();

	private static String gameName = Menu.gameName;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private JFrame controllingFrame; // needed for dialogs

	// open menu window for playerName
	public GameScreen() {
		super(gameName);

		setSize(windowWidth, windowHeight);
		setResizable(false);

		// create
		gold = new JLabel("$" + goldAmount);

		// Font Setup
		gold.setFont(menuFont);

		// make layout
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.ipady = 100;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.weightx = 0.5;
		c.gridy = 1;

		c.gridx = 0;
		pane.add(gold, c);

		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			// make full screen
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
		add(pane);

		setVisible(true);
			start();
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000/goldPerSecond);
			} catch (InterruptedException e) {
				// NOPE
			}
			goldAmount += 1;
			gold.setText("$" + goldAmount);
			setVisible(true);
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
		}
	}
	public void mouseClicked(MouseEvent me) { // TODO Click to move
		System.out.println("Mouse clicked (# of clicks: " + me.getClickCount() + ")");
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
		usePadAndBar = true;
		testing = true;
		GameScreen gs = new GameScreen();
		gs.addKeyListener(gs);
		gs.addMouseListener(gs);
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