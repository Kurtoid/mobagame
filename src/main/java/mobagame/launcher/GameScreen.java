/**
 * Katelynn Morrison
 * Apr 26, 2018
 */

package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener {

	public static int windowHeight = 800; // 800
	public static int windowWidth = (int) (windowHeight * 1.875); // 1500
	private static Font menuFont = SignUp.menuFont;
	
	private static Boolean testing = false;
	
	private static double goldAmount = 0;
	private static JLabel gold;
	private JTextField textField;
	
	private static String gameName = "[INSERT AWESOME GAME NAME HERE]";
	private String charater;

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
		
		textField.addKeyListener(this);

		//Font Setup
		gold.setFont(menuFont);
		
		// make layout
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.ipady = 100;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridy = 0;

		c.gridx = 0;
		pane.add(gold, c);



	
		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		}
		add(pane);

//		setDefaultCloseOperation(0);
		setVisible(true);
	}
	
	/* Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent ke) {
    	char typed = ke.getKeyChar();
        System.out.println("KEY TYPED: " + typed);
    }
     
    /* Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent ke) {
    	char pressed = ke.getKeyChar();
        System.out.println("KEY PRESSED: " + pressed);
    }
     
    /* Handle the key released event from the text field. */
    public void keyReleased(KeyEvent ke) {
	char released = ke.getKeyChar();
    System.out.println("KEY RELEASED: " + released);
    }
	  
	public void actionPerformed(ActionEvent ae) { // TODO Send to appropriate windows
		String cmd = ae.getActionCommand();

		if (SHOP.equals(cmd)) { // GO TO Shop
			JOptionPane.showMessageDialog(controllingFrame, "TO Shop", "GO TO", JOptionPane.INFORMATION_MESSAGE);

		} else if (MENU.equals(cmd)) { // GO TO In-Game
			JOptionPane.showMessageDialog(controllingFrame, "TO In-Game", "GO TO", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Something went wrong", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	//Error: Could not find or load main class mobagame.launcher.GameScreen

	public static void main(String[] args) {
		testing = true;
		new GameScreen("Charater");
		while(true) {
			goldAmount++;
		}
	}
}