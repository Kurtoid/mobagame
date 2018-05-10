package launcher;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyCanvas extends JComponent {
	
	String location;
	int scale;
	
	public MyCanvas(String location, int scale){
		this.location = location;
		this.scale = scale;
	}
	
  public void paint(Graphics g) {
    Image img1 = Toolkit.getDefaultToolkit().getImage(this.location);

    int width = img1.getWidth(this);
    int height = img1.getHeight(this);

    int w = scale * width;
    int h = scale * height;
    // explicitly specify width (w) and height (h)
    g.drawImage(img1, 0, 0, (int) w, (int) h, this);

  }
  
  public static void main(String[] a) {
	    JFrame window = new JFrame();
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setBounds(30, 30, 300, 300);
	    window.getContentPane().add(new MyCanvas("resources/black.png", 1));
	    window.setVisible(true);
	  }
}