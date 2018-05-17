package mobagame.launcher;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class JoiningGameWindow extends JFrame {
	public final Dimension SCREEN_SIZE = getToolkit().getScreenSize();
	JProgressBar loadingBar;

	public JoiningGameWindow() {
		setSize(SCREEN_SIZE.width / 3, SCREEN_SIZE.height / 3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loadingBar = new JProgressBar();
		loadingBar.setStringPainted(true);
		loadingBar.setString("");
		loadingBar.setIndeterminate(true);
		add(loadingBar);
		setVisible(true);
	}

	public static void main(String[] args) {
		new JoiningGameWindow();
	}
}
