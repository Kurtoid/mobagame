package mobagame.launcher.movementdemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovementDemo extends JFrame implements Runnable {
	PlayerMover mover;
	JPanel panel;
	MapRenderer mapR;
	final int SIZE_X = 1000;
	final int SIZE_Y = 1000;

	public static void main(String[] args) {
		Thread t = new Thread(new MovementDemo());
		t.start();

	}

	public MovementDemo() {
		JLayeredPane lPane = new JLayeredPane();
		mapR = new MapRenderer();
		mover = new PlayerMover(this, mapR);
		mover.start();
		panel = new JPanel() {
			@Override
			public void paint(Graphics graphics) {
				super.paint(graphics);
				Graphics2D g = (Graphics2D) graphics;
				g.setColor(Color.RED);
				g.fillRect((int) mover.x, (int) mover.y, 5, 5);
			}
		};

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				mover.targetx = mouseEvent.getX();
				mover.targety = mouseEvent.getY();
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent mouseEvent) {
				mover.targetx = mouseEvent.getX();
				mover.targety = mouseEvent.getY();
			}

			@Override
			public void mouseMoved(MouseEvent mouseEvent) {

			}
		});
		mapR.setOpaque(false);
		mapR.setBounds(0, 0, SIZE_X, SIZE_Y);
		panel.setOpaque(false);
		panel.setBounds(0, 0, SIZE_X, SIZE_Y);
		lPane.add(mapR, 1);
		lPane.add(panel, 0);
		add(lPane);
		setSize(SIZE_X, SIZE_Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void run() {
		long lastRunTime = System.currentTimeMillis();
		final long targetFrequency = 1000 / 60;
		long waitTime = 16;
		long lastDrawLength = 16;
		int i = 0;
		while (true) {
			repaint();
			i++;
			try {
				Thread.sleep(waitTime);
				lastDrawLength = System.currentTimeMillis() - lastRunTime;
				lastRunTime = System.currentTimeMillis();
				waitTime = Math.max(0, targetFrequency - lastDrawLength);
				if (i % 60 == 1) {
					System.out.println(lastDrawLength);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
