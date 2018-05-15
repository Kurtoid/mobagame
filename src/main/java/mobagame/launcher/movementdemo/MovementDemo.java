package mobagame.launcher.movementdemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovementDemo extends JFrame implements Runnable {
	PlayerMover mover;
	JPanel panel;
	MapRenderer mapR;
	boolean running = true;
	boolean paused = false;
	double fps;
	final int SIZE_X = 1000;
	final int SIZE_Y = 1000;

	public static void main(String[] args) {
		Thread t = new Thread(new MovementDemo());
		t.start();

	}

	public MovementDemo() {
		JLayeredPane lPane = new JLayeredPane();
		mapR = new MapRenderer(SIZE_X, SIZE_Y);
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
		lPane.setBounds(0, 0, SIZE_X, SIZE_Y);
		lPane.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		add(lPane);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void run() {
		// Only run this in another Thread!
		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// At the very most we will update the game this many times before a new render.
		// If you're worried about visual hitches more than perfect timing, set this to
		// 1.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		// If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused) {
				// Do as many game updates as we need to, potentially playing catchup.
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					// updateGame();
					mover.update();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				// If for some reason an update takes forever, we don't want to do an insane
				// number of catchups.
				// If you were doing some sort of game that needed to keep EXACT time, you would
				// get rid of this.
				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Render. To do so, we need to calculate interpolation for a smooth render.
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				// drawGame(interpolation);
				repaint();
//				frameCount++;

				lastRenderTime = now;

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					System.out.println("NEW SECOND " + thisSecond + " " + mapR.frameCount);
					fps = mapR.frameCount;
					mapR.frameCount = 0;
					lastSecondTime = thisSecond;
				}

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
						&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();

					// This stops the app from consuming all your CPU. It makes this slightly less
					// accurate, but is worth it.
					// You can remove this line and it will still work (better), your CPU just
					// climbs on certain OSes.
					// FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a
					// look at different peoples' solutions to this.
					try {
						Thread.sleep(1);
					} catch (Exception e) {
					}

					now = System.nanoTime();
				}
			}
		}
	}
}
