/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import mobagame.server.database.PlayerAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {

	public final Dimension SCREEN_SIZE = getToolkit().getScreenSize();

	public int windowHeight = SCREEN_SIZE.height * 4 / 5;
	public int windowWidth = (int) (windowHeight * 1.875);

	private int fontSize = (int) ((windowWidth / 90) * 1.5);
	private static String font = "Old English Text MT";
	public Font menuFont = new Font(font, Font.PLAIN, fontSize);

	public static String gameName = "[INSERT AWESOME GAME NAME HERE]";
	private static boolean isAdmin;
	private static String playerName;

	private static String PLAY = "play";
	private static String PROFILE = "profile";
	private static String SETTINGS = "settings";
	private static String ADMIN = "admin";

	private static Boolean testing = false;

	private static JFrame controllingFrame; // needed for dialogs
	PlayerAccount player;
	// open menu window for testing

	public Menu() {
		this(new PlayerAccount("testing"), false);
	}

	// open menu window for playerName
	public Menu(PlayerAccount name, boolean admin) {
		super(gameName);
		player = name;

		isAdmin = admin;
		playerName = name.username;

		setSize(windowWidth, windowHeight);
		setResizable(false);

		// create
		JButton profileButton = new JButton("Profile");
		profileButton.setActionCommand(PROFILE);
		profileButton.addActionListener(this);

		JButton settingsButton = new JButton("Settings");
		settingsButton.setActionCommand(SETTINGS);
		settingsButton.addActionListener(this);

		JButton playButton = new JButton("Play");
		playButton.setActionCommand(PLAY);
		playButton.addActionListener(this);

		JButton adminButton = new JButton("Admin Panel");
		adminButton.setActionCommand(ADMIN);
		adminButton.addActionListener(this);

		JLabel messageLabel = new JLabel("Welcome " + playerName + " to " + gameName);

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
		pane.add(profileButton, c);

		c.gridx = 1;
		pane.add(settingsButton, c);

		// middle (photo)
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(messageLabel, c);

		// bottom
		if (isAdmin) {
			c.gridwidth = 1;
			c.ipady = 100; // reset to default
			c.weighty = 1.0; // request any extra vertical space
			c.anchor = GridBagConstraints.PAGE_END; // bottom of space
			c.gridy = 2;

			c.gridx = 1;
			pane.add(playButton, c);

			c.gridx = 0;
			pane.add(adminButton, c);

		} else {
			c.ipady = 100; // reset to default
			c.weighty = 1.0; // request any extra vertical space
			c.anchor = GridBagConstraints.PAGE_END; // bottom of space
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 2;
			pane.add(playButton, c);
		}

		add(pane);

		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		setVisible(true);
		changeFontRecursive(this, menuFont);
	}

	public void changeFontRecursive(Container root, Font font) {
		for (Component c : root.getComponents()) {
			c.setFont(font);
			if (c instanceof Container) {
				changeFontRecursive((Container) c, font);
			}
		}
	}
	
	public void actionPerformed(ActionEvent ae) { // TODO Send to appropriate windows
		String cmd = ae.getActionCommand();

		if (PLAY.equals(cmd)) { // GO TO Selection
			// TODO Find available game
			new CharSelect(player);
			setVisible(false);

		} else if (PROFILE.equals(cmd)) { // GO TO Profile
			JOptionPane.showMessageDialog(controllingFrame, "TO Profile", "GO TO", JOptionPane.INFORMATION_MESSAGE);
			// new Profile(playerName);
			// setVisible(false);

		} else if (SETTINGS.equals(cmd)) { // GO TO Settings
			JOptionPane.showMessageDialog(controllingFrame, "TO Settings", "GO TO", JOptionPane.INFORMATION_MESSAGE);
			// new Settings(playerName);
			// setVisible(false);

		} else if (ADMIN.equals(cmd) && isAdmin) {// GO TO Admin
			JOptionPane.showMessageDialog(controllingFrame, "TO Admin", "GO TO", JOptionPane.INFORMATION_MESSAGE);
			// new Admin(playerName);
			// setVisible(false);
		} else {
			JOptionPane.showMessageDialog(controllingFrame, "Something went wrong", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		testing = true;
		new Menu(new PlayerAccount("ktaces"), true);
	}
}