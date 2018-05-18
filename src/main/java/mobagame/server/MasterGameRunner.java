package mobagame.server;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mobagame.core.game.Game;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.PlayerMover;
import mobagame.core.game.maps.MainMap;
import mobagame.server.game.ServerGame;

public class MasterGameRunner extends Thread {
	Set<ServerGame> games;
	Map<Integer, InGamePlayer> connectionToPlayer = new HashMap<>();

	Map<InGamePlayer, ServerGame> playerToGame = new HashMap<>();
	boolean running = false;
	double fps;
	int frameCount = 0;
	public ConnectionListener conn;
	/**
	 * almost never used, but there if we need it
	 */
	boolean paused = false;

	public MasterGameRunner() {
		games = new HashSet<>();
		running = true;
		this.setDaemon(true);
		this.start();
		ServerGame.runner = this;
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
					// System.out.println("NEW SECOND " + thisSecond + " " + updateCount);
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
		for (ServerGame g : games) {
			g.update();
			g.sendToClients(conn);
		}
	}

	public ServerGame findGame(int playerID) {
		for (ServerGame g : games) {
			if (!g.isFull()) {
				System.out.println("using existing game");
				return g;
			}
		}
		MainMap m = new MainMap();
		m.setServerMode();
		m.makeMap();
		ServerGame g = new ServerGame(m);
		games.add(g);
		System.out.println("new game created");
		return g;
	}

	public InGamePlayer getPlayer(int connectionID) {
		return connectionToPlayer.get(connectionID);
	}

	public void addToGame(ServerGame g, InGamePlayer p, int connectionID) {
		g.players.add(p);
		connectionToPlayer.put(connectionID, p);
		playerToGame.put(p, g);

	}
}
