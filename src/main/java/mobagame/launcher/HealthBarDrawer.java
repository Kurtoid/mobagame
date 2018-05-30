package mobagame.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class HealthBarDrawer {
	static public void draw(Graphics2D g, double healthValue, double healthMax, Color color, Point2D.Double pos, Dimension screensize) {
		g.setStroke(new BasicStroke(screensize.width/1000));
		g.setColor(Color.black);
		g.drawRect((int) pos.getX()-screensize.width / 40, (int) (pos.getY() - screensize.height / 20), screensize.width / 20, screensize.height / 50);
		g.setColor(color);
		g.fillRect((int) pos.getX()-screensize.width / 40, (int) (pos.getY() - screensize.height / 20), (int) ((screensize.width / 20)*(healthValue/healthMax)), screensize.height / 50);

	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		JPanel p = new JPanel() {
			public void paint(Graphics g) {
				HealthBarDrawer.draw((Graphics2D) g, 50, 100, Color.RED, new Point2D.Double(300, 400), f.getSize());
			}
		};
		f.setSize(1000, 1000);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}
}
