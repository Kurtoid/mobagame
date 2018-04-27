/**
 * Katelynn Morrison
 * Apr 26, 2018
 */

package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener, MouseListener {

	public static int windowHeight = 200; // 800
	public static int windowWidth = (int) (windowHeight * 1.875); // 1500
	private static Font menuFont = SignUp.menuFont;

	private static Boolean testing = false;

	private double goldAmount = 0;
	private JLabel gold = new JLabel();

	private static String gameName = Menu.gameName;
	private String charater;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private JFrame controllingFrame; // needed for dialogs

	// open menu window for playerName
	public GameScreen(String charater) {
		super(gameName);

		this.charater = charater;

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

		// setDefaultCloseOperation(0);
		setVisible(true);
	}

	public void keyTyped(KeyEvent ke) {
		char typed = Character.toUpperCase(ke.getKeyChar());
//		System.out.println("KEY TYPED: " + typed);
		switch (typed) { // TODO Make keys do proper things
		case 'P':
			// GOTO Shop
			System.out.println("GOTO Shop");
			JOptionPane.showMessageDialog(controllingFrame, "TO Shop", "GOTO", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 'M':
			// GOTO In-Game
			System.out.println("GOTO In-Game");
			JOptionPane.showMessageDialog(controllingFrame, "TO In-Game", "GOTO", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 'Q':
			// USE Q ability
			System.out.println("USE Q ability");
			// charater.UseAbility(Q);
			break;
		case 'W':
			// USE W ability
			System.out.println("USE W ability");
			// charater.UseAbility(W);
			break;
		case 'E':
			// USE E ability
			System.out.println("USE E ability");
			// charater.UseAbility(E);
			break;
		case 'R':
			// USE R ability
			System.out.println("USE R ability");
			// charater.UseAbility(R);
			break;
		case 'D':
			// USE D ability
			System.out.println("USE D ability");
			// charater.UseAbility(D);
			break;
		case 'F':
			// USE F ability
			System.out.println("USE F ability");
			// charater.UseAbility(F);
			break;
		case '1':
			// USE inventory slot 1
			System.out.println("USE inventory slot 1");
			// charater.UseItem(1);
			break;
		case '2':
			// USE inventory slot 2
			System.out.println("USE inventory slot 2");
			// charater.UseItem(2);
			break;
		case '3':
			// USE inventory slot 3
			System.out.println("USE inventory slot 3");
			// charater.UseItem(3);
			break;
		case '4':
			// USE inventory slot 4
			System.out.println("USE inventory slot 4");
			// charater.UseItem(4);
			break;
		case '5':
			// USE inventory slot 5
			System.out.println("USE inventory slot 5");
			// charater.UseItem(5);
			break;
		case '6':
			// USE inventory slot 6
			System.out.println("USE inventory slot 6");
			// charater.UseItem(6);
			break;
		case '7':
			// USE inventory slot 7
			System.out.println("USE inventory slot 7");
			// charater.UseItem(7);
			break;
		case '8':
			// USE inventory slot 8
			System.out.println("USE inventory slot 8");
			// charater.UseItem(8);
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
		testing = true;
		GameScreen gs = new GameScreen("Charater");
		gs.addKeyListener(gs);
		gs.addMouseListener(gs);
		while (true) {
			gs.goldAmount++;
			gs.gold.setText("$" + gs.goldAmount);
			gs.gold.repaint();
		}
	}

	// Not used interface methods
	public void keyPressed(KeyEvent ke) {
		// char pressed = ke.getKeyChar();
		// System.out.println("KEY PRESSED: " + pressed);
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