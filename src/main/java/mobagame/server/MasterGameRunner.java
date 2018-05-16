package mobagame.server;

import java.util.HashSet;
import java.util.Set;

import mobagame.core.game.Game;

public class MasterGameRunner extends Thread {
	Set<Game> games;
	boolean running = false;
	double fps;
	int frameCount = 0;
	/**
	 * almost never used, but there if we need it
	 */
	boolean paused = false;

	public MasterGameRunner() {
		games = new HashSet<>();
		running = true;
		this.setDaemon(true);
		this.start();
	}

	/**
	 * http://www.java-gaming.org/index.php?topic=24220.0 <br>
	 * modified by me for server tick counting
	 */
	@Override
	public void run() {
		super.run();
		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 30.0;
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		int updateCount = 0;

		while (running) {
			double now = System.nanoTime();

			if (!paused) {
				// Do as many game updates as we need to, potentially playing catchup.
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					updateGame();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
					lastUpdateTime = now;

				}

				// If for some reason an update takes forever, we don't want to do an insane
				// number of catchups.
				// If you were doing some sort of game that needed to keep EXACT time, you would
				// get rid of this.
				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					System.out.println("NEW SECOND " + thisSecond + " " + updateCount);
					fps = updateCount;
					updateCount = 0;
					lastSecondTime = thisSecond;
				}

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
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

	private void updateGame() {
		
	}

	public void assignGame(int playerID) {
	}
}
