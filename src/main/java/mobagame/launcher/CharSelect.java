package mobagame.launcher;
//Carson Mango 4/25/18
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
	JPanel blue1 = new JPanel();
	JPanel blue2 = new JPanel();
	JPanel blue3 = new JPanel();
	JPanel blue4 = new JPanel();
	JPanel blue5 = new JPanel();
	JPanel red1 = new JPanel();
	JPanel red2 = new JPanel();
	JPanel red3 = new JPanel();
	JPanel red4 = new JPanel();
	JPanel red5 = new JPanel();
	public CharSelect() {
		ImageIcon placeHolderImage = new ImageIcon("E://ImpressMeProject/Black.png");
		JLabel blue1CharImage = new JLabel(placeHolderImage);
		JLabel blue2CharImage = new JLabel(placeHolderImage);
		JLabel blue3CharImage = new JLabel(placeHolderImage);
		JLabel blue4CharImage = new JLabel(placeHolderImage);
		JLabel blue5CharImage = new JLabel(placeHolderImage);
		JLabel red1CharImage = new JLabel(placeHolderImage);
		JLabel red2CharImage = new JLabel(placeHolderImage);
		JLabel red3CharImage = new JLabel(placeHolderImage);
		JLabel red4CharImage = new JLabel(placeHolderImage);
		JLabel red5CharImage = new JLabel(placeHolderImage);
		JLabel blue1User = new JLabel();
		selectionScreen.setLayout(new GridLayout(3, 3, 5, 5));
		selectionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionScreen.setSize(1500, 800);
		blueTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		redTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		blue1.setLayout(new GridLayout(1,2, 0, 0));
		blue2.setLayout(new GridLayout(1,2, 0, 0));
		blue3.setLayout(new GridLayout(1,2, 0, 0));
		blue4.setLayout(new GridLayout(1,2, 0, 0));
		blue5.setLayout(new GridLayout(1,2, 0, 0));
		red1.setLayout(new GridLayout(1,2, 0, 0));
		red2.setLayout(new GridLayout(1,2, 0, 0));
		red3.setLayout(new GridLayout(1,2, 0, 0));
		red4.setLayout(new GridLayout(1,2, 0, 0));
		red5.setLayout(new GridLayout(1,2, 0, 0));
		blue1.add(blue1CharImage);
		blue2.add(blue2CharImage);
		blue3.add(blue3CharImage);
		blue4.add(blue4CharImage);
		blue5.add(blue5CharImage);
		red1.add(red1CharImage);
		red2.add(red2CharImage);
		red3.add(red3CharImage);
		red4.add(red4CharImage);
		red5.add(red5CharImage);
		selectionScreen.setResizable(false);
		blueTeamSelect.add(blue1);
		blueTeamSelect.add(blue2);
		blueTeamSelect.add(blue3);
		blueTeamSelect.add(blue4);
		blueTeamSelect.add(blue5);
		JLabel penis  = new JLabel("Penis");
		charSelectMenu.add(penis);
		redTeamSelect.add(red1);
		redTeamSelect.add(red2);
		redTeamSelect.add(red3);
		redTeamSelect.add(red4);
		redTeamSelect.add(red5);
		selectionScreen.add(blueTeamSelect);
		selectionScreen.add(charSelectMenu);
		selectionScreen.add(redTeamSelect);
		selectionScreen.add(charStats);
		selectionScreen.setVisible(true);
	}
}