package mobagame.launcher;

//Carson Mango 4/25/18
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CharSelect implements Runnable {
	private JLabel timer = new JLabel("90");
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
	public ImageIcon placeHolderImage = new ImageIcon("E://ImpressMeProject/Black.png");
	public ImageIcon reaperCharPic = new ImageIcon("E://ImpressMeProject/Reaper.png");

	public void run() {
		String temp = "90";
		for (int i = 90; i >= 0; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			temp = Integer.toString(i);
			timer.setText(temp);
			selectionScreen.setVisible(true);
		}
		selectionScreen.setVisible(false);
		 new GameScreen();
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	private GridBagConstraints gbc = new GridBagConstraints();

	public CharSelect() {
		blueTeamSelect.setSize(750, 400);
		redTeamSelect.setSize(750, 400);
		blue1.setSize(150, 400);
		blue2.setSize(150, 400);
		blue3.setSize(150, 400);
		blue4.setSize(150, 400);
		blue5.setSize(150, 400);
		red1.setSize(150, 400);
		red2.setSize(150, 400);
		red3.setSize(150, 400);
		red4.setSize(150, 400);
		red5.setSize(150, 400);
		ImageIcon placeHolderImage = new ImageIcon("E://ImpressMeProject/Black.png");
		JLabel blue1CharImage = new JLabel(placeHolderImage);
		JLabel blue2CharImage = new JLabel(reaperCharPic);
		JLabel blue3CharImage = new JLabel(placeHolderImage);
		JLabel blue4CharImage = new JLabel(placeHolderImage);
		JLabel blue5CharImage = new JLabel(placeHolderImage);
		JLabel red1CharImage = new JLabel(placeHolderImage);
		JLabel red2CharImage = new JLabel(placeHolderImage);
		JLabel red3CharImage = new JLabel(placeHolderImage);
		JLabel red4CharImage = new JLabel(placeHolderImage);
		JLabel red5CharImage = new JLabel(placeHolderImage);
		red5CharImage.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel test = new JLabel(reaperCharPic);
		JLabel test2 = new JLabel("Hi");
		blue1CharImage.setSize(150, 150);
		blue2CharImage.setSize(150, 150);
		blue3CharImage.setSize(150, 150);
		blue4CharImage.setSize(150, 150);
		blue5CharImage.setSize(150, 150);
		red1CharImage.setSize(150, 150);
		red2CharImage.setSize(150, 150);
		red3CharImage.setSize(150, 150);
		red4CharImage.setSize(150, 150);
		red5CharImage.setSize(150, 150);
		JLabel blue1User = new JLabel("Temp");
		JLabel blue2User = new JLabel("Temp");
		JLabel blue3User = new JLabel("Temp");
		JLabel blue4User = new JLabel("Temp");
		JLabel blue5User = new JLabel("Temp");
		JLabel red1User = new JLabel("Temp");
		JLabel red2User = new JLabel("Temp");
		JLabel red3User = new JLabel("Temp");
		JLabel red4User = new JLabel("Temp");
		JLabel red5User = new JLabel("Temp");
		red1User.setHorizontalAlignment(JLabel.RIGHT);
		red2User.setHorizontalAlignment(JLabel.RIGHT);
		red3User.setHorizontalAlignment(JLabel.RIGHT);
		red4User.setHorizontalAlignment(JLabel.RIGHT);
		red5User.setHorizontalAlignment(JLabel.RIGHT);
		selectionScreen.setLayout(new GridBagLayout());
		selectionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionScreen.setSize(1500, 800);
		blueTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		redTeamSelect.setLayout(new GridLayout(5, 1, 5, 5));
		blue1.setLayout(new GridLayout(1, 2, 0, 0));
		blue2.setLayout(new GridLayout(1, 2, 0, 0));
		blue3.setLayout(new GridLayout(1, 2, 0, 0));
		blue4.setLayout(new GridLayout(1, 2, 0, 0));
		blue5.setLayout(new GridLayout(1, 2, 0, 0));
		red1.setLayout(new GridLayout(1, 2, 0, 0));
		red2.setLayout(new GridLayout(1, 2, 0, 0));
		red3.setLayout(new GridLayout(1, 2, 0, 0));
		red4.setLayout(new GridLayout(1, 2, 0, 0));
		red5.setLayout(new GridLayout(1, 2, 0, 0));
		blue1.add(blue1CharImage);
		blue2.add(blue2CharImage);
		blue3.add(blue3CharImage);
		blue4.add(blue4CharImage);
		blue5.add(blue5CharImage);
		red1.add(red1User);
		red2.add(red2User);
		red3.add(red3User);
		red4.add(red4User);
		red5.add(red5User);
		red1.add(red1CharImage);
		red2.add(red2CharImage);
		red3.add(red3CharImage);
		red4.add(red4CharImage);
		red5.add(red5CharImage);
		blue1.add(blue1User);
		blue2.add(blue2User);
		blue3.add(blue3User);
		blue4.add(blue4User);
		blue5.add(blue5User);
		selectionScreen.setResizable(false);
		blueTeamSelect.add(blue1);
		blueTeamSelect.add(blue2);
		blueTeamSelect.add(blue3);
		blueTeamSelect.add(blue4);
		blueTeamSelect.add(blue5);
		redTeamSelect.add(red1);
		redTeamSelect.add(red2);
		redTeamSelect.add(red3);
		redTeamSelect.add(red4);
		redTeamSelect.add(red5);
		charSelectMenu.add(test);
		charStats.add(test2);
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		selectionScreen.add(timer, gbc);
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weighty = 1;
		selectionScreen.add(blueTeamSelect, gbc);
		gbc.gridx = 2;
		selectionScreen.add(redTeamSelect, gbc);
		gbc.gridx = 1;
		gbc.weightx = 2;
		selectionScreen.add(charSelectMenu, gbc);
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.weightx = 1;
		// Found your problem Carson, you were not using the constraint. KEM
		// selectionScreen.add(charStats); old line
		selectionScreen.add(charStats, gbc); // new line
		selectionScreen.setVisible(true);
		start();
		System.out.println(gbc.gridy);
	}
}