package mobagame.launcher;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyCanvas extends JComponent {

	String location;
	int scale;

	public MyCanvas(String location, int scale) {
		super();
		this.location = location;
		this.scale = scale;
		this.setPreferredSize(new Dimension(scale,  scale));
	}

	public void paint(Graphics g) {
		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(new File(location));

			int width = img1.getWidth(this);
			int height = img1.getHeight(this);

			int w = width*scale /* width */;
			int h = height*scale /* height*/;
			// explicitly specify width (w) and height (h)
			g.drawImage(img1, 0, 0, (int) w, (int) h, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 300, 300);
		window.getContentPane().add(new MyCanvas("resources/Items/strawberry.png", 6));
		window.setVisible(true);
	}
}