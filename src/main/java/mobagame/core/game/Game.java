package mobagame.core.game;

import java.util.ArrayList;
import java.util.Random;

import mobagame.core.game.maps.MainMap;

public class Game {
	public ArrayList<InGamePlayer> players;
	MainMap map;

	private int gameID;

	public Game() {
		map = new MainMap();
		map.setServerMode();
		map.makeMap();
		players = new ArrayList<>();
		gameID = new Random().nextInt();
	}

	public int getGameID() {
		return gameID;
	}

	private void update() {
		for (InGamePlayer player : players) {
			double angleRadians = Math.atan2(player.getTargetX() - player.getY(), player.getTargetY() - player.getX());
			double oldx = player.getX();

			double x = (player.getX() + (player.getCharater().getSpeed() * Math.cos(angleRadians)));

			/*
			 * if (x < targetx) { x++; } if (x > targetx) { x--; } //
			 */
			if (map.getMap().intersects(x, player.getY(), 5, 5)) {
				// x = oldx;
			} else {
				player.setX(x);
			}
			double oldy = player.getY();
			double y = (player.getY() + (player.getCharater().getSpeed() * Math.sin(angleRadians)));
			/*
			 * if (y > targety) { y--; } if (y < targety) { y++; } //
			 */
			if (map.getMap().intersects(player.getX(), y, 5, 5)) {
				// y = oldy;
			} else {
				player.setY(y);
			}

		}
	}

}
