package mobagame.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import mobagame.server.database.PlayerAccount;

@SuppressWarnings("serial")
public class Profile extends JFrame implements ActionListener, MobaGameLauncher {

	PlayerAccount player;

	Logger logger = Logger.getLogger(this.getClass().getName());

	private static String EMAIL = "email";
	private static String MENU = "menu";

	public Profile(PlayerAccount p) {
		super(GAME_NAME);

		player = p;

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		
		// left
		JLabel displayUsername = new JLabel(player.getUsername());
		displayUsername.setHorizontalAlignment(JLabel.CENTER);
		panel.add(displayUsername, c);

		c.gridy = 1;
		JPanel icon = new JPanel();
		icon.add(new MyCanvas("resources/Character/Reaper.png", (int) (WINDOW_WIDTH/3.285)));
		panel.add(icon, c);

        // stats table
		String[] statColoumnsNames = { "K/D/A", "W/L", "Favorite Character" };
		String[][] statRow = { statColoumnsNames,
				{ player.getKDARatio(), player.getWLRatio(), player.getFavoritChar() } };
		DefaultTableModel statModel = new DefaultTableModel(0, statColoumnsNames.length){
			@Override
			        public boolean isCellEditable(int row, int column)
			        {
			            return false;
			        }
			};
			    for (String[] row : statRow) {
			    	statModel.addRow(row);
				}
		JTable stats = new JTable(statModel);

		for (int i = 0; i < statColoumnsNames.length; i++) {
			TableColumn column1 = stats.getTableHeader().getColumnModel().getColumn(i);
			column1.setHeaderValue(statColoumnsNames[i]);
		}
		
		c.gridy = 2;
		c.gridx = 0;
		panel.add(stats, c);

		JButton editEmail = new JButton("Edit email");
		editEmail.setActionCommand(EMAIL);
		editEmail.addActionListener(this);

		c.gridy = 3;
		panel.add(editEmail, c);
///*
		// right
		c.gridx = 1;
		JButton menu = new JButton("Return to menu");
		menu.setActionCommand(MENU);
		menu.addActionListener(this);

		c.gridy = 0;
		panel.add(menu, c);

		// recent matches
		c.gridy = 1;
		c.gridheight = 5;
		String[] recentColoumnsNames = { "Time", "W/L", "K/D/A", "Character" };
		String[][] recentRow = {recentColoumnsNames};
		DefaultTableModel recentModel = new DefaultTableModel(0, recentColoumnsNames.length){
			@Override
			        public boolean isCellEditable(int row, int column)
			        {
			            return false;
			        }
			};
				for(int i = 0; i < 10; i++){
					if (i < recentRow.length){
						recentModel.addRow(recentRow[i]);
					}else {
						recentModel.addRow(new String[]{"--:--", "----", "-/-/-", "No match data"});
					}
				}
		JTable recent = new JTable(recentModel);
		recent.getColumnModel().getColumn(0).setPreferredWidth(1);
		recent.getColumnModel().getColumn(1).setPreferredWidth(1);
		recent.getColumnModel().getColumn(2).setPreferredWidth(1);
		recent.getColumnModel().getColumn(3).setPreferredWidth(1);
		recent.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i  = 0; i < recentColoumnsNames.length; i++) {
			TableColumn column1 = recent.getTableHeader().getColumnModel().getColumn(i);			  
			column1.setHeaderValue(recentColoumnsNames[i]);
		}

		c.fill = GridBagConstraints.BOTH;
		
		panel.add(recent, c);
//*/
		add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();

		if (EMAIL.equals(cmd)) {
			// edit email
			JOptionPane.showMessageDialog(null, "This feature is currently unavailable", "EDIT", JOptionPane.INFORMATION_MESSAGE);
		} else 	if (MENU.equals(cmd)) {
			// menu
			new Menu(player, false);
			setVisible(false);
		} else {
			logger.log(Level.WARNING, "Invalid Action");
		}
	}

	public static void main(String[] args) {
		new Profile(new PlayerAccount("ktaces"));
	}
}
