package mobagame.launcher;

import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RectangleDrawing extends JComponent {

	private int xPos, yPos, height, width;
	private Color color;
	private boolean isFill;

	RectangleDrawing(int xPos, int yPos, int width, int height, Color color, boolean isFill) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
		this.width = width;
		this.color = color;
		this.isFill = isFill;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		if (isFill) {
			g.fillRect(xPos, yPos, width, height);
		} else {
			g.drawRect(xPos, yPos, width, height);
		}
	}

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 300, 300);
		window.getContentPane().add(new RectangleDrawing(0, 0, 10, 200, Color.magenta, false));
		window.setVisible(true);
	}
}
