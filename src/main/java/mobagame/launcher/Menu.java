/**
 * Katelynn Morrison
 */

package mobagame.launcher;

import mobagame.core.game.GameCharcters;
import mobagame.core.game.GameTeams;
import mobagame.core.game.InGamePlayer;
import mobagame.core.networking.packets.NotifyPlayerEnterCharacterSelect;
import mobagame.core.networking.packets.NotifyProjectileFiredPacket;
import mobagame.core.networking.packets.RequestEnterLobbyPacket;
import mobagame.core.networking.packets.RequestEnterLobbyResponsePacket;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;
import mobagame.server.database.PlayerAccount;
import mobagame.server.game.ServerGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener, MobaGameLauncher {

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
	JButton playButton;

	// open menu window for playerName
	public Menu(PlayerAccount name, boolean admin) {
		super(GAME_NAME);
		player = name;

		isAdmin = admin;
		playerName = name.username;

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);

		// create
		JButton profileButton = new JButton("Profile");
		profileButton.setActionCommand(PROFILE);
		profileButton.addActionListener(this);

		JButton settingsButton = new JButton("Settings");
		settingsButton.setActionCommand(SETTINGS);
		settingsButton.addActionListener(this);

		playButton = new JButton("Play");
		playButton.setActionCommand(PLAY);
		playButton.addActionListener(this);

		JButton adminButton = new JButton("Admin Panel");
		adminButton.setActionCommand(ADMIN);
		adminButton.addActionListener(this);

		JLabel messageLabel = new JLabel("Welcome " + playerName + " to " + GAME_NAME);

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

//		if (testing) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		}
		setVisible(true);
		changeFontRecursive(this, MENU_FONT);
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
			playButton.setEnabled(false);
			playButton.setText("Searching for lobby");
			repaint();
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					RequestEnterLobbyPacket pkt = new RequestEnterLobbyPacket();
					RequestEnterLobbyResponsePacket resp;

					try {
						ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port).send(pkt.getBytes().array(), RspHandler.getInstance());
						RspHandler.getInstance().waitForResponse();
						resp = (RequestEnterLobbyResponsePacket) RspHandler.getInstance().getResponse(RequestEnterLobbyResponsePacket.class);
						playButton.setText("Joined lobby");
						System.out.println("joined lobby");
						InGamePlayer p = new InGamePlayer(player.id, GameCharcters.jack);
						repaint();

//						RspHandler.getInstance().waitForResponse();
						NotifyPlayerEnterCharacterSelect ecs = null;
						do {
							System.out.println("checking for ecs");
							try {
							RspHandler.getInstance().waitForResponse(500);
							}catch (TimeoutException e) {
								// do nothing, just keep checking
							}
							ecs = (NotifyPlayerEnterCharacterSelect) RspHandler.getInstance().getResponse(NotifyPlayerEnterCharacterSelect.class);
						}while(ecs==null);
						System.out.println("ecs found");
						p.team = GameTeams.gameTeams[ecs.teamID];
						new CharSelect(player, p, resp.lobbyID);
						Menu.this.setVisible(false);

					} catch (IOException e) {
						e.printStackTrace();
					}

					return null;
				}
			};
			worker.execute();

		} else if (PROFILE.equals(cmd)) { // GO TO Profile
			 new Profile(player);
			 setVisible(false);

		} else if (SETTINGS.equals(cmd)) { // GO TO Settings
			JOptionPane.showMessageDialog(controllingFrame, "TO Settings", "GO TO", JOptionPane.INFORMATION_MESSAGE);
			// new Settings(player);
			// setVisible(false);

		} else if (ADMIN.equals(cmd) && isAdmin) {// GO TO Admin
			JOptionPane.showMessageDialog(controllingFrame, "TO Admin", "GO TO", JOptionPane.INFORMATION_MESSAGE);
			// new Admin(player);
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