package mobagame.launcher;

import mobagame.core.game.Game;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.maps.MainMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class MapPanel extends JPanel implements Runnable {
	MainMap map;
	Game game;

	int translateX = 0;
	int translateY = 0;
	int scaleX = 1;
	int scaleY = 1;
	boolean running = true;
	boolean paused = false;
	double fps;
	long frameCount = 0;

	int mouseX;
	int mouseY;

	public MapPanel(MainMap m, Game g) {
		map = m;
		game = g;
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("mouseclick " + e.toString());
				game.getPlayerPlayer().mover.setTarget(e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println("mouse dragged");
				translateX += ((e.getX() - mouseX) / scaleX);
				translateY += ((e.getY() - mouseY) / scaleY);
				mouseX = e.getX();
				mouseY = e.getY();
				repaint();
			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				System.out.println("mouse wheel moved");
				scaleX += e.getWheelRotation();
				scaleY += e.getWheelRotation();

				scaleX = Math.max(1, scaleX);
				scaleY = Math.max(1, scaleY);
				System.out.println(scaleX + " " + scaleY);
				repaint();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setStroke(new BasicStroke(getHeight() / 100));
		Path2D tmp = (Path2D) map.getMap().clone();
		tmp.transform(getCurrentTransform());
		graphics.draw(tmp);
		// for (InGamePlayer player : game.players) {
		// graphics.fillRect((int) player.getX(), (int) player.getY(), 20, 20);
		// }
	}

	private AffineTransform getCurrentTransform() {
		AffineTransform tx = new AffineTransform();
		double centerX = (double) getWidth() / 2;
		double centerY = (double) getHeight() / 2;
		tx.translate(centerX, centerY);
		tx.scale(scaleX, scaleY);
		tx.translate(-centerX, -centerY);

		tx.translate(translateX, translateY);
		return tx;
	}

	void resetPanAndZoom() {
		System.out.println();
		translateX = getWidth() / 2 - map.width / 2;
		translateY = getHeight() / 2 - map.height / 2;
		scaleX = 1;
		scaleY = 1;
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
					// do server pings here
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
				frameCount++;

				lastRenderTime = now;

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
					fps = frameCount;
					frameCount = 0;
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
