/**
 * Katelynn Morrison
 * Apr 26, 2018
 */

package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener {

	public static int windowHeight = 800;
	public static int windowWidth = (int) (windowHeight * 1.875); // 1500
	

	public static double goldAmount = 0;
	public static JLabel gold;

	private static String gameName = "[INSERT AWESOME GAME NAME HERE]";
	private static String charater;

	private static String SHOP = "shop";
	private static String MENU = "menu";

	private static JFrame controllingFrame; // needed for dialogs

	// open menu window for playerName
	public GameScreen(String charater) {
		super(gameName);

		this.charater = charater;

		setSize(windowWidth, windowHeight);
		setResizable(false);

		// create
		JLabel gold = new JLabel("$" + goldAmount);	


		// make layout
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// top
		c.ipady = 100;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridy = 0;

		c.gridx = 0;
		pane.add(gold, c);



	

		add(pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		setDefaultCloseOperation(0);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) { // TODO Send to appropriate windows
		String cmd = ae.getActionCommand();

		if (SHOP.equals(cmd)) { // GO TO Shop
			JOptionPane.showMessageDialog(controllingFrame, "TO Shop", "GO TO", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Something went wrong", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	//Error: Could not find or load main class mobagame.launcher.GameScreen

	public static void main(String[] args) {
		new GameScreen("Charater");
		while(true) {
			goldAmount++;
		}
	}
}