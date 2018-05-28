package mobagame.launcher;

import java.awt.Button;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import mobagame.server.database.PlayerAccount;

@SuppressWarnings("serial")
public class Profile extends JFrame implements ActionListener, MobaGameLauncher {

	PlayerAccount player;

	Logger logger = Logger.getLogger(this.getClass().getName());


	private static String EMAIL = "email";

	public Profile(PlayerAccount name) {
		super(GAME_NAME);

		player = name;

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.LINE_START;
		c.weighty = 1;
		c.weightx = 1;
		c.gridy = 0;

		// left
		c.gridx = 0;
		JPanel icon = new JPanel();
		icon.add(new MyCanvas("resources/Reaper.png", 250));
		panel.add(icon, c);

		c.gridy = 1;
		JLabel displayUsername = new JLabel(player.getUsername());
		panel.add(displayUsername, c);

		String[] statColoumsNames = { "K/D/A", "W/L", "Favorit Character" };
		Object[][] statRow = { statColoumsNames,
				{ player.getKDARatio(), player.getWLRatio(), player.getFavoritChar() } };

		JTable stats = new JTable(statRow, statColoumsNames);

		c.gridy = 2;
		c.gridx = 0;
		panel.add(stats, c);

		Button editEmail = new Button("Edit email");
		editEmail.setActionCommand(EMAIL);
		editEmail.addActionListener(this);

		c.gridy = 3;
		panel.add(editEmail, c);

		// recent matches
		String[] recentColoumsNames = { "Time", "W/L", "K/D/A", "Charater" };
		Object[][] recentRow = { recentColoumsNames, { "10:10", "Lost", "1/100/0", "Reaper" },
				{ "10:01", "Win", "100/1/0", "Reaper" } };
		JTable recent = new JTable(recentRow, recentColoumsNames);

		c.gridwidth = 10;
		c.gridy = 0;
		c.gridx = 1;
		panel.add(recent);

		add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();

		if (EMAIL.equals(cmd)) {
			// edit email
			JOptionPane.showMessageDialog(null, "Current Email: ", "EDIT", JOptionPane.INFORMATION_MESSAGE);
		} else {
			logger.log(Level.WARNING, "  Invalid Action");
		}
	}

	public static void main(String[] args) {
		new Profile(new PlayerAccount("ktaces"));
	}
}
