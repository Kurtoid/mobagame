package mobagame.launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CharSelect {
	JFrame selectionScreen = new JFrame("Character Select");
	public CharSelect() {
		selectionScreen.setLayout(new GridLayout(2, 1, 5, 5));
		selectionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionScreen.setSize(1500, 800);
		selectionScreen.setResizable(false);
		JLabel placeholder = new JLabel("Char select screen");
		selectionScreen.add(placeholder);
		selectionScreen.setVisible(true);
	}
}
