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
	int height;
	int width;

	public MyCanvas(String location, int scale) {
		super();
		this.location = location;
		this.width = scale;
		this.height = scale;
		this.setPreferredSize(new Dimension(width,  height));
		System.out.println("Square Image Created");
	}
	
	public MyCanvas(String location, int width, int height) {
		super();
		this.location = location;
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width,  height));
		System.out.println("Rectangle Image Created");
	}

	public void paint(Graphics g) {
		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(new File(location));

			// explicitly specify width (w) and height (h)
			g.drawImage(img1, 0, 0, (int) width, (int) height, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 300, 300);
		window.getContentPane().add(new MyCanvas("resources/Items/strawberry.png", 300));
		window.setVisible(true);
	}
}