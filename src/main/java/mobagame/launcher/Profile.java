package mobagame.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mobagame.server.database.PlayerAccount;

@SuppressWarnings("serial")
public class Profile extends JFrame implements MobaGameLauncher {

	PlayerAccount player;
	
	public Profile(PlayerAccount name) {
		super(GAME_NAME);
		
		player = name;

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel stats = new JPanel(new GridBagLayout());
		JPanel recentlyPlayed = new JPanel(new GridBagLayout());
		JPanel icon = new JPanel();
		icon.add(new MyCanvas("resources/Reaper.png", 250));
		JLabel displayUsername = new JLabel(player.getUsername());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weighty = 1;
		c.weightx = 1;
		c.gridy = 0;
		
		// left
		c.gridx = 0;
		panel.add(icon, c);
		
		c.gridy = 1;
		panel.add(displayUsername, c);
		
		c.gridy = 2;
		panel.add(stats, c);
		
		
		// recent matches
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		recentlyPlayed.add(new JLabel("Time"), c);
		c.gridx = 1;
		recentlyPlayed.add(new JLabel("W/L"), c);
		c.gridx = 2;
		recentlyPlayed.add(new JLabel("K/D/A"), c);
		c.gridx = 3;
		recentlyPlayed.add(new JLabel("Charater"), c);
		
		c.gridwidth = 10;
		c.gridy = 0;
		c.gridx = 1;
		panel.add(recentlyPlayed);
		
		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Profile(new PlayerAccount("ktaces"));
	}

}
