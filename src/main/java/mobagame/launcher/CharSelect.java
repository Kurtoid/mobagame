package mobagame.launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CharSelect {
	JFrame selectionScreen = new JFrame("Character Select");
	JPanel blueTeamSelect = new JPanel();
	JPanel charSelectMenu = new JPanel();
	JPanel redTeamSelect = new JPanel();
	JPanel charStats = new JPanel();
	
	public CharSelect() {
		selectionScreen.setLayout(new GridLayout(3, 3, 5, 5));
		selectionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionScreen.setSize(1500, 800);
		blueTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		JLabel player1 = new JLabel("Player One");
		JLabel player2 = new JLabel("Player Two");
		JLabel player3 = new JLabel("Player Three");
		JLabel player4 = new JLabel("Player Four");
		JLabel player5 = new JLabel("Player Five");
		selectionScreen.setResizable(false);
		blueTeamSelect.add(player1);
		blueTeamSelect.add(player2);
		blueTeamSelect.add(player3);
		blueTeamSelect.add(player4);
		blueTeamSelect.add(player5);
		selectionScreen.add(blueTeamSelect);
		selectionScreen.add(charSelectMenu);
		selectionScreen.add(redTeamSelect);
		selectionScreen.add(charStats);
		selectionScreen.setVisible(true);
	}
}
